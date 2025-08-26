import java.util.ArrayList;
import java.util.Scanner;

public class Ui {
    private final String BORDER = "_________________________________________________________" +
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

    /**
     * Give greeting texts
     */
    public void greetings() {
        String message = "Hello Sir! I'm Falco (￣^￣ )ゞ " +
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

    public void wrongTimeFormat() {
        String message = "The file time format is wrong... resetting the list ૮(˶ㅠ︿ㅠ)ა";
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

    /**
     * Print out all the task inside list
     */
    public void printList(TaskList list) throws FalcoException {
        int n = list.getSize();
        if (n == 0) {
            throw new FalcoException(FalcoException.ErrorType.EMPTY_LIST);
        } else {
            StringBuilder message = new StringBuilder("Sir, here are the tasks in your list: (￣^￣ )ゞ");
            for (int i = 0; i < n; i++) {
                message.append("\n" + (i + 1) + "." + list.getTask(i).toString());
            }
            bordify(message.toString());
        }
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
