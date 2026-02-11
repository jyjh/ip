package silver.commands;

/**
 * Registry for all available commands in the Silver application.
 * Provides a single source of truth for command classes that both
 * Parser and HelpCommand can access.
 */
public class CommandRegistry {
    /**
     * Array of all available command classes.
     * This list should be updated whenever a new command is added.
     */
    public static final Class<?>[] COMMAND_CLASSES = {
        AddCommand.class,
        ListCommand.class,
        MarkCommand.class,
        UnmarkCommand.class,
        DeleteCommand.class,
        FindCommand.class,
        ByeCommand.class,
        HelpCommand.class,
        BlahCommand.class,
        UnknownCommand.class
    };

    // Private constructor to prevent instantiation
    private CommandRegistry() {
        throw new AssertionError("CommandRegistry should not be instantiated");
    }
}
