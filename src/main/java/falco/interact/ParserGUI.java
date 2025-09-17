package falco.interact;

import java.io.IOException;

import falco.exception.FalcoException;
import falco.storage.Storage;
import falco.storage.TaskList;
import falco.task.*;

/**
 * Parses the command input by user.
 */
public class ParserGUI {
    private TaskList tasks;
    private Storage storage;
    private UiForGUI ui = new UiForGUI();

    /**
     * Creates an instance of <code>Parser</code> with corresponding
     * <code>TaskList</code> and <code>Storage</code>.
     * @param tasks List of tasks
     * @param storage Storage of the list of tasks
     */
    public ParserGUI(TaskList tasks, Storage storage) {
        this.tasks = tasks;
        this.storage = storage;
    }

    /**
     * Splits the first word in input with the rest
     * If input only contains one word, throw out a <code>FalcoException</code>.
     *
     * @param input String input from user
     * @param errorType The errorType to throw if exception is caught
     * @return Array of strings
     * @throws FalcoException If input only contains one word
     */
    private String[] splitInput(String input, FalcoException.ErrorType errorType) throws FalcoException {
        String[] parts = input.split(" ", 2);
        if (parts.length == 1) {
            throw new FalcoException(errorType);
        }
        return parts;
    }

    /**
     * Transforms the string integer into an integer data type
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
     * Transforms all the <code>Tasks</code> in the <code>TaskList</code> into String.
     * Then asks <code>Ui</code> to print the String.
     * If <code>TaskList</code> is empty, throw out a <code>FalcoException</code>.
     *
     * @throws FalcoException If tasklist is empty
     */
    private String executeList() throws FalcoException {
        int n = tasks.getSize();
        if (n == 0) {
            tasks.throwEmptyList();
        }
        return ui.printList(tasks.printList());
    }

    /**
     * Finds all the <code>Task</code> in the <code>TaskList</code> that has the "keyword"
     * and print it out.
     * <p>
     * If input is unclear, throws a <code>FalcoException</code>
     *
     * @param input Keyword input
     * @throws FalcoException If input is unclear
     */
    private String executeFind(String input) throws FalcoException {
        String[] parts = splitInput(input, FalcoException.ErrorType.UNCLEAR_FIND);
        String keyword = parts[1];
        String foundList = tasks.findKeyword(keyword);
        return ui.findList(foundList);
    }

    /**
     * Resets all the <code>Tasks</code> in <code>TaskList</code>.
     * If save process fails, throws out an <code>IOException</code>.
     *
     * @throws FalcoException If save process fails
     */
    private String executeReset() throws IOException {
        tasks.resetList();
        storage.save(tasks);
        return ui.resetListDone();
    }

    /**
     * Deletes a <code>Task</code> from the <code>TaskList</code>.
     * If input fails/unclear, throws out a <code>FalcoException</code>.
     * If save process fails, throws out an <code>IOException</code>.
     *
     * @param input String input from user
     * @throws FalcoException if input fails/unclear
     * @throws IOException if save process fails
     */
    private String executeDelete(String input) throws FalcoException, IOException {
        String[] parts = splitInput(input, FalcoException.ErrorType.UNCLEAR_DELETE);
        int index = transformInputToInt(parts[1]);
        Task removedTask = tasks.getTask(index);
        tasks.deleteTask(index);
        storage.save(tasks);
        return ui.deleteTaskDone(tasks, removedTask);
    }

    /**
     * Marks the designated <code>Task</code> inside the <code>TaskList</code>
     * If input fails/unclear, throws out a <code>FalcoException</code>.
     * If save process fails, throws out an <code>IOException</code>.
     *
     * @param input String input from user
     * @throws FalcoException if input fails/unclear
     * @throws IOException if save process fails
     */
    private String executeMark(String input) throws FalcoException, IOException {
        String[] parts = splitInput(input, FalcoException.ErrorType.UNCLEAR_MARK);
        int index = transformInputToInt(parts[1]);
        tasks.markTask(index);
        storage.save(tasks);
        return ui.markTaskDone(tasks.getTask(index));
    }

    /**
     * Unmarks the designated <code>Task</code> inside the <code>TaskList</code>
     * If input fails/unclear, throws out a <code>FalcoException</code>.
     * If save process fails, throws out an <code>IOException</code>.
     *
     * @param input String input from user
     * @throws FalcoException If input fails/unclear
     * @throws IOException If save process fails
     */
    private String executeUnmark(String input) throws FalcoException, IOException {
        String[] parts = splitInput(input, FalcoException.ErrorType.UNCLEAR_MARK);
        int index = transformInputToInt(parts[1]);
        tasks.unmarkTask(index);
        storage.save(tasks);
        return ui.unmarkTaskDone(tasks.getTask(index));
    }

    /**
     * Splits the input for <code>Deadline</code> task creation
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

        if (details[1].isBlank()) {
            throw new FalcoException(FalcoException.ErrorType.EMPTY_TASK);
        }
        assert !details[1].isBlank() : "desc should not be empty";

        return details;
    }

    /**
     * Creates a new <code>Deadline</code> task and store it inside <code>TaskList</code>.
     * If input fails/unclear, throws out a <code>FalcoException</code>.
     * If save process fails, throws out an <code>IOException</code>.
     *
     * @param input String input from user
     * @throws FalcoException If input fails/unclear
     * @throws IOException If save process fails
     */
    private String createDeadline(String input) throws FalcoException, IOException {
        String[] parts = splitInput(input, FalcoException.ErrorType.EMPTY_TASK);

        String remaining = parts[1];

        String[] details = splitDeadlineInput(remaining);

        String desc = details[0].trim();
        
        String time = details[1].trim();

        Task task = new Deadline(desc, time);

        return insertSaveTask(tasks, task);
    }

    /**
     * Splits the input for <code>Event</code> task creation
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

        String desc = details[0].trim();
        if (desc.isBlank()) {
            throw new FalcoException(FalcoException.ErrorType.EMPTY_TASK);
        }
        assert !desc.isBlank() : "desc should not be empty";
        result[0] = desc;

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
     * Creates a new <code>Event</code> task and store it inside <code>TaskList</code>.
     * If input fails/unclear, throws out a <code>FalcoException</code>.
     * If save process fails, throws out an <code>IOException</code>.
     *
     * @param input String input from user
     * @throws FalcoException If input fails/unclear
     * @throws IOException If save process fails
     */
    private String createEvent(String input) throws FalcoException, IOException {
        String[] parts = splitInput(input, FalcoException.ErrorType.EMPTY_TASK);

        String remaining = parts[1];

        String[] details = splitEventInput(remaining);

        String desc = details[0];
        String from = details[1];
        String to = details[2];

        Task task = new Event(desc, from, to);

        return insertSaveTask(tasks, task);
    }

    /**
     * Splits the input for <code>Period</code> task creation
     * If input fails/unclear, throws out a <code>FalcoException</code>.
     *
     * @param input String input from user
     * @return Array of strings
     * @throws FalcoException If input fails/unclear
     */
    private String[] splitPeriodInput(String input) throws FalcoException {
        String[] result = new String[3];
        String[] details = input.split("/between", 2);
        if (details.length == 1 || details[1].isBlank()) {
            throw new FalcoException(FalcoException.ErrorType.UNCLEAR_PERIOD);
        }

        String desc = details[0].trim();
        if (desc.isBlank()) {
            throw new FalcoException(FalcoException.ErrorType.EMPTY_TASK);
        }
        assert !desc.isBlank() : "desc should not be empty";

        result[0] = desc;


        String time = details[1];
        String[] spantime = time.split("/and", 2);
        if (spantime.length == 1 || spantime[1].isBlank()) {
            throw new FalcoException(FalcoException.ErrorType.UNCLEAR_PERIOD);
        }
        String from = spantime[0].trim();
        String to = spantime[1].trim();

        result[1] = from;
        result[2] = to;

        return result;
    }

    /**
     * Creates a new <code>Period</code> task and store it inside <code>TaskList</code>.
     * If input fails/unclear, throws out a <code>FalcoException</code>.
     * If save process fails, throws out an <code>IOException</code>.
     *
     * @param input String input from user
     * @throws FalcoException If input fails/unclear
     * @throws IOException If save process fails
     */
    private String createPeriod(String input) throws FalcoException, IOException {
        String[] parts = splitInput(input, FalcoException.ErrorType.EMPTY_TASK);

        String remaining = parts[1];

        String[] details = splitPeriodInput(remaining);

        String desc = details[0];
        String from = details[1];
        String to = details[2];

        Task task = new Period(desc, from, to);

        return insertSaveTask(tasks, task);
    }

    /**
     * Creates a new <code>Todo</code> task and store it inside <code>TaskList</code>.
     * If input fails/unclear, throws out a <code>FalcoException</code>.
     * If save process fails, throws out an <code>IOException</code>.
     *
     * @param input String input from user
     * @throws FalcoException If input fails/unclear
     * @throws IOException If save process fails
     */
    private String createTodo(String input) throws FalcoException, IOException {
        String[] parts = splitInput(input, FalcoException.ErrorType.EMPTY_TASK);

        String desc = parts[1].trim();

        if (desc.isBlank()) {
            throw new FalcoException(FalcoException.ErrorType.EMPTY_TASK);
        }
        assert !desc.isBlank() : "desc should not be empty";

        Task task = new Todo(desc);

        return insertSaveTask(tasks, task);
    }

    /**
     * Saves the <code>Task</code> inside <code>TaskList</code>.
     * Saves the list to the <code>Storage</code> as well.
     * If save process fails, throws out an <code>IOException</code>.
     *
     * @param tasks List of tasks
     * @param task A specific task
     * @throws IOException if save process fails
     */
    private String insertSaveTask(TaskList tasks, Task task) throws IOException {
        tasks.insertList(task);
        storage.save(tasks);
        return ui.insertListDone(tasks, task);
    }

    /**
     * Breaks down the input from user and execute function based on input
     *
     * @param text input from user
     */
    public String parse(String text) {
        String input = text.toLowerCase().trim();
        try {
            if (input.equalsIgnoreCase("list")) {
                return executeList();
            } else if (input.equalsIgnoreCase("reset")) {
                return executeReset();
            } else if (input.equalsIgnoreCase("help")) {
                return ui.helpUser();
            } else if (input.startsWith("find")) {
                return executeFind(input);
            } else if (input.startsWith("delete") || input.startsWith("remove")) {
                return executeDelete(input);
            } else if (input.startsWith("mark")) {
                return executeMark(input);
            } else if (input.startsWith("unmark")) {
                return executeUnmark(input);
            } else if (input.startsWith("deadline")) {
                return createDeadline(input);
            } else if (input.startsWith("event")) {
                return createEvent(input);
            } else if (input.startsWith("period")) {
                return createPeriod(input);
            } else if (input.startsWith("todo")) {
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
