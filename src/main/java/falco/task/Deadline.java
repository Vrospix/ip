package falco.task;

import falco.exception.FalcoException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {
    private LocalDateTime bytime;

    public Deadline(String task, String bytime) throws FalcoException {
        super(task);
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
            this.bytime = LocalDateTime.parse(bytime.trim(), formatter);
        } catch (DateTimeParseException e) {
            throw new FalcoException(FalcoException.ErrorType.WRONGFORMATTIME);
        }
    }

    public String getBytime() {
        return this.bytime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm"));
    }

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
