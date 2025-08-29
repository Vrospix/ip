package falco.task;
/**
 * Represents a task that has no time.
 */
public class Todo extends Task {

    /**
     * Creates an instance of <code>Todo</code> with task description
     *
     * @param task Task description
     */
    public Todo(String task) {
        super(task);
    }

    @Override
    public String getType() {
        return "T";
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
