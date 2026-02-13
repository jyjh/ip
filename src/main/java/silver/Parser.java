package silver;


import java.lang.reflect.Constructor;

import silver.commands.Command;
import silver.commands.CommandRegistry;
import silver.commands.UnknownCommand;

/**
 * Parses user input commands and creates corresponding Command objects.
 */
public class Parser {

    /**
     * Parses user input and returns the corresponding Command object.
     * @param input The user input string
     * @return The Command object to execute
     */
    Command parse(String input) {
        assert input != null && !input.trim().isEmpty() : "Input cannot be null or empty";
        String[] parts = input.trim().split(" ", 2);
        String command = parts[0].toLowerCase();
        assert command != null && !command.isEmpty() : "Command keyword cannot be empty";

        // Store the full input for argument parsing
        String[] commandArgs = input.trim().split(" ");

        // Iterate through command classes to find a match
        for (Class<?> commandClass : CommandRegistry.COMMAND_CLASSES) {
            try {
                // Instantiate the command with empty args to check keyword
                Constructor<?> constructor = commandClass.getConstructor(String[].class);
                String[] emptyArgs = new String[0];
                Command cmd = (Command) constructor.newInstance((Object) emptyArgs);
                // Check if the command keyword matches
                if (command.equals(cmd.getKeyword())) {
                    // Return a new instance with the actual arguments
                    return (Command) constructor.newInstance((Object) commandArgs);
                }
            } catch (Exception e) {
                // Skip commands that can't be instantiated or matched
                continue;
            }
        }

        // If no command matched, return UnknownCommand
        return new UnknownCommand(commandArgs);
    }
}
