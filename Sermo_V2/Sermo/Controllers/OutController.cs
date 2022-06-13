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
using Repository;
using Services;
using Domain.Out;
using Domain.In;

namespace SermoAPI.Controllers
{
    [Route("api/")]
    [ApiController]
    public class OutController : ControllerBase
    {
        private readonly UsersService _service;

        public OutController(UsersService service)
        {
            _service = service;
        }


        // GET: api/Users/5
        [HttpPost("invitations")]
        public async Task<ActionResult<User>> PostInvite(Invite msg)
        {
            if (msg.Server == null)
            {
                return ValidationProblem("server field is requierd.");
            }
            bool? res = await _service.Invite(msg.From, msg.To, msg.Server);

            if (res == null)
            {
                return Problem("Entity set 'WebApplication1Context.User'  is null.");
            }
            else if (res == false)
            {
                return Conflict();
            }

            return StatusCode(201);
        }
    

        // POST: api/Users
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPost("transfer")]
        public async Task<ActionResult<User>> PostMessage(Transfer msg)
        {
            
            if (msg.Content == null)
            {
                return ValidationProblem("content field is requierd.");
            }

            bool? res = await _service.Transfer(msg.From, msg.To, msg.Content);

            if (res == null)
            {
                return Problem("Entity set 'WebApplication1Context.User'  is null.");
            }
            else if (res == false)
            {
                return Conflict();
            }

            return StatusCode(201);
        }
    }
}