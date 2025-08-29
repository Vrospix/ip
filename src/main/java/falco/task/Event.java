package falco.task;

import falco.exception.FalcoException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a task that is an event. An <code>Event</code> has a
 * start time and end time.
 */
public class Event extends Task {
    private LocalDateTime fromTime;
    private LocalDateTime toTime;

    /**
     * Creates an instance of <code>Event</code> with the task description, fromTime and toTime.
     * <p>
     * If time format is wrong, throws a <code>FalcoException</code>.
     *
     * @param task Task description
     * @param fromTime Start time
     * @param toTime End time
     * @throws FalcoException If time format is wrong
     * </p>
     */
    public Event(String task, String fromTime, String toTime) throws FalcoException {
        super(task);
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
            this.fromTime = LocalDateTime.parse(fromTime, formatter);
            this.toTime = LocalDateTime.parse(toTime, formatter);
        } catch (DateTimeParseException e) {
            throw new FalcoException(FalcoException.ErrorType.WRONGFORMATTIME);
        }
    }

    /**
     * Returns the start time in default format
     * <p>
     * e.g. 05/10/2025 1800
     *
     * @return <code>LocalDateTime</code> fromTime
     */
    public String getFrom() {
        return this.fromTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm"));
    }

    /**
     * Returns the start time in 'd MMMM yyyy h:mm a' format.
     * <p>
     * e.g. 5 October 2025 6:00 pm
     *
     * @return <code>LocalDateTime</code> fromTime
     */
    public String getFromFormatted() {
        return this.fromTime.format(DateTimeFormatter.ofPattern("d MMMM yyyy h:mm a"));
    }

    /**
     * Returns the end time in default format
     * <p>
     * e.g. 05/10/2025 1800
     *
     * @return <code>LocalDateTime</code> toTime
     */
    public String getTo() {
        return this.toTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm"));
    }

    /**
     * Returns the end time in 'd MMMM yyyy h:mm a' format.
     * <p>
     * e.g. 5 October 2025 6:00 pm
     *
     * @return <code>LocalDateTime</code> toTime
     */
    public String getToFormatted() {
        return this.toTime.format(DateTimeFormatter.ofPattern("d MMMM yyyy h:mm a"));
    }

    @Override
    public String getType() {
        return "E";
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + getFromFormatted()
                + ", to: " + getToFormatted() + ")";
    }
}
