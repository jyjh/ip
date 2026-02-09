package silver;
import java.time.LocalDate;

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
        int commandCode = parser.input(input);
        String[] args = parser.getCommandArgs();
        executeCommand(commandCode, args);
        return ui.getResponseString(input);
    }

    /**
     * Executes a command based on its code.
     * @param commandCode The command code to execute
     * @param args Command arguments
     */
    private void executeCommand(int commandCode, String[] args) {
        switch (commandCode) {
        case 1:
            addTask(args);
            break;
        case 2:
            deleteTask(args);
            break;
        case 3:
            mark(args);
            break;
        case 4:
            unmark(args);
            break;
        case 5:
            list();
            break;
        case 6:
            blah();
            break;
        case 7:
            bye();
            break;
        case 8:
            unknownCommand();
            break;
        case 9:
            find(args);
            break;
        default:
            break;
        }
    }
    /**
     * Runs the main application loop, handling user input and commands.
     */
    public void run() {
        ui.printWelcomeMessage();
        ui.printDivider();
        initialize();
        while (true) {
            int userInput = parser.input(inputSystem.getInput());
            if (userInput == 7) {
                executeCommand(userInput, null);
                return;
            }
            executeCommand(userInput, null);
        }
    }

    void addTask(String[] args) {
        try {
            if (args.length < 3) {
                ui.printResponseMessage("Invalid format. Usage: add todo/deadline/event [description] "
                    + "[additional args]");
                return;
            }

            String taskType = args[1].toLowerCase();
            String desc = args[2];

            if (desc.isEmpty()) {
                throw new IllegalArgumentException("Task description cannot be empty.");
            }

            switch (taskType) {
            case "todo":
                tasks.add(new Todo(desc));
                break;
            case "deadline":
                if (args.length < 4) {
                    ui.printResponseMessage("Invalid format. Usage: add deadline [description] [due-date]");
                    return;
                }
                String by = args[3];
                if (by.isEmpty()) {
                    throw new IllegalArgumentException("Deadline 'by' field cannot be empty.");
                }
                LocalDate byDate = LocalDate.parse(by);
                tasks.add(new Deadline(desc, byDate));
                break;
            case "event":
                if (args.length < 5) {
                    ui.printResponseMessage("Invalid format. Usage: add event [description] [from-date] [to-date]");
                    return;
                }
                String from = args[3];
                String to = args[4];
                if (from.isEmpty()) {
                    throw new IllegalArgumentException("Event 'from' field cannot be empty.");
                }
                if (to.isEmpty()) {
                    throw new IllegalArgumentException("Event 'to' field cannot be empty.");
                }
                LocalDate toDate = LocalDate.parse(to);
                LocalDate fromDate = LocalDate.parse(from);
                tasks.add(new Events(desc, fromDate, toDate));
                break;
            default:
                ui.printResponseMessage("Unknown task type. Please use 'todo', 'deadline', or 'event'.");
                return;
            }
        } catch (IllegalArgumentException e) {
            ui.printResponseMessage(e.getMessage());
            return;
        }
        ui.printResponseMessage("Understood. I've added this task:\n> "
            + tasks.get(tasks.size() - 1).getDescription());
        saveTasks();
    }

    void deleteTask(String[] args) {
        if (args.length < 2) {
            ui.printResponseMessage("Invalid format. Usage: delete [task-number]");
            return;
        }
        int taskNum = 0;
        try {
            taskNum = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            ui.printResponseMessage("Please enter a valid task number.");
            return;
        }
        if (taskNum < 1 || taskNum > tasks.size()) {
            ui.printResponseMessage("No such task found.");
            return;
        }
        Task removedTask = tasks.remove(taskNum - 1);
        ui.printResponseMessage("Deleted task " + taskNum + ": " + removedTask.getDescription());
        saveTasks();
    }

    void bye() {
        saveTasks();
        ui.printResponseMessage("Farewell. Until next time.");
        inputSystem.close();
    }

    /**
     * Saves tasks to the data file.
     */
    private void saveTasks() {
        Filesystem.saveData(Filesystem.initializeFile(DATA_FILEPATH), tasks);
    }

    void unknownCommand() {
        ui.printResponseMessage("I'm sorry, I don't understand that command. Please try again.");
    }

    void blah() {
        ui.printResponseMessage("Enter an actual command next time, please.");
    }

    void mark(String[] args) {
        if (args.length < 2) {
            ui.printResponseMessage("Invalid format. Usage: mark [task-number]");
            return;
        }
        int taskNum = 0;
        try {
            taskNum = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            ui.printResponseMessage("Please enter a valid task number.");
            return;
        }
        if (taskNum < 1 || taskNum > tasks.size()) {
            ui.printResponseMessage("No such task found.");
            return;
        }
        tasks.get(taskNum - 1).mark();
        ui.printResponseMessage("Marked task " + taskNum + ": "
            + tasks.get(taskNum - 1).getDescription() + " as done.");
        saveTasks();
    }

    void unmark(String[] args) {
        if (args.length < 2) {
            ui.printResponseMessage("Invalid format. Usage: unmark [task-number]");
            return;
        }
        int taskNum = 0;
        try {
            taskNum = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            ui.printResponseMessage("Please enter a valid task number.");
            return;
        }
        if (taskNum < 1 || taskNum > tasks.size()) {
            ui.printResponseMessage("No such task found.");
            return;
        }
        tasks.get(taskNum - 1).unmark();
        ui.printResponseMessage("Unmarked task " + taskNum + ": "
            + tasks.get(taskNum - 1).getDescription() + " as done.");
        saveTasks();
    }

    void list() {
        if (tasks.size() == 0) {
            ui.printResponseMessage("Your task list is empty.");
            return;
        }
        ui.printDivider();
        ui.printResponseMessage(tasks.listTasks());
        ui.printDivider();
    }

    void find(String[] args) {
        if (args.length < 2) {
            ui.printResponseMessage("Invalid format. Usage: find [keyword]");
            return;
        }
        String keyword = args[1];
        TaskList foundTasks = tasks.findTasks(keyword);
        if (foundTasks.size() == 0) {
            ui.printResponseMessage("No tasks found matching the keyword: " + keyword);
            return;
        }
        ui.printResponseMessage("Here are the matching tasks in your list:\n"
            + foundTasks.listTasks());
    }
}
