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
public class Parser {
    private TaskList tasks;
    private Storage storage;
    private Ui ui = new Ui();

    /**
     * Create an instance of <code>Parser</code> with corresponding
     * <code>TaskList</code> and <code>Storage</code>.
     * @param tasks List of tasks
     * @param storage Storage of the list of tasks
     */
    public Parser(TaskList tasks, Storage storage) {
        this.tasks = tasks;
        this.storage = storage;
    }

    /**
     * Split the first word in input with the rest
     * If input only contains one word, throw out a <code>FalcoException</code>.
     *
     * @param input String input from user
     * @param errorType The errorType to throw if exception is caught
     * @return Array of strings
     * @throws FalcoException If input only contains one word
     */
    private String[] splitInput(String input, FalcoException.ErrorType errorType) throws FalcoException {
        String[] parts = input.split(" ");
        if (parts.length == 1) {
            throw new FalcoException(errorType);
        }
        return parts;
    }

    /**
     * Transform the string integer into an integer data type
     * If input string is invalid, throw out a <code>FalcoException</code>.
     *
     * @param input String input from user
     * @return Integer
     * @throws FalcoException If input string is invalid
     */
    private int transformInputToInt(String input) throws FalcoException {
        try {
            int index = Integer.parseInt(input) - 1;
            return index;
        } catch (Exception e) {
            throw new FalcoException(FalcoException.ErrorType.UNKNOWN_COMMAND);
        }
    }

    /**
     * Transform all the <code>Tasks</code> in the <code>TaskList</code> into String.
     * Then asks <code>Ui</code> to print the String.
     * If <code>TaskList</code> is empty, throw out a <code>FalcoException</code>.
     *
     * @throws FalcoException If tasklist is empty
     */
    private void executeList() throws FalcoException {
        int n = tasks.getSize();
        if (n == 0) {
            tasks.throwEmptyList();
        }

        String message = tasks.printList();
        ui.printList(message);
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
    private void executeFind(String input) throws FalcoException {
        String[] parts = splitInput(input, FalcoException.ErrorType.UNCLEAR_FIND);
        String keyword = parts[1];
        String foundList = tasks.findKeyword(keyword);
        ui.findList(foundList);
    }

    /**
     * Reset all the <code>Tasks</code> in <code>TaskList</code>.
     * If save process fails, throws out an <code>IOException</code>.
     *
     * @throws FalcoException If save process fails
     */
    private void executeReset() throws IOException {
        tasks.resetList();
        ui.resetListDone();
        storage.save(tasks);
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
    private void executeDelete(String input) throws FalcoException, IOException {
        String[] parts = splitInput(input, FalcoException.ErrorType.UNCLEAR_DELETE);
        int index = transformInputToInt(parts[1]);
        Task removedTask = tasks.getTask(index);
        tasks.deleteTask(index);
        ui.deleteTaskDone(tasks, removedTask);
        storage.save(tasks);
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
    private void executeMark(String input) throws FalcoException, IOException {
        String[] parts = splitInput(input, FalcoException.ErrorType.UNCLEAR_MARK);
        int index = transformInputToInt(parts[1]);
        tasks.markTask(index);
        ui.markTaskDone(tasks.getTask(index));
        storage.save(tasks);
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
    private void executeUnmark(String input) throws FalcoException, IOException {
        String[] parts = splitInput(input, FalcoException.ErrorType.UNCLEAR_MARK);
        int index = transformInputToInt(parts[1]);
        tasks.unmarkTask(index);
        ui.unmarkTaskDone(tasks.getTask(index));
        storage.save(tasks);
    }

    /**
     * Split the input for <code>Deadline</code> task creation
     * If input fails/unclear, throws out a <code>FalcoException</code>.
     *
     * @param input String input from user
     * @return Array of strings
     * @throws FalcoException If input fails/unclear
     */
    private String[] splitDeadlineInput(String input) throws FalcoException {
        String[] details = input.split("/by", 2);
        if (details.length == 1 || details[1].isBlank()) {
            throw new FalcoException(FalcoException.ErrorType.NOTIME_DEADLINE);
        }
        return details;
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
    private void createDeadline(String input) throws FalcoException, IOException {
        String[] parts = splitInput(input, FalcoException.ErrorType.EMPTY_TASK);

        String remaining = parts[1];

        String[] details = splitDeadlineInput(remaining);

        String desc = details[0].trim();
        if (desc.isBlank()) {
            throw new FalcoException(FalcoException.ErrorType.EMPTY_TASK);
        }
        assert !desc.isBlank() : "desc should not be empty";

        String time = details[1].trim();

        Task task = new Deadline(desc, time);

        insertSave(tasks, task);
    }

    /**
     * Split the input for <code>Event</code> task creation
     * If input fails/unclear, throws out a <code>FalcoException</code>.
     *
     * @param input String input from user
     * @return Array of strings
     * @throws FalcoException If input fails/unclear
     */
    private String[] splitEventInput(String input) throws FalcoException {
        String[] result = new String[3];
        String[] details = input.split("/from", 2);
        if (details.length == 1 || details[1].isBlank()) {
            throw new FalcoException(FalcoException.ErrorType.UNCLEAR_EVENT);
        }
      
        result[0] = details[0].trim();
      
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

        result[1] = from;
        result[2] = to;

        return result;
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
    private void createEvent(String input) throws FalcoException, IOException {
        String[] parts = splitInput(input, FalcoException.ErrorType.EMPTY_TASK);

        String remaining = parts[1];

        String[] details = splitEventInput(remaining);

        String desc = details[0];
        String from = details[1];
        String to = details[2];

        Task task = new Event(desc, from, to);

        insertSave(tasks, task);
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
    private void createTodo(String input) throws FalcoException, IOException {
        String[] parts = splitInput(input, FalcoException.ErrorType.EMPTY_TASK);

        String desc = parts[1].trim();

        if (desc.isBlank()) {
            throw new FalcoException(FalcoException.ErrorType.EMPTY_TASK);
        }
        assert !desc.isBlank() : "desc should not be empty";

        Task task = new Todo(desc);

        insertSave(tasks, task);
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
    private void insertSave(TaskList tasks, Task task) throws IOException {
        tasks.insertList(task);
        ui.insertListDone(tasks, task);
        storage.save(tasks);
    }

    /**
     * Break down the input from user and execute function based on input
     *
     * @param text input from user
     */
    public void parse(String text) {
        String input = text.toLowerCase().trim();
        while (!input.equalsIgnoreCase("bye")) {
            try {
                if (input.equalsIgnoreCase("list")) {
                    executeList();
                } else if (input.equalsIgnoreCase("reset")) {
                    executeReset();
                } else if (input.startsWith("find")) {
                    executeFind(input);
                } else if (input.startsWith("delete")) {
                    executeDelete(input);
                } else if (input.startsWith("mark")) {
                    executeMark(input);
                } else if (input.startsWith("unmark")) {
                    executeUnmark(input);
                } else if (input.startsWith("deadline")) {
                    createDeadline(input);
                } else if (input.startsWith("event")) {
                    createEvent(input);
                } else if (input.startsWith("todo")) {
                    createTodo(input);
                } else {
                    throw new FalcoException(FalcoException.ErrorType.UNKNOWN_COMMAND);
                }
            } catch (FalcoException e) {
                ui.bordify(e.getMessage());
            } catch (IOException e) {
                ui.showSavingError();
            }
            input = ui.askInput();
        }
        ui.sayGoodbye();
    }

}
