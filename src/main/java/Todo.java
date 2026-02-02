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
        Todo todo = new Todo(loadState.split("\\|", 4)[2]);
        if (loadState.split("\\|", 4)[1].equals("1")) {
            todo.mark();
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
