package silver.commands;

import silver.SilverUI;
import silver.TaskList;

/**
 * Command to find tasks containing a keyword.
 */
public class FindCommand extends Command {
    public FindCommand(String[] args) {
        super(args);
    }

    @Override
    public String getKeyword() {
        return "find";
    }

    @Override
    public void validateInput() throws IllegalArgumentException {
        if (args.length < 2) {
            throw new IllegalArgumentException("Invalid format. Usage: " + getUsage());
        }
        if (args[1].isEmpty()) {
            throw new IllegalArgumentException("Keyword cannot be empty.");
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

        String keyword = args[1];
        TaskList foundTasks = tasks.findTasks(keyword);
        assert foundTasks != null : "Found tasks list cannot be null";
        if (foundTasks.size() == 0) {
            ui.printResponseMessage("No tasks found matching the keyword: " + keyword);
            return;
        }
        ui.printResponseMessage("Here are the matching tasks in your list:\n"
            + foundTasks.listTasks());
    }

    @Override
    public String getUsage() {
        return getKeyword() + " [keyword]";
    }
}
