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
     * Constructor for JSON deserialization.
     */
    private Events() {
        super("");
    }

    /**
     * Constructor for creating an Event with all fields (used by JSON deserializer).
     */
    public Events(String description, boolean isDone, String noteUid, LocalDate from, LocalDate to) {
        super(description);
        this.isDone = isDone;
        this.noteUid = noteUid;
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return toString(null);
    }

    @Override
    public String toString(Note note) {
        return "[E]" + super.toString(note) + " (from: " + from + " to: " + to + ")";
    }

    @Override
    public String toFullString(Note note) {
        return "[E]" + super.toFullString(note) + " (from: " + from + " to: " + to + ")";
    }
}