import java.util.Scanner;

/**
 * Parses user input commands and maps them to corresponding action codes.
 */
public class Parser {
    int input(Scanner scanner) {
        String userInput = scanner.nextLine();
        switch (userInput) {
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
        default:
            return 8;
        }
    }
}
