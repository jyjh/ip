package silver.commands;

import silver.SilverUI;
import silver.Task;
import silver.TaskList;

/**
 * Command to delete a task from the task list.
 */
public class DeleteCommand extends Command {
    public DeleteCommand(String[] args) {
        super(args);
    }

    @Override
    public void validateInput() throws IllegalArgumentException {
        if (args.length < 2) {
            throw new IllegalArgumentException("Invalid format. Usage: delete [task-number]");
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

        Task removedTask = tasks.remove(taskNum - 1);
        ui.printResponseMessage("Deleted task " + taskNum + ": " + removedTask.getDescription());
    }
}
