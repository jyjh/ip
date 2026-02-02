import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;

/**
 * This is the main class for the Silver task management application.
 * 
 */
public class Silver {

    public final String DATA_FILEPATH = "data/silver.txt";

    private List<Task> tasks = new ArrayList<>();
    private SilverUI ui = new SilverUI();
    
    public Silver(String filePath) {
        if (!Filesystem.fileExists(DATA_FILEPATH)) {
            System.out.println("No previous data found. Starting fresh.");
        }    
    }

    public void run() {
        ui.printWelcomeMessage();
        ui.printDivider();
        tasks = Filesystem.loadData(Filesystem.initializeFile("data/silver.txt"));
        ui.printResponseMessage("Loaded " + tasks.size() + " tasks from previous session.");
        ui.printResponseMessage("What can I do for you today?");
        Scanner scanner = new Scanner(System.in);
        while (input(scanner)) {
        }
    }
    public static void main(String[] args) {
        Silver silver = new Silver("data/silver.txt");
        silver.run();
    }

     boolean input(Scanner scanner) {
        String userInput = scanner.nextLine();
        switch (userInput) {
        case "add":
            addTask(scanner);
            break;
        case "delete":
            deleteTask(scanner);
            break;
        case "mark":
            mark(scanner);
            break;
        case "unmark":
            unmark(scanner);
            break;
        case "list":
            list();
            break;
        case "blah":
            blah();
            break;
        case "bye":
            bye();
            return false;
        default:
            unknownCommand();
            break;
        }
        return true;
    }

    void addTask(Scanner scanner) {
        try {
            ui.printResponseMessage("Enter the task description:");
            ui.printResponseMessage("> ");
            String desc = scanner.nextLine();
            if (desc.isEmpty()) {
                throw new IllegalArgumentException("Task description cannot be empty.");
            }
            ui.printResponseMessage("And what type of task is this? (todo/deadline/event)");
            ui.printResponseMessage("> ");
            String taskType = scanner.nextLine();
            switch (taskType) {
            case "todo":
                tasks.add(new Todo(desc));
                break;
            case "deadline":
                ui.printResponseMessage("Enter the due date/time (by):");
                ui.printResponseMessage("> ");
                String by = scanner.nextLine();
                if (by.isEmpty()) {
                    throw new IllegalArgumentException("Deadline 'by' field cannot be empty.");
                }
                LocalDate byDate = LocalDate.parse(by);
                tasks.add(new Deadline(desc, byDate));
                break;
            case "event":
                ui.printResponseMessage("Enter the start date/time (from):");
                ui.printResponseMessage("> ");
                String from = scanner.nextLine();
                if (from.isEmpty()) {
                    throw new IllegalArgumentException("Event 'from' field cannot be empty.");
                }
                ui.printResponseMessage("Enter the end date/time (to):");
                ui.printResponseMessage("> ");
                String to = scanner.nextLine();
                
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

     void deleteTask(Scanner scanner) {
        ui.printResponseMessage("Which task number do you want to delete?");
        ui.printResponseMessage("> ");
        int taskNum = 0;
        try {
            taskNum = Integer.parseInt(scanner.nextLine());
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
        Filesystem.saveData(Filesystem.initializeFile(DATA_FILEPATH), new ArrayList<>(tasks));
        ui.printResponseMessage("Tasks saved to " + DATA_FILEPATH + ".");
        ui.printResponseMessage("Farewell. Until next time.");
    }

     void unknownCommand() {
        ui.printResponseMessage("I'm sorry, I don't understand that command. Please try again.");
    }

     void blah() {
        ui.printResponseMessage("Enter an actual command next time, please.");
    }

     void mark(Scanner scanner) {
        ui.printResponseMessage("Which task number do you want to mark as done?");
        ui.printResponseMessage("> ");
        int taskNum = 0;
        try {
            taskNum = Integer.parseInt(scanner.nextLine());
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

     void unmark(Scanner scanner) {
        ui.printResponseMessage("Which task number do you want to unmark as done?");
        ui.printResponseMessage("> ");
        int taskNum = 0;
        try {
            taskNum = Integer.parseInt(scanner.nextLine());
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

     void printIndented(int level, String message) {
        String[] lines = message.split("\n");
        for (String line : lines) {
            System.out.println("\t".repeat(level) + line);
        }
    }
     void printIndentedSingle(int level, String message) {
        System.out.print("\t".repeat(level) + message);
    }
}
