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
     * Constructor for Todo class from a saved state string.
     * @param loadState
     */
    public static Todo loadFromState(String loadState) {
        assert loadState != null && !loadState.isEmpty() : "Load state string cannot be null or empty";
        String[] parts = loadState.split("\\|");
        assert parts.length >= 3 : "Load state string must have at least 3 parts for Todo";
        assert "T".equals(parts[0]) : "Load state string must start with 'T' for Todo";

        Todo todo = new Todo(parts[2]);
        if (parts[1].equals("1")) {
            todo.mark();
        }

        // Load notes from remaining parts
        for (int i = 3; i < parts.length; i++) {
            if (!parts[i].trim().isEmpty()) {
                todo.addNote(Note.loadFromState(parts[i]));
            }
        }

        return todo;
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String saveState() {
        return "T|" + super.saveState();
    }
}