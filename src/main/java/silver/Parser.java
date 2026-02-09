package silver;

/**
 * Parses user input commands and maps them to corresponding action codes.
 * Also extracts command arguments for single-line command execution.
 */
public class Parser {
    private String[] commandArgs;

    int input(String input) {
        String[] parts = input.trim().split(" ", 2);
        String command = parts[0].toLowerCase();

        // Store the full input for argument parsing
        commandArgs = input.trim().split(" ");
        
        switch (command) {
        case "add":
            return 1;
        case "delete":
            return 2;
        case "mark":
            return 3;
        case "unmark":
            return 4;
        case "list":
            return 5;
        case "blah":
            return 6;
        case "bye":
            return 7;
        case "find":
            return 9;
        default:
            return 8;
        }
    }
    
    /**
     * Gets the arguments from the last parsed command.
     * @return Array of command arguments
     */
    String[] getCommandArgs() {
        return commandArgs;
    }
}
