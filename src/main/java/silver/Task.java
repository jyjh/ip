package silver;

/**
 * Represents a task with a description and completion status.
 * Can optionally have a single note attached.
 */
public class Task {
    protected String description;
    protected boolean isDone;
    protected String noteUid;

    /**
     * Constructs a Task with the specified description and sets it as not done.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        assert description != null && !description.trim().isEmpty() : "Task description cannot be null or empty";
        this.description = description;
        this.isDone = false;
        this.noteUid = null;
    }

    /**
     * Marks the task as done.
     */
    public void mark() {
        this.isDone = true;
    }

    /**
     * Marks the task as not done.
     */
    public void unmark() {
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public String getDescription() {
        return description;
    }

    /**
     * Attaches a note to this task.
     * Each task can have at most one note.
     *
     * @param noteUid The UID of the note to attach
     */
    public void setNoteUid(String noteUid) {
        assert noteUid != null && !noteUid.trim().isEmpty() : "Note UID cannot be null or empty";
        this.noteUid = noteUid;
    }

    /**
     * Removes the note from this task.
     */
    public void removeNoteUid() {
        this.noteUid = null;
    }

    /**
     * Gets the UID of the note attached to this task.
     *
     * @return The note UID, or null if no note is attached
     */
    public String getNoteUid() {
        return noteUid;
    }

    /**
     * Checks if this task has a note attached.
     *
     * @return true if the task has a note, false otherwise
     */
    public boolean hasNote() {
        return noteUid != null;
    }

    public String getTaskIcon() {
        return "";
    }

    @Override
    public String toString() {
        return toString(null);
    }

    /**
     * Returns a string representation of this task.
     *
     * @param note The note attached to this task (can be null)
     * @return The string representation
     */
    public String toString(Note note) {
        String noteIndicator = hasNote() ? " (1 note)" : "";
        return "[" + this.getTaskIcon() + "]" + "[" + getStatusIcon() + "] " + description + noteIndicator;
    }

    /**
     * Returns a full string representation of this task including the note if present.
     *
     * @param note The note attached to this task (can be null)
     * @return The full string representation
     */
    public String toFullString(Note note) {
        StringBuilder sb = new StringBuilder(toString(note));
        if (hasNote() && note != null) {
            sb.append("\n").append("  1. ").append(note.toString());
        }
        return sb.toString();
    }
}
