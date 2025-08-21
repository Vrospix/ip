public class Deadline extends Task {
    private String bytime;

    public Deadline(String task, String bytime) {
        super(task);
        this.bytime = bytime;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.bytime + ")";
    }
}
