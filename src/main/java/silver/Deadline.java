package silver;

import java.time.LocalDate;

/**
 * Represents a task with a deadline.
 * Extends the Task class to include a "by" date/time.
 */
public class Deadline extends Task {

    LocalDate by;

    /**
     * Constructor for Deadline class.
     * @param description
     * @param by
     */
    public Deadline(String description, LocalDate by) {
        super(description);
        assert by != null : "Deadline date cannot be null";
        this.by = by;
    }

    /**
     * Constructor for JSON deserialization.
     */
    private Deadline() {
        super("");
    }

    /**
     * Constructor for creating a Deadline with all fields (used by JSON deserializer).
     */
    public Deadline(String description, boolean isDone, String noteUid, LocalDate by) {
        super(description);
        this.isDone = isDone;
        this.noteUid = noteUid;
        this.by = by;
    }

    @Override
    public String getTaskIcon() {
        return "D";
    }

    @Override
    public String toString() {
        return toString(null);
    }

    @Override
    public String toString(Note note) {
        return super.toString(note) + " (by: " + by + ")";
    }

    @Override
    public String toFullString(Note note) {
        return super.toFullString(note) + " (by: " + by + ")";
    }
}
