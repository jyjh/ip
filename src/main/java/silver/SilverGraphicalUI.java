package silver;

/**
 * Handles the graphical user interface for Silver.
 * Captures output as strings for display in JavaFX.
 */
public class SilverGraphicalUI extends SilverUI {

    private StringBuilder responseBuffer;

    public SilverGraphicalUI() {
        this.responseBuffer = new StringBuilder();
    }

    @Override
    public void printWelcomeMessage() {
        // Don't print welcome in GUI mode - it's handled by MainWindow
    }

    @Override
    public void printResponseMessage(String message) {
        responseBuffer.append(message).append("\n");
    }

    @Override
    public void printDivider() {
        responseBuffer.append("____________________________________________________________\n");
    }

    @Override
    public String getResponseString(String input) {
        String result = responseBuffer.toString();
        responseBuffer = new StringBuilder();
        return result;
    }
}
