package falco.interact;

import java.io.IOException;

import falco.exception.FalcoException;
import falco.storage.Storage;
import falco.storage.TaskList;
import falco.task.Deadline;
import falco.task.Event;
import falco.task.Task;
import falco.task.Todo;

/**
 * Parses the command input by user.
 */
public class ParserGUI {
    private TaskList tasks;
    private Storage storage;
    private UiForGUI ui = new UiForGUI();

    /**
     * Create an instance of <code>Parser</code> with corresponding
     * <code>TaskList</code> and <code>Storage</code>.
     * @param tasks List of tasks
     * @param storage Storage of the list of tasks
     */
    public ParserGUI(TaskList tasks, Storage storage) {
        this.tasks = tasks;
        this.storage = storage;
    }

    /**
     * Transform all the <code>Tasks</code> in the <code>TaskList</code> into String.
     * Then asks <code>Ui</code> to print the String.
     * If <code>TaskList</code> is empty, throw out a <code>FalcoException</code>.
     *
     * @throws FalcoException If tasklist is empty
     */
    public String executeList() throws FalcoException {
        int n = tasks.getSize();
        if (n == 0) {
            tasks.throwEmptyList();
        }
        return ui.printList(tasks.printList());
    }

    /**
     * Find all the <code>Task</code> in the <code>TaskList</code> that has the "keyword"
     * and print it out.
     * <p>
     * If input is unclear, throws a <code>FalcoException</code>
     *
     * @param input Keyword input
     * @throws FalcoException If input is unclear
     */
    public String executeFind(String input) throws FalcoException {
        String[] parts = input.split(" ", 2);
        if (parts.length == 1) {
            throw new FalcoException(FalcoException.ErrorType.UNCLEAR_FIND);
        }
        String keyword = parts[1];
        String foundList = tasks.findKeyword(keyword);
        return ui.findList(foundList);
    }

    /**
     * Reset all the <code>Tasks</code> in <code>TaskList</code>.
     * If save process fails, throws out an <code>IOException</code>.
     *
     * @throws FalcoException If save process fails
     */
    public String executeReset() throws IOException {
        tasks.resetList();
        storage.save(tasks);
        return ui.resetListDone();
    }

    /**
     * Delete a <code>Task</code> from the <code>TaskList</code>.
     * If input fails/unclear, throws out a <code>FalcoException</code>.
     * If save process fails, throws out an <code>IOException</code>.
     *
     * @param input String input from user
     * @throws FalcoException if input fails/unclear
     * @throws IOException if save process fails
     */
    public String executeDelete(String input) throws FalcoException, IOException {
        String[] parts = input.split(" ");
        if (parts.length == 1) {
            throw new FalcoException(FalcoException.ErrorType.UNCLEAR_DELETE);
        }
        try {
            int index = Integer.parseInt(parts[1]) - 1;
            Task removedTask = tasks.getTask(index);
            tasks.deleteTask(index);
            storage.save(tasks);
            return ui.deleteTaskDone(tasks, removedTask);
        } catch (Exception e) {
            throw new FalcoException(FalcoException.ErrorType.UNKNOWN_COMMAND);
        }

    }

    /**
     * Mark the designated <code>Task</code> inside the <code>TaskList</code>
     * If input fails/unclear, throws out a <code>FalcoException</code>.
     * If save process fails, throws out an <code>IOException</code>.
     *
     * @param input String input from user
     * @throws FalcoException if input fails/unclear
     * @throws IOException if save process fails
     */
    public String executeMark(String input) throws FalcoException, IOException {
        String[] parts = input.split(" ");
        if (parts.length == 1) {
            throw new FalcoException(FalcoException.ErrorType.UNCLEAR_MARK);
        }
        try {
            int index = Integer.parseInt(parts[1]) - 1;
            tasks.markTask(index);
            storage.save(tasks);
            return ui.markTaskDone(tasks.getTask(index));
        } catch (RuntimeException e) {
            throw new FalcoException(FalcoException.ErrorType.OUTOFBOUNDS);
        }
    }

    /**
     * Unmark the designated <code>Task</code> inside the <code>TaskList</code>
     * If input fails/unclear, throws out a <code>FalcoException</code>.
     * If save process fails, throws out an <code>IOException</code>.
     *
     * @param input String input from user
     * @throws FalcoException If input fails/unclear
     * @throws IOException If save process fails
     */
    public String executeUnmark(String input) throws FalcoException, IOException {
        String[] parts = input.split(" ");
        if (parts.length == 1) {
            throw new FalcoException(FalcoException.ErrorType.UNCLEAR_MARK);
        }
        try {
            int index = Integer.parseInt(parts[1]) - 1;
            tasks.unmarkTask(index);
            storage.save(tasks);
            return ui.unmarkTaskDone(tasks.getTask(index));
        } catch (RuntimeException e) {
            throw new FalcoException(FalcoException.ErrorType.OUTOFBOUNDS);
        }
    }

    /**
     * Create a new <code>Deadline</code> task and store it inside <code>TaskList</code>.
     * If input fails/unclear, throws out a <code>FalcoException</code>.
     * If save process fails, throws out an <code>IOException</code>.
     *
     * @param input String input from user
     * @throws FalcoException If input fails/unclear
     * @throws IOException If save process fails
     */
    public String createDeadline(String input) throws FalcoException, IOException {
        String[] parts = input.split(" ", 2);
        if (parts.length == 1) {
            throw new FalcoException(FalcoException.ErrorType.EMPTY_TASK);
        }
        String remaining = parts[1];

        String[] details = remaining.split("/by", 2);
        if (details.length == 1 || details[1].isBlank()) {
            throw new FalcoException(FalcoException.ErrorType.NOTIME_DEADLINE);
        }
        String desc = details[0].trim();
        if (desc.isBlank()) {
            throw new FalcoException(FalcoException.ErrorType.EMPTY_TASK);
        }
        assert !desc.isBlank() : "desc should not be empty";
        
        String time = details[1].trim();

        Task task = new Deadline(desc, time);

        return insertSave(tasks, task);
    }

    /**
     * Create a new <code>Event</code> task and store it inside <code>TaskList</code>.
     * If input fails/unclear, throws out a <code>FalcoException</code>.
     * If save process fails, throws out an <code>IOException</code>.
     *
     * @param input String input from user
     * @throws FalcoException If input fails/unclear
     * @throws IOException If save process fails
     */
    public String createEvent(String input) throws FalcoException, IOException {
        String[] parts = input.split(" ", 2);
        if (parts.length == 1) {
            throw new FalcoException(FalcoException.ErrorType.EMPTY_TASK);
        }
        String remaining = parts[1];

        String[] details = remaining.split("/from", 2);
        if (details.length == 1) {
            throw new FalcoException(FalcoException.ErrorType.UNCLEAR_EVENT);
        }

        String desc = details[0].trim();
        if (desc.isBlank()) {
            throw new FalcoException(FalcoException.ErrorType.EMPTY_TASK);
        }
        assert !desc.isBlank() : "desc should not be empty";

        String time = details[1];
        String[] spantime = time.split("/to", 2);
        if (spantime.length == 1 || spantime[1].isBlank()) {
            throw new FalcoException(FalcoException.ErrorType.UNCLEAR_EVENT);
        }
        String from = spantime[0].trim();
        String to = spantime[1].trim();

        Task task = new Event(desc, from, to);

        return insertSave(tasks, task);
    }

    /**
     * Create a new <code>ToDo</code> task and store it inside <code>TaskList</code>.
     * If input fails/unclear, throws out a <code>FalcoException</code>.
     * If save process fails, throws out an <code>IOException</code>.
     *
     * @param input String input from user
     * @throws FalcoException If input fails/unclear
     * @throws IOException If save process fails
     */
    public String createTodo(String input) throws FalcoException, IOException {
        String[] parts = input.split(" ", 2);
        if (parts.length == 1) {
            throw new FalcoException(FalcoException.ErrorType.EMPTY_TASK);
        }
        String desc = parts[1].trim();

        if (desc.isBlank()) {
            throw new FalcoException(FalcoException.ErrorType.EMPTY_TASK);
        }
        assert !desc.isBlank() : "desc should not be empty";

        Task task = new Todo(desc);

        return insertSave(tasks, task);
    }

    /**
     * Save the <code>Task</code> inside <code>TaskList</code>.
     * Save the list to the <code>Storage</code> as well.
     * If save process fails, throws out an <code>IOException</code>.
     *
     * @param tasks List of tasks
     * @param task A specific task
     * @throws IOException if save process fails
     */
    public String insertSave(TaskList tasks, Task task) throws IOException {
        tasks.insertList(task);
        storage.save(tasks);
        return ui.insertListDone(tasks, task);
    }

    /**
     * Break down the input from user and execute function based on input
     *
     * @param text input from user
     */
    public String parse(String text) {
        String input = text;
        try {
            if (input.equalsIgnoreCase("list")) {
                return executeList();
            } else if (input.equalsIgnoreCase("reset")) {
                return executeReset();
            } else if (input.equalsIgnoreCase("help")) {
                return ui.helpUser();
            } else if (input.toLowerCase().startsWith("find")) {
                return executeFind(input);
            } else if (input.toLowerCase().startsWith("delete")) {
                return executeDelete(input);
            } else if (input.toLowerCase().startsWith("mark")) {
                return executeMark(input);
            } else if (input.toLowerCase().startsWith("unmark")) {
                return executeUnmark(input);
            } else if (input.toLowerCase().startsWith("deadline")) {
                return createDeadline(input);
            } else if (input.toLowerCase().startsWith("event")) {
                return createEvent(input);
            } else if (input.toLowerCase().startsWith("todo")) {
                return createTodo(input);
            } else if (input.equalsIgnoreCase("bye")) {
                return ui.sayGoodbye();
            } else {
                throw new FalcoException(FalcoException.ErrorType.UNKNOWN_COMMAND);
            }
        } catch (FalcoException e) {
            return ui.bordify(e.getMessage());
        } catch (IOException e) {
            return ui.showSavingError();
        }
    }
}
