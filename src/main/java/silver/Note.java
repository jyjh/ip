package silver;

/**
 * Represents a single note attached to a task.
 * A note contains text content and can be extended with additional properties in the future.
 */
public class Note {
    private String content;

    /**
     * Constructs a Note with the specified content.
     * Validates that the content is not empty and is a single line.
     *
     * @param content The content of the note
     * @throws IllegalArgumentException if content is null, empty, or contains newlines
     */
    public Note(String content) {
        assert content != null : "Note content cannot be null";
        if (content.trim().isEmpty()) {
            throw new IllegalArgumentException("Note content cannot be empty.");
        }
        if (content.contains("\n")) {
            throw new IllegalArgumentException("Note content must be a single line.");
        }
        this.content = content;
    }

    /**
     * Gets the content of this note.
     *
     * @return The note content
     */
    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return content;
    }

    /**
     * Returns the save state of this note as a string.
     *
     * @return The note content for saving
     */
    public String saveState() {
        return content;
    }

    /**
     * Creates a Note from a saved state string.
     *
     * @param state The saved state string
     * @return A new Note object
     */
    public static Note loadFromState(String state) {
        assert state != null && !state.trim().isEmpty() : "Note state cannot be null or empty";
        return new Note(state);
    }
}