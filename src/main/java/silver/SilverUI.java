package silver;
/**
 * Handles the user interface for Silver.
 */
public class SilverUI {

    public static final int RESPONSE_INDENT = 1;
    private String logo = ""
            + " ____  _ _                \n"
            + "/ ___|(_) |_   _____ _ __ \n"
            + "\\___ \\| | \\ \\ / / _ \\ '__|\n"
            + " ___) | | |\\ V /  __/ |   \n"
            + "|____/|_|_| \\_/ \\___|_|   ";
    private String hString = "____________________________________________________________";

    /**
     * Prints a message with the preset indentation level.
     * @param message
    */
    public void printResponseMessage(String message) {
        if (message.lines().count() > 1) {
            printIndented(RESPONSE_INDENT, message);
        } else {
            printIndentedSingle(RESPONSE_INDENT, message);
        }
    }

    public void printWelcomeMessage() {
        printIndented(0, "Hello! I'm \n" + logo + "\n");
    }

    public void printDivider() {
        printIndented(0, hString);
    }

    private void printIndented(int level, String message) {
        String[] lines = message.split("\n");
        for (String line : lines) {
            System.out.println("\t".repeat(level) + line);
        }
    }
    private void printIndentedSingle(int level, String message) {
        System.out.println("\t".repeat(level) + message);
    }
}
