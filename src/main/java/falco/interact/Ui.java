package falco.interact;

import falco.exception.FalcoException;
import falco.storage.TaskList;
import falco.task.Task;
import java.util.Scanner;

public class Ui {
    private final static String BORDER = "_________________________________________________________" +
            "_______________________________________________";

    public Ui() {}

    /**
     * Put border before and after message
     *
     * @param message
     */
    public void bordify(String message) {
        String result = BORDER + "\n" + message + "\n" + BORDER;
        System.out.println(result.indent(4));
    }

    public String askInput() {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        return input;
    }

    /**
     * Give greeting texts
     */
    public void greetings() {
        String message = "Hello Sir! I'm falco.interact.Falco (￣^￣ )ゞ " +
                "\nIt's an honor to be here" +
                "\nWhat can I do for you?";
        bordify(message);
    }

    public void goodbye() {
        bordify("Bye Sir! It's an honor to work with you! (￣^￣ )ゞ");
    }

    public void showLoadingError() {
        String message = "Something went wrong when loading the list file... ૮(˶ㅠ︿ㅠ)ა";
        bordify(message);
    }

    public void showSavingError() {
        String message = "Uh..I can't seem to save the list to the file, Sir (ಠ_ಠ)" +
                "\nPerhaps you should try delete and create a new falcolist.txt in data folder";
        bordify(message);
    }

    public void markTaskDone(Task task) {
        String message = "Yessir! (￣^￣ )ゞ I've marked this task as done: " +
                "\n\t" + task;
        bordify(message);
    }

    public void unmarkTaskDone(Task task) {
        String message = "Affirmative! (￣^￣ )ゞ I've marked this task as not done: " +
                "\n\t" + task;
        bordify(message);
    }

    public void deleteTaskDone(TaskList list, Task removedTask) {
        String message = "Understandable Sir. I've removed this task: " +
                "\n\t" + removedTask +
                "\nNow you have " + list.getSize() + " tasks in the list, Sir! (￣^￣ )ゞ";
        bordify(message);
    }


    public void insertListDone(TaskList tasksList, Task task) {
        String taskText = (tasksList.getSize() == 1) ? " task"  : " tasks";
        String message = "Yessir! I've added this task: " +
                "\n\t" + task +
                "\nNow you have " + tasksList.getSize() + taskText + " in the list, Sir! (￣^￣ )ゞ";
        bordify(message);
    }

    public void resetListDone() {
        String message = "I've reset the list, Sir! (￣^￣ )ゞ";
        bordify(message);
    }
}
