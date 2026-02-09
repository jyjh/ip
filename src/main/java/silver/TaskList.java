package silver;
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

    /**
     * Returns a TaskList of tasks that contain the specified keyword.
     * @param keyword
     * @return
     */
    public TaskList findTasks(String keyword) {
        TaskList foundTasks = new TaskList();
        for (Task task : tasks) {
            if (task.getDescription().contains(keyword)) {
                foundTasks.add(task);
            }
        }
        return foundTasks;
    }

    public String listTasks() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            sb.append((i + 1)).append(". ").append(tasks.get(i).toString()).append("\n");
        }
        return sb.toString();
    }
}
