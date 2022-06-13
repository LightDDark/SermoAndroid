using System;
using Repository;
using Domain.Rate;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace SermoAPP.Views.Rating
{
	public class searchR
	{
		private readonly WebApplication1Context _context;



		public searchR(WebApplication1Context context)
		{
			_context = context;
		}
		public IList<Ratings> ratings { get; set; }
		[BindProperty(SupportsGet = true)]
		public string SearchString { get; set; }

		public async Task OnGetAsync()
		{
			var movies = from m in _context.Ratings
						 select m;
			if (!string.IsNullOrEmpty(SearchString))
			{
				movies = movies.Where(s => s.Content.Contains(SearchString));
			}

			ratings = await _context.Ratings.ToListAsync();
		}
	}
}


