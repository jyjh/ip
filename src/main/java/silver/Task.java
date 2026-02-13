package silver;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a task with a description and completion status.
 */
public class Task {
    private String description;
    private boolean isDone;
    private List<Note> notes;

    /**
     * Constructs a Task with the specified description and sets it as not done.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        assert description != null && !description.trim().isEmpty() : "Task description cannot be null or empty";
        this.description = description;
        this.isDone = false;
        this.notes = new ArrayList<>();
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
     * Adds a note to this task.
     *
     * @param note The note to add
     */
    public void addNote(Note note) {
        assert note != null : "Note cannot be null";
        notes.add(note);
    }

    /**
     * Removes a note from this task at the specified index.
     *
     * @param index The 1-based index of the note to remove
     * @return The removed note
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    public Note removeNote(int index) {
        assert index >= 1 && index <= notes.size() : "Note index must be valid";
        return notes.remove(index - 1);
    }

    /**
     * Gets all notes attached to this task.
     *
     * @return A copy of the notes list
     */
    public List<Note> getNotes() {
        return new ArrayList<>(notes);
    }

    /**
     * Gets the number of notes attached to this task.
     *
     * @return The number of notes
     */
    public int getNoteCount() {
        return notes.size();
    }

    /**
     * Checks if this task has any notes.
     *
     * @return true if the task has notes, false otherwise
     */
    public boolean hasNotes() {
        return !notes.isEmpty();
    }

    @Override
    public String toString() {
        String noteCountStr = hasNotes() ? " (" + getNoteCount() + " note" + (getNoteCount() > 1 ? "s" : "") + ")" : "";
        return "[" + getStatusIcon() + "] " + description + noteCountStr;
    }

    /**
     * Returns a full string representation of this task including all notes.
     *
     * @return The full string representation
     */
    public String toFullString() {
        StringBuilder sb = new StringBuilder(toString());
        if (hasNotes()) {
            sb.append("\n");
            for (int i = 0; i < notes.size(); i++) {
                sb.append("  ").append(i + 1).append(". ").append(notes.get(i).toString()).append("\n");
            }
        }
        return sb.toString();
    }

    /**
     * Returns the current state of the task as a string, to be loaded later via loadFromSave.
     * @return saveState string
     */
    public String saveState() {
        assert description != null : "Description must not be null when saving state";
        StringBuilder sb = new StringBuilder();
        sb.append((isDone ? "1" : "0")).append("|").append(description);
        for (Note note : notes) {
            sb.append("|").append(note.saveState());
        }
        return sb.toString();
    }
}