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

namespace Services
{

    public class UsersService
    {
        private readonly WebApplication1Context _context;

        public UsersService(WebApplication1Context context)
        {
            _context = context;
        }

        public async Task<bool> UpdateContact(InContact contact, string user)
        {
            User? u = await UserWithContacts(user);
            if (u == null || u.Contacts == null)
            {
                return false;
            }
            Contact? c = u.Contacts.Find(c => c.Id == contact.Id);
            if (c == null)
            {
                return false;
            }
            if (contact.Server != null)
            {
                c.Server = contact.Server;
            }
            if (contact.Name != null)
            {
                c.Name = contact.Name;
            }

            _context.Entry(u).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                throw;
            }

            return true;
        }
        public async Task<User?> GetUser(string id)
        {
            if (_context.User == null)
            {
                return null;
            }
            var user = await _context.User.FindAsync(id);

            if (user == null)
            {
                return null;
            }
            User retuser = new User() { Id = user.Id, Name = user.Name, Server = user.Server};

            return retuser;
        }

        public async Task<List<OutContact>?> GetContacts(string currentName)
        {
            
            User? user = await UserWithAll(currentName);

            if (user == null)
            {
                return null;
            }

            if (user.Contacts == null)
            {
                return null;
            }

            List<OutContact> contacts = new List<OutContact>();
            user.Contacts.ForEach(x =>
            {
                string? last = null;
                string? lastDate = null;
                Log? l = user.Logs.Find(l => l.stringId == Log.LogId(user.Id, x.Id));
                if (l != null)
                {
                    Message? m = l.Messages.FindLast(m => m.Author == x.Id);
                    if (m != null)
                    {
                        last = m.Content;
                        lastDate = m.Created.ToString("o");
                    }
                }
                contacts.Add(new OutContact() { Id = x.Id, Name = x.Name,  Server = x.Server, Last = last, Lastdate = lastDate});
            });

            return contacts;
        }

        public async Task<OutContact?> GetContact(string id, string currentName)
        {

            User? user = await UserWithAll(currentName);

            if (!(await ContactExists(id, currentName)))
            {
                return null;
            }


            Contact? x = user.Contacts.Find(x => x.Id == id);
            string? last = null;
            string? lastDate = null;
            Log? l = user.Logs.Find(l => l.stringId == Log.LogId(user.Id, x.Id));
            if (l != null)
            {
                Message? m = l.Messages.FindLast(m => m.Author == x.Id);
                if (m != null)
                {
                    last = m.Content;
                    lastDate = m.Created.ToString("o");
                }
            }

            return new OutContact() { Id = x.Id, Name = x.Name, Server = x.Server, Last = last, Lastdate = lastDate };
        }

        public async Task<List<OutMessage>?> GetMessages(string id, string currentName)
        {

            Log? log = await GetLog(id, currentName);
            User? user = await _context.User.FindAsync(currentName);

            if (log == null || log.Messages == null || user == null)
            {
                return null;
            }
            List<OutMessage> msgs = new List<OutMessage>();
            log.Messages.ForEach(x =>
            {
                msgs.Add(new OutMessage() { Content = x.Content, Created = x.Created, Id = x.Id, Sent = x.Author == user.Id });
            });
            return msgs;
        }

        public async Task<bool> AddMessage(string id, string msg, string currentName)
        {
            User? user = await UserWithLogs(currentName);
            
            if (user == null || !(await ContactExists(id, currentName)))
            {
                return false;
            }
            Log? log = await GetLog(id, currentName);

            if (log == null)
            {
                return false;
            }
            if (log.Messages == null)
            {
                log.Messages = new List<Message>();
            }
            /*int nextId = 0;
            if (_context.Message != null && _context.Message.Any())
            {
                nextId = _context.Message.Max(x => x.Id) + 1;
            }*/
            DateTime date = DateTime.Now;
            Message m = new Message()
            {
                Content = msg,
                Created = date,
            };
            //m.Id = nextId;
            m.Log = log;
            m.Author = user.Id;
            log.Messages.Add(m);
            //await _context.Message.AddAsync(m);
            //_context.Entry(m).State = EntityState.Modified;
            _context.Entry(log).State = EntityState.Modified;


            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                throw;
            }

            return true;
        }

        public async Task<bool?> DeleteContact(string id, string currentName)
        {
            if (_context.User == null)
            {
                return null;
            }
            User? user = await UserWithContacts(currentName);
            if (user == null)
            {
                return null;
            }
            if (user.Contacts == null)
            {
                return false;
            }
            int numRemoved = user.Contacts.RemoveAll(x =>
            {
                return x.Id == id;
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

            if (numRemoved == 0)
            {
                return false;
            }

            return true;
        }

        public async Task<OutMessage?> GetMessage(int msgId, string contactId, string currentName)
        {
            if (_context.Message == null)
            {
                return null;
            }
            Log? log = await GetLog(contactId, currentName);
            if (log == null)
            {
                return null;
            }
            Message? msg = log.Messages.Find(m => m.Id == msgId);
            if (msg == null)
            {
                return null;
            }
            return new OutMessage() { Id = msg.Id, Content = msg.Content, Created = msg.Created, Sent = msg.Author == currentName };
        }

        public async Task<bool?> PutMessage(int msgId, string contactId, string content, string currentName)
        {
            if (_context.Message == null)
            {
                return null;
            }
            Log? log = await GetLog(contactId, currentName);
            if (log == null)
            {
                return null;
            }
            Message? msg = log.Messages.Find(m => m.Id == msgId);
            if (msg == null)
            {
                return null;
            }
       
            msg.Content = content;
            
            _context.Entry(msg).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                throw;
            }
            return true;
        }

        public async Task<bool?> Transfer(string from, string to, string content)
        {
            if (_context.User == null)
            {
                return null;
            }
            return await AddMessage(to, content, from);
        }

        public async Task<bool?> Invite(string from, string to, string server)
        {
            if (_context.User == null)
            {
                return null;
            }
            return await AddContact(new Contact() { Id = from, Name = from, Server = server }, to);
        }

        public async Task<bool?> DeleteMessage(int msgId, string contactId, string currentName)
        {
            if (_context.Message == null )
            {
                return null;
            }
            Log? log = await GetLog(contactId, currentName);
            if (log == null)
            {
                return null;
            }
            
            if (log == null || log.Messages.RemoveAll(m => m.Id == msgId) == 0)
            {
                return false;
            }

            _context.Entry(log).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                throw;
            }
            return true;
        }
        // add contact for current
        public async Task<bool?> AddContact(Contact contact, string currentName)
        {
            if (_context.User == null)
            {
                return null;
            }
            User? user = await UserWithContacts(currentName);
            if (user == null || contact == null)
            {
                return false;
            }
            if (user.Contacts == null)
            {
                user.Contacts = new List<Contact>();
            }
            contact.Users.Add(user);
            user.Contacts.Add(contact);
            _context.Entry(user).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                throw;
            }

            return true;
        }


        public async Task<bool?> AddUser(User user)
        {
            if (_context.User == null)
            {
                return null;
            }
            _context.User.Add(user);
            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateException)
            {
                if (UserExists(user.Id))
                {
                    return false;
                }
                else
                {
                    throw;
                }
            }

            return true;
        }

        public async Task<bool?> ValidateUser(LogUser user)
        {
            if (_context.User == null)
            {
                return null;
            }

            if (user == null || !UserExists(user.Id))
            {
                return false;
            }
            var realUser = await _context.User.FindAsync(user.Id);
            if (realUser == null || realUser.Password != user.Password)
            {
                return false;
            }

            return true;
        }

        private bool UserExists(string id)
        {
            return (_context.User?.Any(e => e.Id == id)).GetValueOrDefault();
        }

        private async ValueTask<bool> ContactExists(string id, string currentName)
        {
            User? user = await UserWithContacts(currentName);
            if (user == null || user.Contacts == null)
            {
                return false;
            }
            return user.Contacts.Exists(x =>
            {
                return x.Id == id;
            });
        }


        private async ValueTask<User?> UserWithContacts(string currentName)
        {
            if (_context.User == null)
            {
                return null;
            }
            User? user = await _context.User.FindAsync(currentName);

            if (user == null)
            {
                return null;
            }

            return await _context.User.Include(x => x.Contacts).FirstOrDefaultAsync(u => u.Id == user.Id);
        }

        private async ValueTask<User?> UserWithLogs(string currentName)
        {
            if (_context.User == null)
            {
                return null;
            }
            User? user = await _context.User.FindAsync(currentName);

            if (user == null)
            {
                return null;
            }
            return await _context.User.Include(x => x.Logs).FirstOrDefaultAsync(u => u.Id == user.Id);
        }

        private async ValueTask<User?> UserWithAll(string currentName)
        {
            if (_context.User == null)
            {
                return null;
            }
            User? user = await _context.User.FindAsync(currentName);

            if (user == null)
            {
                return null;
            }
            return await _context.User.Include(x => x.Logs).Include(x => x.Contacts).FirstOrDefaultAsync(u => u.Id == user.Id);
        }

        private async ValueTask<Log?> GetLog(string id, string currentName)
        {
            User? user = await UserWithAll(currentName);

            if (user == null || !(await ContactExists(id, currentName)))
            {
                return null;
            }
            string logId = Log.LogId(user.Id, id);
            Contact? contact;
            if (_context.Contact != null )
            {
                contact = await _context.Contact.FindAsync(id);
                if (contact == null)
                {
                    return null;
                }
            } else
            {
                return null;
            }
            Log? log;
            if (user.Logs == null)
            {
                user.Logs = new List<Log>();
            }
            log = user.Logs.Find(x => x.stringId == logId);
            if (log != null)
            {
                log = await _context.Log.Include(x => x.Messages).FirstOrDefaultAsync(l => l.stringId == logId);
            }
            if (log == null || log.stringId == "")
            {
                log = await _context.Log.Include(x => x.Messages).FirstOrDefaultAsync(l => l.stringId == logId);
                if (log == null || log.stringId == "")
                {
                    log = new Log()
                    {
                        stringId = logId,
                        Messages = new List<Message>(),
                        User = user,
                        Contact = contact
                    };
                }
                user.Logs.Add(log);

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
            
            return log;
        }
    }
}