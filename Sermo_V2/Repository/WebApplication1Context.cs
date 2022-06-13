using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using Domain;
using Domain.Rate;
using Domain.Hub;



namespace Repository
{
    public class WebApplication1Context : DbContext
    {
        public WebApplication1Context (DbContextOptions<WebApplication1Context> options)
            : base(options)
        {
        }

        public DbSet<User>? User { get; set; }

        public DbSet<Log>? Log { get; set; }

        public DbSet<Message>? Message { get; set; }

        public DbSet<Contact>? Contact { get; set; }

        public DbSet<Ratings>? Ratings { get; set; }

        public DbSet<Connection>? Connections { get; set; }

    }
}
