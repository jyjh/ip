package silver.commands;

import silver.Note;
import silver.SilverUI;
import silver.Task;
import silver.TaskList;

/**
 * Command to detach (remove) a note from a task.
 */
public class DetachCommand extends Command {
    public DetachCommand(String[] args) {
        super(args);
    }

    @Override
    public String getKeyword() {
        return "detach";
    }

    @Override
    public void validateInput() throws IllegalArgumentException {
        if (args.length < 3) {
            throw new IllegalArgumentException("Invalid format. Usage: " + getUsage());
        }
    }

    @Override
    public void execute(TaskList tasks, SilverUI ui) {
        try {
            validateInput();
        } catch (IllegalArgumentException e) {
            ui.printResponseMessage(e.getMessage());
            return;
        }

        try {
            int taskIndex = Integer.parseInt(args[1]);
            if (taskIndex < 1 || taskIndex > tasks.size()) {
                ui.printResponseMessage("Invalid task index. Please provide a number between 1 and " + tasks.size() + ".");
                return;
            }

            Task task = tasks.get(taskIndex - 1);
            if (!task.hasNotes()) {
                ui.printResponseMessage("Task " + taskIndex + " has no notes to detach.");
                return;
            }

            int noteIndex = Integer.parseInt(args[2]);
            if (noteIndex < 1 || noteIndex > task.getNoteCount()) {
                ui.printResponseMessage("Invalid note index. Please provide a number between 1 and " + task.getNoteCount() + ".");
                return;
            }

            Note removedNote = task.removeNote(noteIndex);
            ui.printResponseMessage("Detached note from task " + taskIndex + ": \"" + removedNote + "\"");

        } catch (NumberFormatException e) {
            ui.printResponseMessage("Invalid index. Please provide valid numbers for task and note indices.");
        }
    }

    @Override
    public String getUsage() {
        return getKeyword() + " <task-index> <note-index>";
    }
}