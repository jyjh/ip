package silver.commands;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import silver.SilverUI;
import silver.TaskList;

/**
 * Command to display help information for all available commands.
 * Uses reflection to programmatically discover and list all commands.
 */
public class HelpCommand extends Command {
    // Commands to exclude from help listing
    private static final Class<?>[] EXCLUDED_COMMANDS = {
        HelpCommand.class,
        UnknownCommand.class,
        BlahCommand.class
    };

    public HelpCommand(String[] args) {
        super(args);
    }

    @Override
    public String getKeyword() {
        return "help";
    }

    @Override
    public void validateInput() throws IllegalArgumentException {
        // No arguments required for help command
    }

    @Override
    public void execute(TaskList tasks, SilverUI ui) {
        try {
            validateInput();
        } catch (IllegalArgumentException e) {
            ui.printResponseMessage(e.getMessage());
            return;
        }

        List<CommandUsageInfo> commandUsages = discoverCommands();
        if (commandUsages.isEmpty()) {
            ui.printResponseMessage("No commands found.");
            return;
        }
        StringBuilder helpText = new StringBuilder();
        helpText.append("Here are the available commands:\n");
        helpText.append("=".repeat(40)).append("\n");
        for (CommandUsageInfo info : commandUsages) {
            helpText.append(info.commandName).append(":\n");
            helpText.append("  ").append(info.usage).append("\n\n");
        }
        helpText.append("=".repeat(40)).append("\n");
        helpText.append("Type 'help' again to see this list.");
        ui.printResponseMessage(helpText.toString());
    }

    @Override
    public String getUsage() {
        return getKeyword();
    }

    /**
     * Discovers all Command subclasses in the commands package using reflection.
     * @return List of command usage information
     */
    private List<CommandUsageInfo> discoverCommands() {
        List<CommandUsageInfo> commandUsages = new ArrayList<>();
        // Use CommandRegistry to get the list of all command classes
        for (Class<?> clazz : CommandRegistry.COMMAND_CLASSES) {
            // Skip excluded commands
            if (isExcluded(clazz)) {
                continue;
            }
            try {
                // Instantiate the command with empty args to get usage
                Constructor<?> constructor = clazz.getConstructor(String[].class);
                String[] emptyArgs = new String[0];
                Command command = (Command) constructor.newInstance((Object) emptyArgs);
                String usage = command.getUsage();
                String commandName = command.getKeyword();
                commandUsages.add(new CommandUsageInfo(commandName, usage));
            } catch (Exception e) {
                // Skip commands that can't be instantiated
                continue;
            }
        }
        return commandUsages;
    }

    /**
     * Checks if a command class should be excluded from help.
     * @param clazz The command class to check
     * @return true if the command should be excluded
     */
    private boolean isExcluded(Class<?> clazz) {
        for (Class<?> excluded : EXCLUDED_COMMANDS) {
            if (clazz.equals(excluded)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Inner class to hold command usage information.
     */
    private static class CommandUsageInfo {
        private final String commandName;
        private String usage;

        CommandUsageInfo(String commandName, String usage) {
            this.commandName = commandName;
            this.usage = usage;
        }
    }
}
