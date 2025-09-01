package falco.storage;

import java.util.ArrayList;

import falco.exception.FalcoException;
import falco.task.Task;

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

    /**
     * Throws a <code>FalcoException</code> directly
     *
     * @throws FalcoException
     */
    public void throwEmptyList() throws FalcoException {
        throw new FalcoException(FalcoException.ErrorType.EMPTY_LIST);
    }

    /**
     * Throws a <code>FalcoException</code> directly
     *
     * @throws FalcoException
     */
    public void throwNotFindList() throws FalcoException {
        throw new FalcoException(FalcoException.ErrorType.NOT_FOUND);
    }

    /**
     * Finds all the tasks containing the "keyword"
     * @param keyword
     * @return
     * @throws FalcoException
     */
    public String findKeyword(String keyword) throws FalcoException {
        if (tasks.isEmpty()) {
            throwEmptyList();
        }
        TaskList copyList = new TaskList();
        for (int i = 0; i < getSize(); i++) {
            Task task = getTask(i);
            String taskDescAny = task.getDescription().toLowerCase();
            if (taskDescAny.contains(keyword.toLowerCase())) {
                copyList.insertList(task);
            }
        }


        int size = copyList.getSize();
        if (size == 0) {
            throwNotFindList();
        }
        StringBuilder message = new StringBuilder("1." + copyList.getTask(0).toString());
        for (int i = 1; i < size; i++) {
            message.append("\n" + (i + 1) + "." + copyList.getTask(i).toString());
        }
        return message.toString();
    }

    /**
     * Turns all the <code>Task</code> in the <code>TaskList</code> into set of strings.
     * <p>
     * If <code>TaskList</code> is empty, throws an <code>FalcoException</code>.
     *
     * @return String
     * @throws FalcoException If <code>TaskList</code> is empty
     */
    public String printList() throws FalcoException {
        if (getSize() == 0) {
            throwEmptyList();
        }
        StringBuilder message = new StringBuilder("1." + getTask(0).toString());
        for (int i = 1; i < getSize(); i++) {
            message.append("\n" + (i + 1) + "." + getTask(i).toString());
        }

        return message.toString();
    }

    /**
     * Return the amount of tasks inside <code>TaskList</code>
     *
     * @return <code>int</code>
     */
    public int getSize() {
        return tasks.size();
    }

    /**
     * Return the arraylist that contains the tasks
     *
     * @return <code>ArrayList</code> Task
     */
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
            throwEmptyList();
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
