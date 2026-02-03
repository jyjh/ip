package silver;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ToDoTest {
    @Test
    public void testToString() {
        ToDo todo = new ToDo("Read a book");
        assertEquals("[T][ ] Read a book", todo.toString());
        todo.mark();
        assertEquals("[T][X] Read a book", todo.toString());
    }

    @Test
    public void testSaveLoad() {
        ToDo todo = new ToDo("Write unit tests");
        String savedState = todo.saveState();
        assertEquals("T|0|Write unit tests", savedState);

        ToDo loadedTodo = ToDo.loadFromSave(savedState);
        assertEquals("[T][ ] Write unit tests", loadedTodo.toString());

        todo.mark();
        savedState = todo.saveState();
        assertEquals("T|1|Write unit tests", savedState);

        loadedTodo = ToDo.loadFromSave(savedState);
        assertEquals("[T][X] Write unit tests", loadedTodo.toString());
    }
}
