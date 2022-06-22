using System.ComponentModel.DataAnnotations;
namespace Domain
{
    public class Log
    {
        [Key]
        public string stringId { get; set; } = "";

        public List<AppMessage> Messages { get; set; } = new List<AppMessage>();

        public User User { get; set; }
        public Contact Contact { get; set; }

        public static string LogId(string name1, string name2)
        {
            int res = string.Compare(name1, name2);
            if (res < 0)
            {
                return name2 + "-" + name1;
            }
            return name1 + "-" + name2;
        }
    }
}
