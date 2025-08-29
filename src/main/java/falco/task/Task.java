package falco.task;

/**
 * An abstract class that represents a 'Task'
 */
public abstract class Task {
    private boolean isDone;
    private String description;

    /**
     * Initialize <code>Task</code> as not done and store the task description.
     *
     * @param description Task description
     */
    public Task(String description) {
        this.isDone = false;
        this.description = description;
    }

    /**
     * Set the task as done.
     */
    public void mark() {
        this.isDone = true;
    }

    /**
     * Set the task as not done.
     */
    public void unmark() {
        this.isDone = false;
    }

    /**
     * Check whether the task is done or not.
     *
     * @return <code>True</code> if task is done; otherwise, <code>False</code>
     */
    public boolean isDone() {
        return this.isDone;
    }

    /**
     * Get the description of the task.
     *
     * @return <code>description</code>
     */
    public String getDescription() {
        return this.description;
    }
    
    /**
     * Change the description of the task.
     *
     * @param newDesc New description task
     */
    public void setDescription(String newDesc) {
        this.description = newDesc;
    }

    /**
     * Get the type of task.
     *
     * @return the inital letter of the <code>Task</code>
     */
    public abstract String getType();

    @Override
    public String toString() {
        if (this.isDone) {
            return "[X] " + this.description;
        } else {
            return "[ ] " + this.description;
        }
    }
}
