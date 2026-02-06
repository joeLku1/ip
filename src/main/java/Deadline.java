public class Deadline extends Task {
    protected String by;
    protected static final int OFFSET = 3;

    public Deadline(String description) {
        super(description.substring(0, description.indexOf("/by")).trim());
        this.by = description.substring(description.indexOf("/by") + OFFSET).trim();
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.by + ")";
    }
}
