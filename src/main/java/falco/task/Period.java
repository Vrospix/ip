package falco.task;

import falco.exception.FalcoException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a task that has a period. A <code>Period</code> has a
 * start time and end time.
 */
public class Period extends Task {
    private LocalDateTime fromTime;
    private LocalDateTime toTime;

    public Period(String task, String fromTime, String toTime) throws FalcoException {
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

    public String getType() {
        return "P";
    }

    @Override
    public String toString() {
        return "[P]" + super.toString() + ", between " + getFromFormatted()
                + " and " + getToFormatted();
    }
}
