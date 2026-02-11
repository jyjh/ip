package silver;


import silver.commands.AddCommand;
import silver.commands.BlahCommand;
import silver.commands.ByeCommand;
import silver.commands.Command;
import silver.commands.DeleteCommand;
import silver.commands.FindCommand;
import silver.commands.ListCommand;
import silver.commands.MarkCommand;
import silver.commands.UnknownCommand;
import silver.commands.UnmarkCommand;

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
        String[] parts = input.trim().split(" ", 2);
        String command = parts[0].toLowerCase();

        // Store the full input for argument parsing
        String[] commandArgs = input.trim().split(" ");

        switch (command) {
        case "add":
            return new AddCommand(commandArgs);
        case "delete":
            return new DeleteCommand(commandArgs);
        case "mark":
            return new MarkCommand(commandArgs);
        case "unmark":
            return new UnmarkCommand(commandArgs);
        case "list":
            return new ListCommand(commandArgs);
        case "blah":
            return new BlahCommand(commandArgs);
        case "bye":
            return new ByeCommand(commandArgs);
        case "find":
            return new FindCommand(commandArgs);
        default:
            return new UnknownCommand(commandArgs);
        }
    }
}
