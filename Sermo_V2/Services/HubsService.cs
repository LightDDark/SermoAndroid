using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Claims;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Authentication;
using Microsoft.AspNetCore.Authentication.Cookies;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Domain;
using Domain.Out;
using Repository;
using Domain.In;
using Domain.Hub;

namespace Services
{
    public class HubsService
    {
        private readonly WebApplication1Context _context;

        public HubsService(WebApplication1Context context)
        {
            _context = context;
        }

        public async void OnConnected(string userId, string ConnectionId)
        {
                
            var user = await _context.User.Include(u => u.Connections).FirstOrDefaultAsync(u => u.Id == userId);

            if (user == null)
            {
                   // problem
            }

            user.Connections.Add(new Connection
            {
                ConnectionID = ConnectionId,
                Connected = true
            });

            _context.Entry(user).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                throw;
            }
        }

        public async void OnDisconnected(string connectionId)
        {
            if (_context.Connections == null)
            {
                return;
            }
            var connection = await _context.Connections.FindAsync(connectionId);
            if (connection == null)
            {
                return;
            }
            connection.Connected = false;
            _context.Entry(connection).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                throw;
            }
        }

        public async Task<List<Connection>> getConnections(string who, string userName)
        {

            var user = await _context.User.FindAsync(who);
            List<Connection> connections = new List<Connection>();
                if (user == null)
                {
                    // problem
                }
                else
                {
                    _context.Entry(user)
                        .Collection(u => u.Connections)
                        .Query()
                        .Where(c => c.Connected == true)
                        .Load();

                    if (user.Connections == null)
                    {
                        // already not connected
                    }
                    else
                    {
                        foreach (var connection in user.Connections)
                        {
                        connections.Add(connection);
                        }
                    }
                }
            return connections;
        }
    }
}

