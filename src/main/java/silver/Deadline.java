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
        this.by = by;
    }

    /**
     * Constructor for Deadline class from a saved state string.
     * @param loadState
     */
    public static Deadline loadFromState(String loadState) {
        Deadline deadline = new Deadline(loadState.split("\\|", 4)[2],
            LocalDate.parse(loadState.split("\\|", 4)[3]));
        if (loadState.split("\\|", 4)[1].equals("1")) {
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
