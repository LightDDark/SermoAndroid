using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Claims;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Domain;
using Repository;
using Services;
using Domain.Out;
using Domain.In;

namespace SermoAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    [Authorize]
    public class ContactsController : ControllerBase
    {
        private readonly UsersService _service;

        public ContactsController(UsersService service)
        {
            _service = service;
        }

        // GET: api/Contacts
        // returns list of contacts
        [HttpGet]
        public async Task<ActionResult<IEnumerable<OutContact>>> GetContacts()
        {
            List<OutContact>? users = await _service.GetContacts(HttpContext.User.Claims.First().Value);
            if (users == null)
            {
                return NotFound();
            }

            return users;
        }

        // GET: api/Contacts/5
        // get contact details 
        [HttpGet("{id}")]
        public async Task<ActionResult<OutContact>> GetContact(string id)
        {
            OutContact? contact = await _service.GetContact(id, HttpContext.User.Claims.First().Value);

            if (contact == null)
            {
                return NotFound();
            }

            return contact;
        }

        // GET: api/Contacts/5/messages
        // get messages
        [HttpGet("{id}/messages")]
        public async Task<ActionResult<IEnumerable<OutMessage>>> GetMessages(string id)
        {
            List<OutMessage>? messages = await _service.GetMessages(id, HttpContext.User.Claims.First().Value);

            if (messages == null)
            {
                return NotFound();
            }

            return messages;
        }

        // Pst: api/Contacts/5/messages
        // post message
        [HttpPost("{id}/messages")]
        public async Task<ActionResult<OutMessage>> AddMessage(string id, [FromBody] InMessage msg)
        {
            bool messages = await _service.AddMessage(id, msg.Content, HttpContext.User.Claims.First().Value);

            if (messages == false)
            {
                return NotFound();
            }

            return Created(id + "messages", msg);
        }

        [HttpDelete("{id}/messages/{id2}")]
        public async Task<IActionResult> DeleteMessage(string id, int id2)
        {
            bool? res = await _service.DeleteMessage(id2, id, HttpContext.User.Claims.First().Value);
            if (res == null || res == false)
            {
                return NotFound();
            }

            return NoContent();
        }

        [HttpGet("{id}/messages/{id2}")]
        public async Task<ActionResult<OutMessage>> GetMessage(string id, int id2)
        {
            OutMessage? res = await _service.GetMessage(id2, id, HttpContext.User.Claims.First().Value);
            if (res == null)
            {
                return NotFound();
            }

            return res;
        }

        [HttpPut("{id}/messages/{id2}")]
        public async Task<IActionResult> PutMessage(string id, int id2, InMessage msg)
        {
            if (msg == null)
            {
                return BadRequest();
            }
            bool? res = await _service.PutMessage(id2, id, msg.Content, HttpContext.User.Claims.First().Value);
            if (res == null || res == false)
            {
                return NotFound();
            }

            return NoContent();
        }

        // PUT: api/Contacts/5
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        // update details of contact with the given id
        [HttpPut("{id}")]
        public async Task<IActionResult> PutContact(string id, InContact contact)
        {
            contact.Id = id;
            bool res = await _service.UpdateContact(contact, HttpContext.User.Claims.First().Value);
            if (res == false)
            {
                return NotFound();
            }

            return NoContent();
        }

        // POST: api/Contacts
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        // add new contact to current user
        [HttpPost]
        public async Task<ActionResult<InContact>> PostContact(InContact contact)
        {
            if (contact.Id == null)
            {
                return ValidationProblem("Id field is requiered.");
            } 

            bool? res = await _service.AddContact(new Contact()
            {
                Id = contact.Id,
                Server = contact.Server,
                Name = contact.Name,
            }, HttpContext.User.Claims.First().Value);
            if (res == null)
            {
                return Problem("Entity set 'WebApplication1Context.User'  is null.");
            }

            if (res == false)
            {
                return BadRequest();
            }

            return RedirectToAction(nameof(GetContact), new { id = contact.Id });
        }

        // DELETE: api/Contacts/5
        // delete id from contacts
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteContact(string id)
        {

            bool? res = await _service.DeleteContact(id, HttpContext.User.Claims.First().Value);
            if (res == null || res == false)
            {
                return NotFound();
            }

            return NoContent();
        }


    }
}
