package silver;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TaskListTest {
    @Test
    public void testAddAndGetTasks() {
        TaskList taskList = new TaskList();
        Todo todo = new Todo("Read a book");
        taskList.add(todo);
        assertEquals(1, taskList.size());
        assertEquals(todo, taskList.get(0));
    }

    @Test
    public void testRemoveTask() {
        TaskList taskList = new TaskList();
        Todo todo1 = new Todo("Read a book");
        Todo todo2 = new Todo("Write unit tests");
        taskList.add(todo1);
        taskList.add(todo2);
        assertEquals(2, taskList.size());

        Task removedTask = taskList.remove(0);
        assertEquals(todo1, removedTask);
        assertEquals(1, taskList.size());
        assertEquals(todo2, taskList.get(0));
    }
}
