package falco.storage;

import falco.exception.FalcoException;
import falco.task.Deadline;
import falco.task.Event;
import falco.task.Task;
import falco.task.Todo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StorageTest {

    @Test
    public void turnTextToTask_normalTask_success() {
        try {
            Storage storage = new Storage("./data/falcolist.txt");
            String test1 = "D | 0 | Homework | 06/10/2025 1800";
            String test2 = "T | 1 | Buy bread";
            String test3 = "E | 0 | Project discussion | 27/08/2025 1300 - 27/08/2025 1500";

            assertEquals(storage.turnTextToTask(test1).toString(),
                    new Deadline("Homework", "06/10/2025 1800").toString());
            Task task = new Todo("Buy bread");
            task.mark();
            assertEquals(storage.turnTextToTask(test2).toString(), task.toString());

            assertEquals(storage.turnTextToTask(test3).toString(), new Event("Project discussion",
                    "27/08/2025 1300", "27/08/2025 1500").toString());

        } catch (FalcoException e) {
            System.out.println("Test failed");
        }
    }

    @Test
    public void turnTextToTask_wrongTask_exceptionThrown() {
        try {
            Storage storage = new Storage("./data/falcolist.txt");
            String test1 = "W | 0 | Homework | 06/10/2025 1800";
            String test2 = "V | 1 | Buy bread";

            assertEquals(storage.turnTextToTask(test1).toString(),
                    new Deadline("Homework", "06/10/2025 1800").toString());
            Task task = new Todo("Buy bread");
            task.mark();
            assertEquals(storage.turnTextToTask(test2).toString(), task.toString()); //Should stop right here

            assertEquals(2, 3); //Will cause test case fail
        } catch (FalcoException e) {
            assertEquals(2,2);
        }
    }
}
