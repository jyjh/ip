package silver;

/**
 * Parses user input commands and maps them to corresponding action codes.
 */
public class Parser {
    int input(String input) {
        switch (input) {
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
}
