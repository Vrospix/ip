package falco.task;

import falco.exception.FalcoException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Event extends Task {
    private LocalDateTime fromTime;
    private LocalDateTime toTime;

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

    public String getFrom() {
        return this.fromTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm"));
    }

    public String getFromFormatted() {
        return this.fromTime.format(DateTimeFormatter.ofPattern("d MMMM yyyy h:mm a"));
    }

    public String getTo() {
        return this.toTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm"));
    }

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
