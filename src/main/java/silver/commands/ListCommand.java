package silver.commands;

import silver.SilverUI;
import silver.TaskList;

/**
 * Command to list all tasks in the task list.
 */
public class ListCommand extends Command {
    public ListCommand(String[] args) {
        super(args);
    }

    @Override
    public void validateInput() throws IllegalArgumentException {
        // No arguments required for list command
    }

    @Override
    public void execute(TaskList tasks, SilverUI ui) {
        if (tasks.size() == 0) {
            ui.printResponseMessage("Your task list is empty.");
            return;
        }
        ui.printDivider();
        ui.printResponseMessage(tasks.listTasks());
        ui.printDivider();
    }
}
