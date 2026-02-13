package silver.commands;

import silver.SilverUI;
import silver.TaskList;

/**
 * Command to unmark a task as not done.
 */
public class UnmarkCommand extends Command {
    public UnmarkCommand(String[] args) {
        super(args);
    }

    @Override
    public String getKeyword() {
        return "unmark";
    }

    @Override
    public void validateInput() throws IllegalArgumentException {
        if (args.length < 2) {
            throw new IllegalArgumentException("Invalid format. Usage: " + getUsage());
        }
        try {
            int taskNum = Integer.parseInt(args[1]);
            if (taskNum < 1) {
                throw new IllegalArgumentException("Task number must be positive.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Please enter a valid task number.");
        }
    }

    @Override
    public void execute(TaskList tasks, SilverUI ui) {
        assert tasks != null : "TaskList cannot be null";
        assert ui != null : "UI cannot be null";
        try {
            validateInput();
        } catch (IllegalArgumentException e) {
            ui.printResponseMessage(e.getMessage());
            return;
        }

        int taskNum = Integer.parseInt(args[1]);
        if (taskNum > tasks.size()) {
            ui.printResponseMessage("No such task found.");
            return;
        }

        tasks.get(taskNum - 1).unmark();
        ui.printResponseMessage("Unmarked task " + taskNum + ": "
            + tasks.get(taskNum - 1).getDescription() + " as done.");
    }

    @Override
    public String getUsage() {
        return getKeyword() + " [task-number]";
    }
}
