package silver;
import java.time.LocalDate;


/**
 * This is the main class for the Silver task management application.
 */
public class Silver {

    public static final String DATA_FILEPATH = "data/silver.txt";

    private SilverTerminalUI ui = new SilverTerminalUI();
    private Parser parser = new Parser();
    private TaskList tasks = new TaskList();
    private Input inputSystem;
    /**
     * Constructor for Silver application.
     * @param filePath
     */
    public Silver(String filePath) {
        if (!Filesystem.fileExists(DATA_FILEPATH)) {
            System.out.println("No previous data found. Starting fresh.");
        }
        inputSystem = new TerminalInput();
    }
    /**
     * Runs the main application loop, handling user input and commands.
     */
    public void run() {
        ui.printWelcomeMessage();
        ui.printDivider();
        tasks = Filesystem.loadData(Filesystem.initializeFile("data/silver.txt"));
        ui.printResponseMessage("Loaded " + tasks.size() + " tasks from previous session.");
        ui.printResponseMessage("What can I do for you today?");
        while (true) {
            int userInput = parser.input(inputSystem.getInput());
            switch (userInput) {
            case 1:
                addTask();
                break;
            case 2:
                deleteTask();
                break;
            case 3:
                mark();
                break;
            case 4:
                unmark();
                break;
            case 5:
                list();
                break;
            case 6:
                blah();
                break;
            case 7:
                bye();
                return;
            case 8:
                unknownCommand();
                break;
            case 9:
                find();
                break;
            default:
                break;
            }
        }
    }
    public static void main(String[] args) {
        Silver silver = new Silver("data/silver.txt");
        silver.run();
    }
    void addTask() {
        try {
            ui.printResponseMessage("Enter the task description:");
            ui.printResponseMessage("> ");
            String desc = inputSystem.getInput();
            if (desc.isEmpty()) {
                throw new IllegalArgumentException("Task description cannot be empty.");
            }
            ui.printResponseMessage("And what type of task is this? (todo/deadline/event)");
            ui.printResponseMessage("> ");
            String taskType = inputSystem.getInput();
            switch (taskType) {
            case "todo":
                tasks.add(new Todo(desc));
                break;
            case "deadline":
                ui.printResponseMessage("Enter the due date/time (by):");
                ui.printResponseMessage("> ");
                String by = inputSystem.getInput();
                if (by.isEmpty()) {
                    throw new IllegalArgumentException("Deadline 'by' field cannot be empty.");
                }
                LocalDate byDate = LocalDate.parse(by);
                tasks.add(new Deadline(desc, byDate));
                break;
            case "event":
                ui.printResponseMessage("Enter the start date/time (from):");
                ui.printResponseMessage("> ");
                String from = inputSystem.getInput();
                if (from.isEmpty()) {
                    throw new IllegalArgumentException("Event 'from' field cannot be empty.");
                }
                ui.printResponseMessage("Enter the end date/time (to):");
                ui.printResponseMessage("> ");
                String to = inputSystem.getInput();
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
    }

    void deleteTask() {
        ui.printResponseMessage("Which task number do you want to delete?");
        ui.printResponseMessage("> ");
        int taskNum = 0;
        try {
            taskNum = Integer.parseInt(inputSystem.getInput());
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
    }

    void bye() {
        Filesystem.saveData(Filesystem.initializeFile(DATA_FILEPATH), tasks);
        ui.printResponseMessage("Tasks saved to " + DATA_FILEPATH + ".");
        ui.printResponseMessage("Farewell. Until next time.");
        inputSystem.close();
    }

    void unknownCommand() {
        ui.printResponseMessage("I'm sorry, I don't understand that command. Please try again.");
    }

    void blah() {
        ui.printResponseMessage("Enter an actual command next time, please.");
    }

    void mark() {
        ui.printResponseMessage("Which task number do you want to mark as done?");
        ui.printResponseMessage("> ");
        int taskNum = 0;
        try {
            taskNum = Integer.parseInt(inputSystem.getInput());
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
    }

    void unmark() {
        ui.printResponseMessage("Which task number do you want to unmark as done?");
        ui.printResponseMessage("> ");
        int taskNum = 0;
        try {
            taskNum = Integer.parseInt(inputSystem.getInput());
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
    }

    void list() {
        if (tasks.size() == 0) {
            ui.printResponseMessage("Your task list is empty.");
            return;
        }
        ui.printDivider();
        for (int i = 0; i < tasks.size(); i++) {
            ui.printResponseMessage((i + 1) + ". " + tasks.get(i).toString());
        }
        ui.printDivider();
    }

    void find() {
        ui.printResponseMessage("Enter the keyword to search for:");
        ui.printResponseMessage("> ");
        String keyword = inputSystem.getInput();
        TaskList foundTasks = tasks.findTasks(keyword);
        if (foundTasks.size() == 0) {
            ui.printResponseMessage("No tasks found matching the keyword: " + keyword);
            return;
        }
        ui.printResponseMessage("Here are the matching tasks in your list:");
        for (int i = 0; i < foundTasks.size(); i++) {
            ui.printResponseMessage((i + 1) + ". " + foundTasks.get(i).toString());
        }
    }
}
