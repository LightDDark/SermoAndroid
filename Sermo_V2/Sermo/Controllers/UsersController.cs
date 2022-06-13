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
using System.IdentityModel.Tokens.Jwt;
using Microsoft.IdentityModel.Tokens;
using System.Text;

namespace SermoAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class UsersController : ControllerBase
    {
        private readonly UsersService _service;
        private readonly IConfiguration _configuration;

        public UsersController(UsersService service, IConfiguration config)
        {
            _service = service;
            _configuration = config;
        }


        // GET: api/Users/5
        [HttpGet("{id}")]
        public async Task<ActionResult<User>> GetUser(string id)
        {
            User? user = await _service.GetUser(id);

            if (user == null)
            {
                return NotFound();
            }

            return user;
        }

        // POST: api/Users
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPost]
        public async Task<ActionResult<InUser>> PostUser(InUser user)
        {
            bool? res = await _service.AddUser(new User() { Id = user.Id, Name = user.Name, Password = user.Password, Server = HttpContext.Request.Host.Value });

            if (res == null)
            {
                return Problem("Entity set 'WebApplication1Context.User'  is null.");
            }
            else if (res == false)
            {
                return Conflict();
            }

            return CreatedAtAction("GetUser", new { id = user.Name }, user);
        }

        [HttpPost("Login")]
        public async Task<IActionResult> LoginUser(LogUser user)
        {
            bool? res = await _service.ValidateUser(user);
            if (res == null)
            {
                return Problem("Entity set 'WebApplication1Context.User'  is null.");

            }
            else if (res == false)
            {
                return NotFound();

            }

            return Ok(Signin(user));
        }


        private string Signin(LogUser account)
        {
            var claims = new[]
            {
                new Claim(ClaimTypes.Name, account.Id),
                new Claim(JwtRegisteredClaimNames.Sub, _configuration["JWTParams:Subject"]),
                new Claim(JwtRegisteredClaimNames.Jti, Guid.NewGuid().ToString()),
                new Claim(JwtRegisteredClaimNames.Iat, DateTime.UtcNow.ToString())
            };

            var secretKey = new SymmetricSecurityKey(Encoding.UTF8.GetBytes(_configuration["JWTParams:SecretKey"]));
            var mac = new SigningCredentials(secretKey, SecurityAlgorithms.HmacSha256);
            var token = new JwtSecurityToken(
                _configuration["JWTParams:Issuer"],
                _configuration["JWTParams:Audience"],
                claims,
                expires: DateTime.UtcNow.AddMinutes(20),
                signingCredentials: mac);
            return new JwtSecurityTokenHandler().WriteToken(token);
        }
    }
}