package silver.commands;

import silver.SilverUI;
import silver.TaskList;

/**
 * Abstract base class for all commands in the Silver application.
 * Each command is responsible for its own input validation, core logic, and output.
 */
public abstract class Command {
    protected String[] args;

    /**
     * Constructor for Command.
     * @param args Command arguments
     */
    public Command(String[] args) {
        this.args = args;
    }

    /**
     * Validates the input arguments for this command.
     * Subclasses should implement this to check if the provided arguments are valid.
     * @throws IllegalArgumentException if arguments are invalid
     */
    public abstract void validateInput() throws IllegalArgumentException;

    /**
     * Executes the command's core logic.
     * @param tasks The task list to operate on
     * @param ui The UI to use for output
     */
    public abstract void execute(TaskList tasks, SilverUI ui);

    /**
     * Gets the command arguments.
     * @return Array of command arguments
     */
    public String[] getArgs() {
        return args;
    }

    /**
     * Gets the usage string for this command.
     * @return A string describing how to use this command
     */
    public abstract String getUsage();

    /**
     * Gets the keyword for this command.
     * @return The command keyword (e.g., "add", "list", "delete")
     */
    public abstract String getKeyword();
}
