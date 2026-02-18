package silver;

import java.util.UUID;

/**
 * Represents a single note attached to a task.
 * A note contains text content and a unique identifier (UID).
 * Each note can only be attached to one task at a time.
 */
public class Note {
    private String uid;
    private String content;

    /**
     * Constructs a Note with the specified content and generates a unique UID.
     * Validates that the content is not empty and is a single line.
     *
     * @param content The content of the note
     * @throws IllegalArgumentException if content is null, empty, or contains newlines
     */
    public Note(String content) {
        this(null, content);
    }

    /**
     * Constructs a Note with the specified UID and content.
     * Used when loading from saved data.
     *
     * @param uid The unique identifier for the note
     * @param content The content of the note
     * @throws IllegalArgumentException if content is null, empty, or contains newlines
     */
    public Note(String uid, String content) {
        assert content != null : "Note content cannot be null";
        if (content.trim().isEmpty()) {
            throw new IllegalArgumentException("Note content cannot be empty.");
        }
        if (content.contains("\n")) {
            throw new IllegalArgumentException("Note content must be a single line.");
        }
        this.uid = uid != null ? uid : UUID.randomUUID().toString();
        this.content = content;
    }

    /**
     * Gets the unique identifier (UID) of this note.
     *
     * @return The note UID
     */
    public String getUid() {
        return uid;
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
}