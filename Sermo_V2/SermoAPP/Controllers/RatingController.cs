using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Repository;
using Domain.Rate;

namespace SermoAPP.Controllers
{
    public class RatingController : Controller
    {
        private readonly WebApplication1Context _context;

        public RatingController(WebApplication1Context context)
        {
            _context = context;
        }

        // GET: Ratings
        public async Task<IActionResult> Index()
        {
            return _context.Ratings != null ?
                        View(await _context.Ratings.ToListAsync()) :
                        Problem("Entity set 'WebApplication1Context.Ratings'  is null.");
        }

        // GET: Ratings/Details/5
        public async Task<IActionResult> Details(int? id)
        {
            if (id == null || _context.Ratings == null)
            {
                return NotFound();
            }

            var ratings = await _context.Ratings
                .FirstOrDefaultAsync(m => m.Id == id);
            if (ratings == null)
            {
                return NotFound();
            }

            return View(ratings);
        }

        // GET: Ratings/Create
        public IActionResult Create()
        {
            return View();
        }

        // POST: Ratings/Create
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Create([Bind("Id,Author,Content,Stars,dateTime")] Ratings ratings)
        {
            if (ModelState.IsValid)
            {
                ratings.dateTime = DateTime.Now;
                _context.Ratings.Add(ratings);
                await _context.SaveChangesAsync();
                return RedirectToAction(nameof(Index));
            }
            return View(ratings);
        }

        // GET: Ratings/Edit/5
        public async Task<IActionResult> Edit(int? id)
        {
            if (id == null || _context.Ratings == null)
            {
                return NotFound();
            }

            var ratings = await _context.Ratings.FindAsync(id);
            if (ratings == null)
            {
                return NotFound();
            }
            return View(ratings);
        }

        // POST: Ratings/Edit/5
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Edit(int id, [Bind("Id,Author,Content,Stars")] Ratings ratings)
        {
            if (id != ratings.Id)
            {
                return NotFound();
            }

            if (ModelState.IsValid)
            {
                try
                {
                    _context.Ratings.Update(ratings);
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!RatingsExists(ratings.Id))
                    {
                        return NotFound();
                    }
                    else
                    {
                        throw;
                    }
                }
                return RedirectToAction(nameof(Index));
            }
            return View(ratings);
        }

        // GET: Ratings/Delete/5
        public async Task<IActionResult> Delete(int? id)
        {
            if (id == null || _context.Ratings == null)
            {
                return NotFound();
            }

            var ratings = await _context.Ratings
                .FirstOrDefaultAsync(m => m.Id == id);
            if (ratings == null)
            {
                return NotFound();
            }

            return View(ratings);
        }

        // POST: Ratings/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> DeleteConfirmed(int id)
        {
            if (_context.Ratings == null)
            {
                return Problem("Entity set 'WebApplication1Context.Ratings'  is null.");
            }
            var ratings = await _context.Ratings.FindAsync(id);
            if (ratings != null)
            {
                _context.Ratings.Remove(ratings);
            }

            await _context.SaveChangesAsync();
            return RedirectToAction(nameof(Index));
        }

        private bool RatingsExists(int id)
        {
            return (_context.Ratings?.Any(e => e.Id == id)).GetValueOrDefault();
        }
    }
}
