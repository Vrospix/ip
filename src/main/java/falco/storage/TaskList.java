package falco.storage;

import falco.exception.FalcoException;
import falco.task.Task;
import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> list;

    public TaskList() {
        this.list = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> storageload) {
        this.list = storageload;
    }

    public void throwEmptyList() throws FalcoException {
        throw new FalcoException(FalcoException.ErrorType.EMPTY_LIST);
    }

    public String findKeyword(String keyword) throws FalcoException {
        if (list.isEmpty()) {
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
        StringBuilder message = new StringBuilder("Sir, here are the matching tasks in your list: (￣^￣ )ゞ");
        int size = copyList.getSize();
        for (int i = 0; i < size; i++) {
            message.append("\n" + (i + 1) + "." + copyList.getTask(i).toString());
        }
        return message.toString();
    }

    public String printList() {
        StringBuilder message = new StringBuilder("Sir, here are the tasks in your list: (￣^￣ )ゞ");
        for (int i = 0; i < getSize(); i++) {
            message.append("\n" + (i + 1) + "." + getTask(i).toString());
        }
        return message.toString();
    }

    public int getSize() {
        return list.size();
    }

    public ArrayList<Task> getList(){
        return this.list;
    }

    /**
     * Reset the list back to empty
     */
    public void resetList() {
        this.list = new ArrayList<>();
    }


    /**
     * Insert a task inside the list
     *
     * @param task
     */
    public void insertList(Task task) {
        list.add(task);
    }

    /**
     * Get task-'i' from list
     *
     * @param i
     * @return a task from the list
     */
    public Task getTask(int i) throws RuntimeException {
        return list.get(i);
    }

    /**
     * Delete the designated task-'i' from the list
     *
     * @param i
     * @throws FalcoException
     */
    public void deleteTask(int i) throws FalcoException {
        if (list.isEmpty()) {
            throwEmptyList();
        } else {
            try {
                list.remove(i);
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
