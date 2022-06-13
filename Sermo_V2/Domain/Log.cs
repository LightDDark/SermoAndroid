using System.ComponentModel.DataAnnotations;
namespace Domain
{
    public class Log
    {
        [Key]
        public string stringId { get; set; } = "";

        public List<Message> Messages { get; set; } = new List<Message>();

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
