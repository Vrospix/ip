package falco.storage;

import falco.exception.FalcoException;
import falco.task.Task;

import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

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
     * Reset the list back to empty
     */
    public void resetList() {
        this.tasks = new ArrayList<>();
    }


    /**
     * Insert a task inside the list
     *
     * @param task
     */
    public void insertList(Task task) {
        tasks.add(task);
    }

    /**
     * Get task-'i' from list
     *
     * @param i
     * @return a task from the list
     */
    public Task getTask(int i) throws RuntimeException {
        return tasks.get(i);
    }

    /**
     * Delete the designated task-'i' from the list
     *
     * @param i
     * @throws FalcoException
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
     * Mark or Unmark the specific task in list
     *
     * @param i
     * @throws FalcoException
     */
    public void markTask(int i) throws RuntimeException {
        getTask(i).mark();
    }

    public void unmarkTask(int i) throws RuntimeException {
        getTask(i).unmark();
    }

}
