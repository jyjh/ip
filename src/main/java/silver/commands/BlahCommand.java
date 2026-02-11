package silver.commands;

import silver.SilverUI;
import silver.TaskList;

/**
 * Command to handle nonsense input.
 */
public class BlahCommand extends Command {
    public BlahCommand(String[] args) {
        super(args);
    }

    @Override
    public String getKeyword() {
        return "blah";
    }

    @Override
    public void validateInput() throws IllegalArgumentException {
        // No arguments required for blah command
    }

    @Override
    public void execute(TaskList tasks, SilverUI ui) {
        ui.printResponseMessage("Enter an actual command next time, please.");
    }

    @Override
    public String getUsage() {
        return getKeyword();
    }
}
