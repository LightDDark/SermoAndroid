using System;
using System.ComponentModel.DataAnnotations;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;

namespace Domain.Rate
{
	public class Ratings
	{
		[Key]

		public int Id { get; set; }
		public string? Author { get; set; }
		public string? Content { get; set; }
		public int Stars { get; set; }
		public DateTime dateTime { get; set; }

	}
}

