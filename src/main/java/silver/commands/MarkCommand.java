package silver.commands;

import silver.SilverUI;
import silver.TaskList;

/**
 * Command to mark a task as done.
 */
public class MarkCommand extends Command {
    public MarkCommand(String[] args) {
        super(args);
    }

    @Override
    public String getKeyword() {
        return "mark";
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

        tasks.get(taskNum - 1).mark();
        ui.printResponseMessage("Marked task " + taskNum + ": "
            + tasks.get(taskNum - 1).getDescription() + " as done.");
    }

    @Override
    public String getUsage() {
        return getKeyword() + " [task-number]";
    }
}
