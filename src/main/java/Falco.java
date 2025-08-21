import java.util.Scanner;
import java.util.ArrayList;

public class Falco {
    ArrayList<Task> list = new ArrayList<>();

    public void insertList(Task task) {
        list.add(task);
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
        for(int i = 0; i < n; i++) {
            System.out.println(i + 1 + "." + list.get(i).toString());
        }
    }

    public static void main(String[] args) {
        String border = "_________________________________";
        System.out.println(border);
        System.out.println("Hello! I'm Falco");
        System.out.println("What can I do for you?");
        System.out.println(border);

        Falco falco = new Falco();

        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        while(!input.equals("bye")) {
            if (input.equals("list")) {

                System.out.println(border);
                falco.printList();
                System.out.println(border);

            } else if (input.startsWith("mark") || input.startsWith("unmark")) {
                String[] parts = input.split(" ");
                int index = Integer.parseInt(parts[1]) - 1;
                falco.markList(parts[0], index);

                if (parts[0].equals("mark")) {
                    System.out.println(border);
                    System.out.println("Yessir! I've marked this task as done: ");
                    System.out.println(falco.getTask(index));
                    System.out.println(border);
                } else {
                    System.out.println(border);
                    System.out.println("Sir! I've marked this task as not done: ");
                    System.out.println(falco.getTask(index));
                    System.out.println(border);
                }

            } else {
                Task task = new Task(input);
                falco.insertList(task);

                System.out.println(border);
                System.out.println("Yessir! added: " + input);
                System.out.println(border);
            }

            input = sc.nextLine();
        }
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(border);
    }
}
