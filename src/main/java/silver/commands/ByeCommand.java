package silver.commands;

import silver.SilverUI;
import silver.TaskList;

/**
 * Command to exit the application.
 */
public class ByeCommand extends Command {
    public ByeCommand(String[] args) {
        super(args);
    }

    @Override
    public String getKeyword() {
        return "bye";
    }

    @Override
    public void validateInput() throws IllegalArgumentException {
        // No arguments required for bye command
    }

    @Override
    public void execute(TaskList tasks, SilverUI ui) {
        ui.printResponseMessage("Farewell. Until next time.");
    }

    @Override
    public String getUsage() {
        return getKeyword();
    }
}
