public class Event extends Task {
    private String fromTime;
    private String toTime;

    public Event(String task, String fromTime, String toTime) {
        super(task);
        this.fromTime = fromTime;
        this.toTime = toTime;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + this.fromTime
                + " to: " + this.toTime + ")";
    }
}
