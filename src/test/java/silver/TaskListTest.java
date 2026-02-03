package silver;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskListTest {
    @Test
    public void testAddAndGetTasks() {
        TaskList taskList = new TaskList();
        ToDo todo = new ToDo("Read a book");
        taskList.addTask(todo);
        assertEquals(1, taskList.getSize());
        assertEquals(todo, taskList.getTask(0));
    }

    @Test
    public void testRemoveTask() {
        TaskList taskList = new TaskList();
        ToDo todo1 = new ToDo("Read a book");
        ToDo todo2 = new ToDo("Write unit tests");
        taskList.addTask(todo1);
        taskList.addTask(todo2);
        assertEquals(2, taskList.getSize());

        Task removedTask = taskList.removeTask(0);
        assertEquals(todo1, removedTask);
        assertEquals(1, taskList.getSize());
        assertEquals(todo2, taskList.getTask(0));
    }
}
