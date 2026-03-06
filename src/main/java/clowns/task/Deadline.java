package clowns.task;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.ResolverStyle;

public class Deadline extends Task {
    protected LocalDateTime by;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendPattern("dd MMM uuuu HHmm")
            .toFormatter()
            .withResolverStyle(ResolverStyle.STRICT);

    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    public void setBy(LocalDateTime by) {
        this.by = by;
    }

    public LocalDateTime getBy() {
        return this.by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.by.format(DATE_TIME_FORMATTER) + ")";
    }
}
