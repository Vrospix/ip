public class Task {
    private boolean done;
    private final String task;

    public Task(String task) {
        this.done = false;
        this.task = task;
    }

    public void mark() {
        this.done = true;
    }

    public void unmark() {
        this.done = false;
    }

    @Override
    public String toString() {
        if (this.done) {
            return "[X] " + this.task;
        } else {
            return "[ ] " + this.task;
        }
    }
}
