package falco.storage;

import falco.exception.FalcoException;
import falco.task.Task;

import java.util.ArrayList;

/**
 * Acts as a list for tasks.
 */
public class TaskList {
    private ArrayList<Task> tasks;

    /**
     * Creates an instance of <code>TaskList</code>
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Creates an instance of <code>TaskList</code> that contains a preset list of tasks.
     *
     * @param storageload A premade ArrayList of tasks
     */
    public TaskList(ArrayList<Task> storageload) {
        this.tasks = storageload;
    }

    public int getSize() {
        return tasks.size();
    }

    public ArrayList<Task> getTasks() {
        return this.tasks;
    }

    /**
     * Reset the list back to empty.
     */
    public void resetList() {
        this.tasks = new ArrayList<>();
    }


    /**
     * Insert a task inside the list.
     *
     * @param task A specific task
     */
    public void insertList(Task task) {
        tasks.add(task);
    }

    /**
     * Get task-'i' from list.
     * If 'i' is out of bound, throws a <code>RuntimeException</code>.
     *
     * @param i The task number in the list
     * @return a task from the list
     * @throws RuntimeException If 'i' is out of bound
     */
    public Task getTask(int i) throws RuntimeException {
        return tasks.get(i);
    }

    /**
     * Delete the designated task-'i' from the list.
     * If list is empty, throws a <code>FalcoException</code>.
     *
     * @param i The task number in the list
     * @throws FalcoException If list is empty
     */
    public void deleteTask(int i) throws FalcoException {
        if (tasks.isEmpty()) {
            throw new FalcoException(FalcoException.ErrorType.EMPTY_LIST);
        } else {
            try {
                tasks.remove(i);
            } catch (Exception e) {
                throw new FalcoException(FalcoException.ErrorType.OUTOFBOUNDS);
            }
        }
    }

    /**
     * Mark the designated task in the list.
     * If 'i' is out of bound, throws a <code>RuntimeException</code>.
     *
     * @param i The task number in the list
     * @throws RuntimeException If 'i' is out of bound
     */
    public void markTask(int i) throws RuntimeException {
        getTask(i).mark();
    }

    /**
     * Unmark the designated task in the list.
     * If 'i' is out of bound, throws a <code>RuntimeException</code>.
     *
     * @param i The task number in the list
     * @throws RuntimeException If 'i' is out of bound
     */
    public void unmarkTask(int i) throws RuntimeException {
        getTask(i).unmark();
    }

}
