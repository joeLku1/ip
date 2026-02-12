package clowns.task;
public class Events extends Task {
    protected static final int OFFSET_FROM = 5;
    protected static final int OFFSET_TO = 3;

    protected String from;
    protected String to;

    public Events(String description) {
        super(description.substring(0, description.indexOf("/from")).trim());
        this.from = description.substring(description.indexOf("/from") + OFFSET_FROM, description.indexOf("/to")).trim();
        this.to = description.substring(description.indexOf("/to") + OFFSET_TO).trim();
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return this.from;
    }

    public String getTo() {
        return this.to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + this.from + " to: " + this.to + ")";
    }
}
