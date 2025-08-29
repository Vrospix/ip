package falco.interact;

import falco.exception.FalcoException;

import falco.storage.Storage;
import falco.storage.TaskList;

import falco.task.Deadline;
import falco.task.Event;
import falco.task.Task;
import falco.task.Todo;

import java.io.IOException;

public class Parser {
    private TaskList tasks;
    private Storage storage;
    private Ui ui = new Ui();

    public Parser(TaskList tasks, Storage storage) {
        this.tasks = tasks;
        this.storage = storage;
    }

    public void executeList() throws FalcoException {
        ui.printList(tasks);
    }

    public void executeReset() throws IOException {
        tasks.resetList();
        ui.resetListDone();
        storage.save(tasks);
    }

    public void executeDelete(String input) throws FalcoException, IOException {
        String[] parts = input.split(" ");
        if (parts.length == 1) {
            throw new FalcoException(FalcoException.ErrorType.UNCLEAR_DELETE);
        }
        try {
            int index = Integer.parseInt(parts[1]) - 1;
            Task removedTask = tasks.getTask(index);
            tasks.deleteTask(index);
            ui.deleteTaskDone(tasks, removedTask);
        } catch (Exception e) {
            throw new FalcoException(FalcoException.ErrorType.UNKNOWN_COMMAND);
        }
        storage.save(tasks);
    }

    public void executeMark(String input) throws FalcoException, IOException {
        String[] parts = input.split(" ");
        if (parts.length == 1) {
            throw new FalcoException(FalcoException.ErrorType.UNCLEAR_MARK);
        }
        try {
            int index = Integer.parseInt(parts[1]) - 1;
            tasks.markTask(index);
            ui.markTaskDone(tasks.getTask(index));
        } catch (RuntimeException e) {
            throw new FalcoException(FalcoException.ErrorType.OUTOFBOUNDS);
        }
        storage.save(tasks);
    }

    public void executeUnmark(String input) throws FalcoException, IOException {
        String[] parts = input.split(" ");
        if (parts.length == 1) {
            throw new FalcoException(FalcoException.ErrorType.UNCLEAR_MARK);
        }
        try {
            int index = Integer.parseInt(parts[1]) - 1;
            tasks.unmarkTask(index);
            ui.unmarkTaskDone(tasks.getTask(index));
        } catch (RuntimeException e) {
            throw new FalcoException(FalcoException.ErrorType.OUTOFBOUNDS);
        }
        storage.save(tasks);
    }

    public void createDeadline(String input) throws FalcoException, IOException {
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
        String time = details[1].trim();

        Task task = new Deadline(desc, time);

        insertSave(tasks, task);
    }

    public void createEvent(String input) throws FalcoException, IOException {
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
        String time = details[1];

        String[] spantime = time.split("/to", 2);
        if (spantime.length == 1 || spantime[1].isBlank()) {
            throw new FalcoException(FalcoException.ErrorType.UNCLEAR_EVENT);
        }
        String from = spantime[0].trim();
        String to = spantime[1].trim();

        Task task = new Event(desc, from, to);

        insertSave(tasks, task);
    }

    public void createTodo(String input) throws FalcoException, IOException {
        String[] parts = input.split(" ", 2);
        if (parts.length == 1) {
            throw new FalcoException(FalcoException.ErrorType.EMPTY_TASK);
        }
        String desc = parts[1].trim();

        Task task = new Todo(desc);

        insertSave(tasks, task);
    }

    public void insertSave(TaskList tasks, Task task) throws IOException {
        tasks.insertList(task);
        ui.insertListDone(tasks, task);
        storage.save(tasks);
    }

    public void parse(String text) {
        String input = text;
        while (!input.equalsIgnoreCase("bye")) {
            try {
                if (input.equalsIgnoreCase("list")) {
                    executeList();
                } else if (input.equalsIgnoreCase("reset")) {
                    executeReset();
                } else if (input.toLowerCase().startsWith("delete")) {
                    executeDelete(input);
                } else if (input.toLowerCase().startsWith("mark")) {
                    executeMark(input);
                } else if (input.toLowerCase().startsWith("unmark")) {
                    executeUnmark(input);
                } else if (input.toLowerCase().startsWith("deadline")) {
                    createDeadline(input);
                } else if (input.toLowerCase().startsWith("event")) {
                    createEvent(input);
                } else if (input.toLowerCase().startsWith("todo")) {
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
