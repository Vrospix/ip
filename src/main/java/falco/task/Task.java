package falco.task;

public abstract class Task {
    private boolean isDone;
    private String task;

    public Task(String task) {
        this.isDone = false;
        this.task = task;
    }

    public void mark() {
        this.isDone = true;
    }

    public void unmark() {
        this.isDone = false;
    }

    public boolean isDone() {
        return this.isDone;
    }

    public String getDescription() {
        return this.task;
    }

    public void changeDescription(String newDesc) {
        this.task = newDesc;
    }

    public abstract String getType();

    @Override
    public String toString() {
        if (this.isDone) {
            return "[X] " + this.task;
        } else {
            return "[ ] " + this.task;
        }
    }
}
