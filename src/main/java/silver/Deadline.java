package silver;
import java.time.LocalDate;

/**
 * Represents a task with a deadline.
 * Extends the Task class to include a "by" date/time.
 */
public class Deadline extends Task {

    protected LocalDate by;

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
     * Constructor for Deadline class from a saved state string.
     * @param loadState
     */
    public static Deadline loadFromState(String loadState) {
        assert loadState != null && !loadState.isEmpty() : "Load state string cannot be null or empty";
        String[] parts = loadState.split("\\|", 4);
        assert parts.length >= 4 : "Load state string must have at least 4 parts for Deadline";
        assert "D".equals(parts[0]) : "Load state string must start with 'D' for Deadline";
        
        Deadline deadline = new Deadline(parts[2], LocalDate.parse(parts[3]));
        if (parts[1].equals("1")) {
            deadline.mark();
        }
        return deadline;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }

    @Override
    public String saveState() {
        return "D|" + super.saveState() + "|" + by.toString();
    }
}
