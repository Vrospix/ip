package falco.task;

import falco.exception.FalcoException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a task that has a deadline.
 */
public class Deadline extends Task {
    private LocalDateTime bytime;

    /**
     * Creates an instance of <code>Deadline</code> with the task description and time deadline.
     * <p>
     * If time format is wrong, throws a <code>FalcoException</code>.
     *
     * @param task Task description
     * @param bytime Deadline time
     * @throws FalcoException If time format is wrong
     * </p>
     */
    public Deadline(String task, String bytime) throws FalcoException {
        super(task);
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
            this.bytime = LocalDateTime.parse(bytime.trim(), formatter);
        } catch (DateTimeParseException e) {
            throw new FalcoException(FalcoException.ErrorType.WRONGFORMATTIME);
        }
    }

    /**
     * Returns the deadline time in default format.
     * <p>
     * e.g. 05/10/2025 1800
     *
     * @return <code>LocalDateTime</code> bytime
     */
    public String getBytime() {
        return this.bytime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm"));
    }

    /**
     * Returns the deadline time in 'd MMMM yyyy h:mm a' format.
     * <p>
     * e.g. 5 October 2025 6:00 pm
     *
     * @return <code>LocalDateTime</code> bytime
     */
    public String getBytimeFormatted() {
        return this.bytime.format(DateTimeFormatter.ofPattern("d MMMM yyyy h:mm a"));
    }

    @Override
    public String getType() {
        return "D";
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + getBytimeFormatted() + ")";
    }
}
