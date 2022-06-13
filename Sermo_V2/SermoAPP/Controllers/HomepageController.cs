using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;

namespace SermoAPP.Views.Sermo
{
    public class HomepageController : Controller
    {
        public IActionResult Index()
        {
            return View();
        }
    }
}