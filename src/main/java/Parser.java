import java.io.IOException;
import java.util.Scanner;

public class Parser {
    private TaskList tasks;
    private Storage storage;
    private Ui ui;

    public Parser(TaskList tasks, Storage storage, Ui ui) {
        this.tasks = tasks;
        this.storage = storage;
        this.ui = ui;
    }


    public void execute() {
        ui.greetings();
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        while (!input.equals("bye")) {
            try {
                if (input.equalsIgnoreCase("list")) {
                    ui.printList(tasks);
                } else if (input.equalsIgnoreCase("reset")) {
                    tasks.resetList();
                    ui.resetListDone();
                    storage.save(tasks);
                } else if (input.toLowerCase().startsWith("delete")) {
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
                } else if (input.toLowerCase().startsWith("mark") || input.toLowerCase().startsWith("unmark")) {
                    String[] parts = input.split(" ");
                    if (parts.length == 1) {
                        throw new FalcoException(FalcoException.ErrorType.UNCLEAR_MARK);
                    }
                    try {
                        int index = Integer.parseInt(parts[1]) - 1;
                        if (parts[0].trim().equalsIgnoreCase("mark")) {
                            tasks.markTask(index);
                            ui.markTaskDone(tasks.getTask(index));
                        } else {
                            tasks.unmarkTask(index);
                            ui.unmarkTaskDone(tasks.getTask(index));
                        }
                    } catch (RuntimeException e) {
                        throw new FalcoException(FalcoException.ErrorType.OUTOFBOUNDS);
                    }
                    storage.save(tasks);
                } else if (input.toLowerCase().startsWith("deadline")) {
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

                    tasks.insertList(task);
                    ui.insertListDone(tasks, task);
                    storage.save(tasks);
                } else if (input.toLowerCase().startsWith("event")) {
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

                    tasks.insertList(task);
                    ui.insertListDone(tasks, task);
                    storage.save(tasks);
                } else if (input.toLowerCase().startsWith("todo")) {
                    String[] parts = input.split(" ", 2);
                    if (parts.length == 1) {
                        throw new FalcoException(FalcoException.ErrorType.EMPTY_TASK);
                    }
                    String desc = parts[1].trim();

                    Task task = new Todo(desc);

                    tasks.insertList(task);
                    ui.insertListDone(tasks, task);
                    storage.save(tasks);
                } else {
                    throw new FalcoException(FalcoException.ErrorType.UNKNOWN_COMMAND);
                }
            } catch (FalcoException e) {
                ui.bordify(e.getMessage());
            } catch (IOException e) {
                ui.showSavingError();
            }

            input = sc.nextLine();
        }
        ui.goodbye();
    }

}
