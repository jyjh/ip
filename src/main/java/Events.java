import java.time.LocalDate;

/**
 * Represents a task with a start and end time.
 * Extends the Task class to include a "from" and "to" date/time.
 */
public class Events extends Task {

    private LocalDate from;
    private LocalDate to;

    /**
     * Constructor for Events class.
     * @param description
     * @param from
     * @param to
     */
    public Events(String description, LocalDate from, LocalDate to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Constructor for Events class from a saved state string.
     * @param loadState
     */
    public static Events loadFromState(String loadState) {
        Events events = new Events(loadState.split("\\|", 5)[2],
            LocalDate.parse(loadState.split("\\|", 5)[3]),
            LocalDate.parse(loadState.split("\\|", 5)[4]));
        if (loadState.split("\\|", 5)[1].equals("1")) {
            events.mark();
        }
        return events;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }

    @Override
    public String saveState() {
        return "E|" + super.saveState() + "|" + from + "|" + to;
    }
}