import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class Falco {
    private ArrayList<Task> list;
    private final String BORDER = "_________________________________________________________" +
            "_______________________________________________";
    private final static String LIST_PATH = "./data/falcolist.txt";
    private Storage storage;

    /**
     * Constructor for Falco class
     */
    public Falco() {
        this.storage = new Storage(LIST_PATH);
        try {
            this.list = storage.load();
        } catch (FalcoException ex) {
            String message = "The file time format is wrong... resetting the list ૮(˶ㅠ︿ㅠ)ა";
            bordify(message);
            resetList();
        } catch (IOException e) {
            String message = "Something went wrong when loading the list file... ૮(˶ㅠ︿ㅠ)ა";
            bordify(message);
            resetList();
        }
    }

    /**
     * Put border before and after message
     *
     * @param message
     */
    public void bordify(String message) {
        String result = BORDER + "\n" + message + "\n" + BORDER;
        System.out.println(result.indent(4));
    }

    /**
     * Reset the list back to empty
     */
    public void resetList() {
        this.list = new ArrayList<>();
        String message = "I've reset the list, Sir! (￣^￣ )ゞ";
        bordify(message);
        saveList();
    }

    /**
     * Save task list to a .txt file inside data folder
     */
    public void saveList() {
        try {
            this.storage.save(this.list);
        } catch (IOException e) {
            String message = "Uh..I can't seem to save the list to the file, Sir (ಠ_ಠ)" +
                            "\nPerhaps you should try delete and create a new falcolist.txt in data folder";
            bordify(message);
        }
    }

    /**
     * Insert a task inside the list
     *
     * @param task
     */
    public void insertList(Task task) {
        list.add(task);
        String taskText = (list.size() == 1) ? " task"  : " tasks";
        String message = "Yessir! I've added this task: " +
                        "\n\t" + task +
                        "\nNow you have " + list.size() + taskText + " in the list, Sir! (￣^￣ )ゞ";
        bordify(message);
        saveList();
    }

    /**
     * Get task-'i' from list
     *
     * @param i
     * @return a task from the list
     */
    public Task getTask(int i) {
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
            throw new FalcoException(FalcoException.ErrorType.EMPTY_LIST);
        } else {
            try {
                Task removedTask = getTask(i);
                list.remove(i);

                String message = "Understandable Sir. I've removed this task: " +
                        "\n\t" + removedTask +
                        "\nNow you have " + list.size() + " tasks in the list, Sir! (￣^￣ )ゞ";
                bordify(message);
            } catch (RuntimeException e) {
                throw new FalcoException(FalcoException.ErrorType.OUTOFBOUNDS);
            }
        }
        saveList();
    }

    /**
     * Mark or Unmark the specific task in list
     *
     * @param action
     * @param i
     * @throws FalcoException
     */
    public void markList(String action, int i) throws FalcoException {
        try {
            if (action.equals("mark")) {
                list.get(i).mark();
                String message = "Yessir! (￣^￣ )ゞ I've marked this task as done: " +
                        "\n\t" + getTask(i);
                bordify(message);
            } else {
                list.get(i).unmark();
                String message = "Affirmative! (￣^￣ )ゞ I've marked this task as not done: " +
                        "\n\t" + getTask(i);
                bordify(message);
            }
        } catch (Exception e) {
            throw new FalcoException(FalcoException.ErrorType.OUTOFBOUNDS);
        }
        saveList();
    }

    /**
     * Print out all the task inside list
     */
    public void printList() throws FalcoException{
        int n = list.size();
        if (list.isEmpty()) {
            throw new FalcoException(FalcoException.ErrorType.EMPTY_LIST);
        } else {
            StringBuilder message = new StringBuilder("Sir, here are the tasks in your list: (￣^￣ )ゞ");
            for (int i = 0; i < n; i++) {
                message.append("\n" + (i + 1) + "." + list.get(i).toString());
            }
            bordify(message.toString());
        }
    }

    /**
     * Give greeting texts
     */
    public void greetings() {
        String message = "Hello Sir! I'm Falco (￣^￣ )ゞ " +
                        "\nIt's an honor to be here" +
                        "\nWhat can I do for you?";
        bordify(message);
    }

    public static void main(String[] args) {
        Falco falco = new Falco();
        falco.greetings();

        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        while(!input.equals("bye")) {
            try {
                if (input.equalsIgnoreCase("list")) {
                    falco.printList();
                } else if (input.equalsIgnoreCase("reset")) {
                    falco.resetList();
                } else if (input.toLowerCase().startsWith("delete")) {
                    String[] parts = input.split(" ");
                    if (parts.length == 1) { throw new FalcoException(FalcoException.ErrorType.UNCLEAR_DELETE); }
                    try {
                        int index = Integer.parseInt(parts[1]) - 1;
                        falco.deleteTask(index);
                    } catch (RuntimeException e) {
                        throw new FalcoException(FalcoException.ErrorType.UNKNOWN_COMMAND);
                    }

                } else if (input.toLowerCase().startsWith("mark") || input.toLowerCase().startsWith("unmark")) {
                    String[] parts = input.split(" ");
                    if (parts.length == 1) { throw new FalcoException(FalcoException.ErrorType.UNCLEAR_MARK); }
                    try {
                        int index = Integer.parseInt(parts[1]) - 1;
                        falco.markList(parts[0], index);
                    } catch (FalcoException e) {
                        throw new FalcoException(FalcoException.ErrorType.OUTOFBOUNDS);
                    } catch (Exception e) {
                        throw new FalcoException(FalcoException.ErrorType.UNKNOWN_COMMAND);
                    }
                } else if (input.toLowerCase().startsWith("deadline")) {
                    String[] parts = input.split(" ", 2);
                    if (parts.length == 1) { throw new FalcoException(FalcoException.ErrorType.EMPTY_TASK); }
                    String remaining = parts[1];

                    String[] details = remaining.split("/by", 2);
                    if (details.length == 1 || details[1].isBlank()) { throw new FalcoException(FalcoException.ErrorType.NOTIME_DEADLINE); }
                    String desc = details[0].trim();
                    String time = details[1].trim();

                    Task task = new Deadline(desc, time);

                    falco.insertList(task);
                } else if (input.toLowerCase().startsWith("event")) {
                    String[] parts = input.split(" ", 2);
                    if (parts.length == 1) { throw new FalcoException(FalcoException.ErrorType.EMPTY_TASK); }
                    String remaining = parts[1];

                    String[] details = remaining.split("/from", 2);
                    if (details.length == 1) { throw new FalcoException(FalcoException.ErrorType.UNCLEAR_EVENT); }
                    String desc = details[0].trim();
                    String time = details[1];

                    String[] spantime = time.split("/to", 2);
                    if (spantime.length == 1 || spantime[1].isBlank()) { throw new FalcoException(FalcoException.ErrorType.UNCLEAR_EVENT); }
                    String from = spantime[0].trim();
                    String to = spantime[1].trim();

                    Task task = new Event(desc, from, to);

                    falco.insertList(task);
                } else if (input.toLowerCase().startsWith("todo")) {
                    String[] parts = input.split(" ", 2);
                    if (parts.length == 1) { throw new FalcoException(FalcoException.ErrorType.EMPTY_TASK); }
                    String desc = parts[1].trim();

                    Task task = new Todo(desc);

                    falco.insertList(task);
                } else {
                    throw new FalcoException(FalcoException.ErrorType.UNKNOWN_COMMAND);
                }
            } catch (FalcoException e) {
                falco.bordify(e.getMessage());
            }

            input = sc.nextLine();
        }
        falco.bordify("Bye Sir! It's an honor to work with you! (￣^￣ )ゞ");
    }
}
