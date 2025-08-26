public class Event extends Task {
    private String fromTime;
    private String toTime;

    public Event(String task, String fromTime, String toTime) {
        super(task);
        this.fromTime = fromTime;
        this.toTime = toTime;
    }

    public String getFrom() {
        return this.fromTime;
    }

    public String getTo() {
        return this.toTime;
    }

    @Override
    public String getType() {
        return "E";
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + this.fromTime
                + " to: " + this.toTime + ")";
    }
}
