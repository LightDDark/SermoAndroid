using Domain.In;

namespace Domain.Out
{
    public class OutContact : InContact
    {
        public string? Last { get; set; } = null;
        public string? Lastdate { get; set; } = null;
    }
}
