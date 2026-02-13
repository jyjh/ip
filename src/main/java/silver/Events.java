package silver;
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
        assert from != null : "Event start date cannot be null";
        assert to != null : "Event end date cannot be null";
        assert !from.isAfter(to) : "Event start date cannot be after end date";
        this.from = from;
        this.to = to;
    }

    /**
     * Constructor for Events class from a saved state string.
     * @param loadState
     */
    public static Events loadFromState(String loadState) {
        assert loadState != null && !loadState.isEmpty() : "Load state string cannot be null or empty";
        String[] parts = loadState.split("\\|", 5);
        assert parts.length >= 5 : "Load state string must have at least 5 parts for Events";
        assert "E".equals(parts[0]) : "Load state string must start with 'E' for Events";
        
        Events events = new Events(parts[2], LocalDate.parse(parts[3]), LocalDate.parse(parts[4]));
        if (parts[1].equals("1")) {
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
