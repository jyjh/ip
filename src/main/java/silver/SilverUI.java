package silver;

/**
 * Abstract class for handling user interface operations in Silver.
 * Concrete implementations can be terminal-based or graphical.
 */
public abstract class SilverUI {

    /**
     * Callback interface for immediate message display.
     */
    public interface MessageCallback {
        void onMessage(String message);
    }

    protected MessageCallback messageCallback;

    /**
     * Sets the message callback for immediate output display.
     * @param callback The callback to invoke when messages are printed
     */
    public void setMessageCallback(MessageCallback callback) {
        this.messageCallback = callback;
    }

    /**
     * Prints the welcome message when the application starts.
     */
    public abstract void printWelcomeMessage();

    /**
     * Prints a response message to the user.
     * @param message The message to display
     */
    public abstract void printResponseMessage(String message);

    /**
     * Prints a divider/separator line.
     */
    public abstract void printDivider();

    /**
     * Gets the response string for graphical UI implementations.
     * This method captures output for display in a GUI.
     * @param input The user input (optional, may not be used)
     * @return The response string to display
     */
    public String getResponseString(String input) {
        return ""; // Default implementation for terminal UI
    }

    /**
     * Notifies the callback immediately when a message is printed.
     * @param message The message to send to callback
     */
    protected void notifyCallback(String message) {
        if (messageCallback != null) {
            messageCallback.onMessage(message);
        }
    }
}
