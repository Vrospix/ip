package falco.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import falco.exception.FalcoException;

public class DeadlineTest {

    @Test
    public void deadlineConstruct_correctTimeFormat_success() {
        try {
            Deadline d1 = new Deadline("Homework", "30/10/2025 1800");
            assertEquals(2, 2); //Reach this line if construct is correct
        } catch (FalcoException e) {
            assertEquals(2, 3);
        }
    }

    @Test
    public void deadlineConstruct_falseTimeFormat_exceptionThrown() {
        try {
            Deadline d1 = new Deadline("Homework", "30 October 2025 1800");
            assertEquals(2, 3); //Should not reach this line if construct is correct
        } catch (FalcoException e) {
            assertEquals(2, 2);
        }
    }
}
