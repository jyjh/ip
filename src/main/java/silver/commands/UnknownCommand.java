package silver.commands;

import silver.SilverUI;
import silver.TaskList;

/**
 * Command to handle unrecognized commands.
 */
public class UnknownCommand extends Command {
    public UnknownCommand(String[] args) {
        super(args);
    }

    @Override
    public void validateInput() throws IllegalArgumentException {
        // No arguments required for unknown command
    }

    @Override
    public void execute(TaskList tasks, SilverUI ui) {
        ui.printResponseMessage("I'm sorry, I don't understand that command. Please try again.");
    }
}
