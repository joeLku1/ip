public class Events extends Task {
    public Events(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString();
    }
}
