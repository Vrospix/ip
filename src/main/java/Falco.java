import java.util.Scanner;
import java.util.ArrayList;

public class Falco {
    private ArrayList<Task> list = new ArrayList<>();
    private final String border = "_________________________________________________________" +
            "_______________________________________________";

    /** Put border before and after text **/
    public void bordify(String message) {
        String result = border + "\n" + message + "\n" + border;
        System.out.println(result.indent(4));
    }

    /** Insert a task inside the list **/
    public void insertList(Task task) {
        list.add(task);

        String message = "Yessir! I've added this task: " +
                        "\n\t" + task +
                        "\nNow you have " + list.size() + " tasks in the list, Sir! (￣^￣ )ゞ";
        bordify(message);
    }

    /** Get the specific task from list **/
    public Task getTask(int i) {
        return list.get(i);
    }

    /** Mark or Unmark the specific task in list **/
    public void markList(String action, int i) {
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
    }

    /** Print out all the task inside list **/
    public void printList() {
        int n = list.size();
        if (n == 0) {
            bordify("Sir, you haven't added any task yet (ಠ_ಠ)");
        } else {
            StringBuilder message = new StringBuilder("Sir, here are the tasks in your list: (￣^￣ )ゞ");
            for (int i = 0; i < n; i++) {
                message.append("\n" + (i + 1) + "." + list.get(i).toString());
            }
            bordify(message.toString());
        }
    }

    /** Greeting command **/
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
                if (input.toLowerCase().startsWith("list")) {
                    falco.printList();

                } else if (input.toLowerCase().startsWith("mark") || input.toLowerCase().startsWith("unmark")) {
                    String[] parts = input.split(" ");
                    int index = Integer.parseInt(parts[1]) - 1;
                    falco.markList(parts[0], index);

                } else if (input.toLowerCase().startsWith("deadline")) {
                    String[] parts = input.split(" ", 2);
                    if (parts.length == 1) { throw new FalcoException(FalcoException.ErrorType.EMPTY_TASK); }
                    String remaining = parts[1];

                    String[] details = remaining.split("/by", 2);
                    if (details.length == 1) { throw new FalcoException(FalcoException.ErrorType.NOTIME_DEADLINE); }
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
                    if (spantime.length == 1) { throw new FalcoException(FalcoException.ErrorType.UNCLEAR_EVENT); }
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
