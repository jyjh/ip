package silver.commands;

import silver.SilverUI;
import silver.Task;
import silver.TaskList;

/**
 * Command to view a specific task and all its attached notes.
 */
public class ViewCommand extends Command {
    public ViewCommand(String[] args) {
        super(args);
    }

    @Override
    public String getKeyword() {
        return "view";
    }

    @Override
    public void validateInput() throws IllegalArgumentException {
        if (args.length < 2) {
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
            ui.printDivider();
            ui.printResponseMessage("Task " + taskIndex + ":");
            ui.printResponseMessage(task.toFullString());
            if (!task.hasNotes()) {
                ui.printResponseMessage("  No notes attached.");
            }
            ui.printDivider();

        } catch (NumberFormatException e) {
            ui.printResponseMessage("Invalid task index. Please provide a valid number.");
        }
    }

    @Override
    public String getUsage() {
        return getKeyword() + " <task-index>";
    }
}