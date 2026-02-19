package silver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages a list of tasks and their associated notes.
 * Provides methods to add, remove, and retrieve tasks and notes.
 */
public class TaskList {
    private List<Task> tasks = new ArrayList<>();
    private Map<String, Note> notes = new HashMap<>();

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
     * Also removes any note associated with this task.
     * @param index
     * @return
     */
    public Task remove(int index) {
        assert index >= 0 && index < tasks.size() : "Index must be within bounds of task list";
        Task removed = tasks.get(index);

        // Remove associated note if exists
        if (removed.hasNote()) {
            notes.remove(removed.getNoteUid());
        }

        tasks.remove(index);
        return removed;
    }

    public Task get(int index) {
        assert index >= 0 && index < tasks.size() : "Index must be within bounds of task list";
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
     * Sets the notes map for this task list.
     * @param notes
     */
    public void setNotes(Map<String, Note> notes) {
        this.notes = notes;
    }

    /**
     * Gets the notes map for this task list.
     * @return
     */
    public Map<String, Note> getNotes() {
        return notes;
    }

    /**
     * Adds a note to the notes map.
     * @param note
     * @return the UID of the added note
     */
    public String addNote(Note note) {
        notes.put(note.getUid(), note);
        return note.getUid();
    }

    /**
     * Removes a note from the notes map.
     * @param uid the UID of the note to remove
     */
    public void removeNote(String uid) {
        notes.remove(uid);
    }

    /**
     * Gets a note by its UID.
     * @param uid the UID of the note
     * @return the Note, or null if not found
     */
    public Note getNote(String uid) {
        return notes.get(uid);
    }

    /**
     * Returns a TaskList of tasks that contain the specified keyword.
     * @param keyword
     * @return
     */
    public TaskList findTasks(String keyword) {
        assert keyword != null : "Keyword cannot be null";
        TaskList foundTasks = new TaskList();
        for (Task task : tasks) {
            if (task.getDescription().contains(keyword)) {
                foundTasks.add(task);
            }
        }
        return foundTasks;
    }

    /**
     * Returns a string representation of all tasks in the list, with their associated notes if they have any.
     * @return
     */
    public String listTasks() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            Note note = task.hasNote() ? notes.get(task.getNoteUid()) : null;
            sb.append((i + 1)).append(". ").append(task.toString(note)).append("\n");
        }
        return sb.toString();
    }
}
