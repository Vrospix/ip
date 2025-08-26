public abstract class Task {
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

    public boolean isDone() {
        return this.done;
    }

    public String getDescription() {
        return this.task;
    }

    public abstract String getType();

    @Override
    public String toString() {
        if (this.done) {
            return "[X] " + this.task;
        } else {
            return "[ ] " + this.task;
        }
    }
}
