package silver;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TodoTest {
    @Test
    public void testToString() {
        Todo todo = new Todo("Read a book");
        assertEquals("[T][ ] Read a book", todo.toString());
        todo.mark();
        assertEquals("[T][X] Read a book", todo.toString());
    }

    @Test
    public void testNoteFunctionality() {
        Todo todo = new Todo("Write unit tests");
        Note note = new Note("Important task");

        // Test attaching note
        todo.setNoteUid(note.getUid());
        assertEquals(note.getUid(), todo.getNoteUid());
        assertEquals("[T][ ] Write unit tests (1 note)", todo.toString(note));

        // Test detaching note
        todo.removeNoteUid();
        assertEquals(null, todo.getNoteUid());
        assertEquals("[T][ ] Write unit tests", todo.toString(note));
    }
}
