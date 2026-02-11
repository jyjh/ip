package silver;

import silver.commands.ByeCommand;
import silver.commands.Command;
import silver.input.Input;
import silver.input.TerminalInput;

/**
 * This is the main class for the Silver task management application.
 */
public class Silver {

    public static final String DATA_FILEPATH = "data/silver.txt";

    private SilverUI ui;
    private Parser parser = new Parser();
    private TaskList tasks = new TaskList();
    private Input inputSystem;

    /**
     * Constructor for Silver application.
     * @param filePath The path to the data file
     * @param ui The UI implementation to use (terminal or graphical)
     */
    public Silver(String filePath, SilverUI ui) {
        this.ui = ui;
        if (!Filesystem.fileExists(DATA_FILEPATH)) {
            ui.printResponseMessage("No previous data found. Starting fresh.");
        }
        inputSystem = new TerminalInput();
    }

    /**
     * Initializes the Silver application by loading tasks from file.
     * This should be called before running the application.
     */
    public void initialize() {
        tasks = Filesystem.loadData(Filesystem.initializeFile("data/silver.txt"));
        ui.printResponseMessage("Loaded " + tasks.size() + " tasks from previous session.");
        ui.printResponseMessage("What can I do for you today?");
    }

    /**
     * Processes a command and returns the response.
     * Used by GUI mode where input is provided as a string.
     * @param input The command input string
     * @return The response string to display
     */
    public String processCommand(String input) {
        Command command = parser.parse(input);
        command.execute(tasks, ui);
        saveTasks();
        return ui.getResponseString(input);
    }

    /**
     * Runs the main application loop, handling user input and commands.
     */
    public void run() {
        ui.printWelcomeMessage();
        ui.printDivider();
        initialize();
        while (true) {
            String userInput = inputSystem.getInput();
            Command command = parser.parse(userInput);
            if (command instanceof ByeCommand) {
                command.execute(tasks, ui);
                saveTasks();
                inputSystem.close();
                return;
            }
            command.execute(tasks, ui);
            saveTasks();
        }
    }

    /**
     * Saves tasks to the data file.
     */
    private void saveTasks() {
        Filesystem.saveData(Filesystem.initializeFile(DATA_FILEPATH), tasks);
    }
}
