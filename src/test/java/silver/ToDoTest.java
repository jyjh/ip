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
    public void testSaveLoad() {
        Todo todo = new Todo("Write unit tests");
        String savedState = todo.saveState();
        assertEquals("T|0|Write unit tests", savedState);

        Todo loadedTodo = Todo.loadFromState(savedState);
        assertEquals("[T][ ] Write unit tests", loadedTodo.toString());

        todo.mark();
        savedState = todo.saveState();
        assertEquals("T|1|Write unit tests", savedState);

        loadedTodo = Todo.loadFromState(savedState);
        assertEquals("[T][X] Write unit tests", loadedTodo.toString());
    }
}
