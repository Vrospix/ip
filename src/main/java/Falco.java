import java.util.Scanner;
import java.util.ArrayList;

public class Falco {
    private ArrayList<Task> list = new ArrayList<>();
    private final String border = "\t__________________________________________________________________";

    public void insertList(Task task) {
        list.add(task);

        System.out.println(border);
        System.out.println("\tYessir! I've added this task: ");
        System.out.println("\t\t" + task);
        System.out.println("\tNow you have " + list.size() + " tasks in the list, Sir! (￣^￣ )ゞ");
        System.out.println(border);
    }

    public Task getTask(int i) {
        return list.get(i);
    }

    public void markList(String action, int i) {
        if (action.equals("mark")) {
            list.get(i).mark();
        } else {
            list.get(i).unmark();
        }
    }

    public void printList() {
        int n = list.size();
        System.out.println(border);
        System.out.println("\tSir, here are the tasks in your list: (￣^￣ )ゞ");
        for(int i = 0; i < n; i++) {
            System.out.println("\t" + (i + 1) + "." + list.get(i).toString());
        }
        System.out.println(border);
    }

    public void greetings() {
        System.out.println(border);
        System.out.println("\tHello Sir! I'm Falco (￣^￣ )ゞ");
        System.out.println("\tIt's an honor to be here");
        System.out.println("\tWhat can I do for you?");
        System.out.println(border);
    }
    public static void main(String[] args) {
        Falco falco = new Falco();
        falco.greetings();

        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        while(!input.equals("bye")) {
            if (input.equals("list")) {

                System.out.println(falco.border);
                falco.printList();
                System.out.println(falco.border);

            } else if (input.startsWith("mark") || input.startsWith("unmark")) {
                String[] parts = input.split(" ");
                int index = Integer.parseInt(parts[1]) - 1;
                falco.markList(parts[0], index);

                if (parts[0].equals("mark")) {
                    System.out.println(falco.border);
                    System.out.println("\tYessir! (￣^￣ )ゞ I've marked this task as done: ");
                    System.out.println("\t\t" + falco.getTask(index));
                    System.out.println(falco.border);
                } else {
                    System.out.println(falco.border);
                    System.out.println("\tAffirmative! (￣^￣ )ゞ I've marked this task as not done: ");
                    System.out.println("\t\t" + falco.getTask(index));
                    System.out.println(falco.border);
                }

            } else  if (input.startsWith("deadline")) {
                String[] parts = input.split(" ", 2);
                String remaining = parts[1];

                String[] details = remaining.split("/by", 2);
                String desc = details[0].trim();
                String time = details[1].trim();

                Task task = new Deadline(desc, time);

                falco.insertList(task);
            } else if (input.startsWith("event")) {
                String[] parts = input.split(" ", 2);
                String remaining = parts[1];

                String[] details = remaining.split("/from", 2);
                String desc = details[0].trim();
                String time = details[1];

                String[] spantime = time.split("/to", 2);
                String from = spantime[0].trim();
                String to = spantime[1].trim();

                Task task = new Event(desc, from, to);

                falco.insertList(task);
            } else if (input.startsWith("todo")) {
                String[] parts = input.split(" ", 2);
                String desc = parts[1].trim();

                Task task = new Todo(desc);

                falco.insertList(task);
            } else {
                Task task = new Task(input);
                falco.insertList(task);
            }

            input = sc.nextLine();
        }
        System.out.println("\tBye Sir! It's an honor to work with you! (￣^￣ )ゞ");
        System.out.println(falco.border);
    }
}
