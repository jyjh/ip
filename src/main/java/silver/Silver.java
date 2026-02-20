package silver;

import java.util.List;
import java.util.Map;

import silver.commands.ByeCommand;
import silver.commands.Command;
import silver.input.Input;
import silver.input.TerminalInput;



/**
 * This is the main class for the Silver task management application.
 */
public class Silver {

    public static final String TASKS_FILEPATH = "data/tasks.json";
    public static final String NOTES_FILEPATH = "data/notes.json";

    private SilverUI ui;
    private Parser parser = new Parser();
    private TaskList tasks = new TaskList();
    private Input inputSystem;

    /**
     * Constructor for Silver application.
     * @param filePath The path to the data file (legacy, no longer used)
     * @param ui The UI implementation to use (terminal or graphical)
     */
    public Silver(String filePath, SilverUI ui) {
        this.ui = ui;
        if (!Filesystem.fileExists(TASKS_FILEPATH) && !Filesystem.fileExists(NOTES_FILEPATH)) {
            ui.printResponseMessage("No previous data found. Starting fresh.");
        }
        inputSystem = new TerminalInput();
    }

    /**
     * Initializes the Silver application by loading notes and tasks from JSON files.
     * Notes are loaded first, then tasks can reference them by UID.
     * This should be called before running the application.
     */
    public void initialize() {
        // Load notes first
        Filesystem.initializeFile(NOTES_FILEPATH);
        Map<String, Note> loadedNotes = Filesystem.loadNotes(
            Filesystem.initializeFile(NOTES_FILEPATH));
        // Load tasks second
        Filesystem.initializeFile(TASKS_FILEPATH);
        List<Task> loadedTasks = Filesystem.loadTasks(
            Filesystem.initializeFile(TASKS_FILEPATH));
        // Set the data in TaskList
        tasks.setTasks(loadedTasks);
        tasks.setNotes(loadedNotes);
        // Validate task-note references and warn about broken ones
        validateTaskNoteReferences();
        ui.printResponseMessage("Loaded " + tasks.size() + " tasks from previous session.");
        ui.printResponseMessage("What can I do for you today?");
    }

    /**
     * Validates that all tasks reference existing notes.
     * If a task references a non-existent note UID, removes the reference and prints a warning.
     */
    private void validateTaskNoteReferences() {
        Map<String, Note> notes = tasks.getNotes();
        for (Task task : tasks.getAllTasks()) {
            if (task.hasNote()) {
                String noteUid = task.getNoteUid();
                if (!notes.containsKey(noteUid)) {
                    ui.printResponseMessage("Warning: Task '" + task.getDescription()
                        + "' references non-existent note UID " + noteUid
                        + ". Removing note reference.");
                    task.removeNoteUid();
                }
            }
        }
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
        saveData();
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
                saveData();
                inputSystem.close();
                return;
            }
            command.execute(tasks, ui);
            saveData();
        }
    }

    /**
     * Saves both tasks and notes to their respective JSON files.
     */
    private void saveData() {
        Filesystem.saveTasks(Filesystem.initializeFile(TASKS_FILEPATH), tasks.getAllTasks());
        Filesystem.saveNotes(Filesystem.initializeFile(NOTES_FILEPATH), tasks.getNotes());
    }
}
