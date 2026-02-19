package silver.commands;

import silver.SilverUI;
import silver.Task;
import silver.TaskList;

/**
 * Command to detach a note from a task.
 * The note is immediately deleted from storage when detached.
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
        if (args.length != 2) {
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

            if (!task.hasNote()) {
                ui.printResponseMessage("Task " + taskIndex + " does not have a note attached.");
                return;
            }

            // Get the note UID before removing
            String noteUid = task.getNoteUid();

            // Remove note from task
            task.removeNoteUid();

            // Delete note from storage
            tasks.removeNote(noteUid);

            ui.printResponseMessage("Detached and deleted note from task " + taskIndex + ".");

        } catch (NumberFormatException e) {
            ui.printResponseMessage("Invalid task index. Please provide a valid number.");
        }
    }

    @Override
    public String getUsage() {
        return getKeyword() + " <task-index>";
    }
}