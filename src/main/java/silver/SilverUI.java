package silver;

/**
 * Abstract class for handling user interface operations in Silver.
 * Concrete implementations can be terminal-based or graphical.
 */
public abstract class SilverUI {
    
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
}