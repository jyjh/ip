package silver;

/**
 * Represents a task without a specific time.
 * Extends the Task class.
 */
public class Todo extends Task {
    public Todo(String description) {
        super(description);
    }

    /**
     * Constructor for JSON deserialization.
     */
    private Todo() {
        super("");
    }

    /**
     * Constructor for creating a Todo with all fields (used by JSON deserializer).
     */
    public Todo(String description, boolean isDone, String noteUid) {
        super(description);
        this.isDone = isDone;
        this.noteUid = noteUid;
    }

    @Override
    public String getTaskIcon() {
        return "T";
    }

    @Override
    public String toString() {
        return toString(null);
    }

    @Override
    public String toString(Note note) {
        return super.toString(note);
    }

    @Override
    public String toFullString(Note note) {
        return super.toFullString(note);
    }
}
