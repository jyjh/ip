import java.util.ArrayList;
import java.util.List;

/**
 * Manages a list of tasks, providing methods to add, remove, and retrieve tasks.
 */
public class TaskList {
    private List<Task> tasks = new ArrayList<>();

    public TaskList() {
    }

    /**
     * Adds a task to the task list.
     * @param task
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Removes and returns the task at the specified index.
     * @param index
     * @return
     */
    public Task remove(int index) {
        Task removed = tasks.get(index);
        tasks.remove(index);
        return removed;
    }

    public Task get(int index) {
        return tasks.get(index);
    }

    public int size() {
        return tasks.size();
    }

    public List<Task> getAllTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
