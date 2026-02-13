package silver;

import java.time.LocalDate;

/**
 * Represents a task with a start and end time.
 * Extends the Task class to include "from" and "to" date/times.
 */
public class Events extends Task {

    protected LocalDate from;
    protected LocalDate to;

    /**
     * Constructor for Events class.
     * @param description
     * @param from
     * @param to
     */
    public Events(String description, LocalDate from, LocalDate to) {
        super(description);
        assert from != null : "Event 'from' date cannot be null";
        assert to != null : "Event 'to' date cannot be null";
        this.from = from;
        this.to = to;
    }

    /**
     * Constructor for Events class from a saved state string.
     * @param loadState
     */
    public static Events loadFromState(String loadState) {
        assert loadState != null && !loadState.isEmpty() : "Load state string cannot be null or empty";
        String[] parts = loadState.split("\\|");
        assert parts.length >= 5 : "Load state string must have at least 5 parts for Events";
        assert "E".equals(parts[0]) : "Load state string must start with 'E' for Events";

        Events event = new Events(parts[2], LocalDate.parse(parts[3]), LocalDate.parse(parts[4]));
        if (parts[1].equals("1")) {
            event.mark();
        }

        // Load notes from remaining parts
        for (int i = 5; i < parts.length; i++) {
            if (!parts[i].trim().isEmpty()) {
                event.addNote(Note.loadFromState(parts[i]));
            }
        }

        return event;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }

    @Override
    public String saveState() {
        return "E|" + super.saveState() + "|" + from.toString() + "|" + to.toString();
    }
}