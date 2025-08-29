package falco.storage;

import falco.exception.FalcoException;
import falco.task.Deadline;
import falco.task.Event;
import falco.task.Task;
import falco.task.Todo;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents a <code>storage</code> to save a <code>TaskList</code>.
 */
public class Storage {
    private String filePath;

    /**
     * Creates an instance of <code>Storage</code> with the designated file path.
     * @param filePath
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Read, transform, and load the saved file in the file path.
     * If fails to create a new file, throws an <code>IOException</code>.
     * If fails to turn text to a task, throws a <code>FalcoException</code>.
     *
     * @return An arrayList of tasks
     * @throws IOException if fails to create new file
     * @throws FalcoException if fails to turn text to a task
     */
    public ArrayList<Task> load() throws IOException, FalcoException {
        File file = new File(filePath);
        if (!file.exists()) {
            file.getParentFile().mkdirs(); // Create ./data if not exists
            file.createNewFile();
        }
        ArrayList<Task> taskList = new ArrayList<>();
        Scanner s = new Scanner(file);
        while(s.hasNext()) {
            taskList.add(turnTextToTask(s.nextLine()));
        }
        return taskList;
    }

    /**
     * Turns the specific text into an instance of <code>Task</code>.
     * If text is formatted wrong, throws a <code>FalcoException</code>.
     *
     * @param text Text that represents a task
     * @return Task
     * @throws FalcoException If text is formatted wrong
     */
    public Task turnTextToTask(String text) throws FalcoException {
        String[] parts = text.split("\\|", 5);
        Task task;
        if (parts[0].trim().equals("D")) {
            task = new Deadline(parts[2].trim(), parts[3].trim());
        } else if (parts[0].trim().equals("E")) {
            String[] times = parts[3].trim().split("-", 2);
            task = new Event(parts[2].trim(), times[0].trim(), times[1].trim());
        } else if (parts[0].trim().equals("T")) {
            task = new Todo(parts[2].trim());
        } else {
            throw new FalcoException(FalcoException.ErrorType.UNKNOWN_COMMAND);
        }

        if (parts[1].trim().equalsIgnoreCase("1")) {
            task.mark();
        }

        return task;
    }

    /**
     * Save the <code>TaskList</code> to the designated file.
     * If save process goes wrong, throws an <code>IOException</code>.
     *
     * @param tasks List of tasks
     * @throws IOException If save process goes wrong
     */
    public void save(TaskList tasks) throws IOException {
        ArrayList<Task> taskList = tasks.getList();
        FileWriter fw = new FileWriter(this.filePath, false);
        for(Task task : taskList) {
            String text = turnTaskToText(task);
            fw.append(text + "\n");
        }
        fw.close();
    }

    /**
     * Turn the corresponding <code>task</code> into a text format.
     *
     * @param task A specific task
     * @return String of a task
     */
    public String turnTaskToText(Task task) {
        String message = task.getType();

        if (task.isDone()) {
            message = message + " | 1 | ";
        } else {
            message = message + " | 0 | ";
        }

        if (task instanceof Deadline) {
            message = message + task.getDescription() + " | " + ((Deadline) task).getBytime();
        } else if (task instanceof Event) {
            message = message + task.getDescription() + " | " + ((Event) task).getFrom() + " - " + ((Event) task).getTo();
        } else {
            message =  message + task.getDescription();
        }

        return message;
    }
}
