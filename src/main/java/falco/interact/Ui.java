package falco.interact;

import java.util.Scanner;

import falco.storage.TaskList;
import falco.task.Task;

/**
 * Represents the UI in <code>Falco</code>. A <code>UI</code>
 * design to interact directly with the user.
 */
public class Ui {
    private static final String BORDER = "_________________________________________________________"
            + "_______________________________________________";

    /**
     * Creates a UI instance
     */
    public Ui() {}

    /**
     * Puts border before and after message.
     *
     * @param message Message
     */
    public void bordify(String message) {
        String result = BORDER + "\n" + message + "\n" + BORDER;
        System.out.println(result.indent(4));
    }

    /**
     * Scans the input from the user.
     *
     * @return String input
     */
    public String askInput() {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        return input;
    }

    /**
     * Prints the list to user
     *
     * @param message Message
     */
    public void printList(String message) {
        String header = "Sir, here are the tasks in your list: (￣^￣ )ゞ";
        bordify(header + "\n" + message);
    }

    /**
     * Prints the "keyword" list to user
     *
     * @param message Message
     */
    public void findList(String message) {
        String header = "Sir, here are the matching tasks in your list: (￣^￣ )ゞ";
        bordify(header + "\n" + message);
    }

    /**
     * Prints greeting message.
     */
    public void sayGreetings() {
        String message = "Hello Sir! I'm falco.interact.Falco (￣^￣ )ゞ "
                + "\nIt's an honor to be here"
                + "\nWhat can I do for you?";
        bordify(message);
    }

    /**
     * Prints goodbye message.
     */
    public void sayGoodbye() {
        bordify("Bye Sir! It's an honor to work with you! (￣^￣ )ゞ");
    }

    /**
     * Prints a loading error message.
     */
    public void showLoadingError() {
        String message = "Something went wrong when loading the list file... ૮(˶ㅠ︿ㅠ)ა";
        bordify(message);
    }

    /**
     * Prints a saving error message.
     */
    public void showSavingError() {
        String message = "Uh..I can't seem to save the list to the file, Sir (ಠ_ಠ)"
                + "\nPerhaps you should try delete and create a new falcolist.txt in data folder";
        bordify(message);
    }

    /**
     * Prints a message saying task has been mark.
     */
    public void markTaskDone(Task task) {
        String message = "Yessir! (￣^￣ )ゞ I've marked this task as done: "
                + "\n\t" + task;
        bordify(message);
    }

    /**
     * Prints a message saying task has been unmark.
     */
    public void unmarkTaskDone(Task task) {
        String message = "Affirmative! (￣^￣ )ゞ I've marked this task as not done: "
                + "\n\t" + task;
        bordify(message);
    }

    /**
     * Prints a message saying task has been deleted.
     *
     * @param list List of tasks
     * @param removedTask The deleted task
     */
    public void deleteTaskDone(TaskList list, Task removedTask) {
        String message = "Understandable Sir. I've removed this task: "
                + "\n\t" + removedTask
                + "\nNow you have " + list.getSize() + " tasks in the list, Sir! (￣^￣ )ゞ";
        bordify(message);
    }

    /**
     * Prints a message saying the <code>task</code> has been inserted to the <code>tasksList</code>.
     *
     * @param tasksList List of tasks
     * @param task A specific task
     */
    public void insertListDone(TaskList tasksList, Task task) {
        String taskText = (tasksList.getSize() == 1) ? " task" : " tasks";
        String message = "Yessir! I've added this task: "
                + "\n\t" + task
                + "\nNow you have " + tasksList.getSize() + taskText + " in the list, Sir! (￣^￣ )ゞ";
        bordify(message);
    }

    /**
     * Prints a message saying the <code>TaskList</code> has been resetted.
     */
    public void resetListDone() {
        String message = "I've reset the list, Sir! (￣^￣ )ゞ";
        bordify(message);
    }
}
