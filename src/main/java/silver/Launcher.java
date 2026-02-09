package silver;

import javafx.application.Application;

/**
 * Launcher class to start the Silver application.
 * Supports both terminal and GUI modes via command-line arguments.
 */
public class Launcher {
    public static void main(String[] args) {
        // Check for terminal mode flag
        if (args.length > 0 && args[0].equals("--terminal")) {
            // Terminal mode
            SilverTerminalUI terminalUI = new SilverTerminalUI();
            Silver silver = new Silver(Silver.DATA_FILEPATH, terminalUI);
            silver.run();
        } else {
            // GUI mode (default)
            Application.launch(Main.class, args);
        }
    }
}
