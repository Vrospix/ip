import java.util.Scanner;
import java.util.ArrayList;

public class Falco {
    public static void main(String[] args) {
        String border = "_________________________________";
        System.out.println(border);
        System.out.println("Hello! I'm Falco");
        System.out.println("What can I do for you?");
        System.out.println(border);

        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        while(!input.equals("bye")) {
            System.out.println(border);
            System.out.println(input + "? Yessir!");
            System.out.println(border);

            input = sc.nextLine();
        }
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(border);
    }
}
