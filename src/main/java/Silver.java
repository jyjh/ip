import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;

/**
 * This is the main class for the Silver task management application.
 * 
 */
public class Silver {

    public static final int RESPONSE_INDENT = 1;
    public static final int MAX_TASKS = 100;
    public static final String DATA_FILEPATH = "data/silver.txt";

    private static List<Task> tasks = new ArrayList<>();
    private static int taskCounter = 0;
    private static String hString = "____________________________________________________________";
    public static void main(String[] args) {
        String logo = ""
                + " ____  _ _                \n"
                + "/ ___|(_) |_   _____ _ __ \n"
                + "\\___ \\| | \\ \\ / / _ \\ '__|\n"
                + " ___) | | |\\ V /  __/ |   \n"
                + "|____/|_|_| \\_/ \\___|_|   ";
        System.out.println("Hello. I'm \n" + logo);
        System.out.println(hString);

        if (!Filesystem.fileExists(DATA_FILEPATH)) {
            System.out.println("No previous data found. Starting fresh.");
        }
        
        tasks = Filesystem.loadData(Filesystem.initializeFile("data/silver.txt"));
        taskCounter = tasks.size();
        System.out.println("Loaded " + taskCounter + " tasks from previous session.");

        System.out.println("What can I do for you today?");
        Scanner scanner = new Scanner(System.in);
        while (input(scanner)){
        }

    }

    static boolean input(Scanner scanner) {
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

    static void addTask(Scanner scanner) {
        if (taskCounter >= MAX_TASKS) {
            printIndented(RESPONSE_INDENT, "Task list is full. Cannot add more tasks.");
            return;
        }
        try {
            printIndented(RESPONSE_INDENT, "Enter the task description:");
            printIndentedSingle(RESPONSE_INDENT, "> ");
            String desc = scanner.nextLine();
            if (desc.isEmpty()) {
                throw new IllegalArgumentException("Task description cannot be empty.");
            }
            printIndented(RESPONSE_INDENT, "And what type of task is this? (todo/deadline/event)");
            printIndentedSingle(RESPONSE_INDENT, "> ");
            String taskType = scanner.nextLine();
            switch (taskType) {
            case "todo":
                tasks.add(new Todo(desc));
                break;
            case "deadline":
                printIndented(RESPONSE_INDENT, "Enter the due date/time (by):");
                printIndentedSingle(RESPONSE_INDENT, "> ");
                String by = scanner.nextLine();
                if (by.isEmpty()) {
                    throw new IllegalArgumentException("Deadline 'by' field cannot be empty.");
                }
                LocalDate byDate = LocalDate.parse(by);
                tasks.add(new Deadline(desc, byDate));
                break;
            case "event":
                printIndented(RESPONSE_INDENT, "Enter the start date/time (from):");
                printIndentedSingle(RESPONSE_INDENT, "> ");
                String from = scanner.nextLine();
                if (from.isEmpty()) {
                    throw new IllegalArgumentException("Event 'from' field cannot be empty.");
                }
                printIndented(RESPONSE_INDENT, "Enter the end date/time (to):");
                printIndentedSingle(RESPONSE_INDENT, "> ");
                String to = scanner.nextLine();
                
                if (to.isEmpty()) {
                    throw new IllegalArgumentException("Event 'to' field cannot be empty.");
                }
                LocalDate toDate = LocalDate.parse(to);
                LocalDate fromDate = LocalDate.parse(from);
                tasks.add(new Events(desc, fromDate, toDate));
                break;
            default:
                printIndented(RESPONSE_INDENT, "Unknown task type. Please use 'todo', 'deadline', or 'event'.");
                return;
            }
        } catch (IllegalArgumentException e) {
            printIndented(RESPONSE_INDENT, e.getMessage());
            return;
        }
        printIndented(RESPONSE_INDENT, "Understood. I've added this task:\n> "
            + tasks.get(taskCounter).getDescription());
        taskCounter++;
    }

    static void deleteTask(Scanner scanner) {
        printIndented(RESPONSE_INDENT, "Which task number do you want to delete?");
        printIndentedSingle(RESPONSE_INDENT, "> ");
        int taskNum = 0;
        try {
            taskNum = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            printIndented(RESPONSE_INDENT, "Please enter a valid task number.");
            return;
        }
        if (taskNum < 1 || taskNum > taskCounter) {
            printIndented(RESPONSE_INDENT, "No such task found.");
            return;
        }
        Task removedTask = tasks.remove(taskNum - 1);
        taskCounter--;
        printIndented(RESPONSE_INDENT, "Deleted task " + taskNum + ": " + removedTask.getDescription());
    }

    static void bye() {
        Filesystem.saveData(Filesystem.initializeFile(DATA_FILEPATH), new ArrayList<>(tasks));
        printIndented(RESPONSE_INDENT, "Tasks saved to " + DATA_FILEPATH + ".");
        printIndented(RESPONSE_INDENT, "Farewell. Until next time.");
    }

    static void unknownCommand() {
        printIndented(RESPONSE_INDENT, "I'm sorry, I don't understand that command. Please try again.");
    }

    static void blah() {
        printIndented(RESPONSE_INDENT, "Enter an actual command next time, please.");
    }

    static void mark(Scanner scanner) {
        printIndented(RESPONSE_INDENT, "Which task number do you want to mark as done?");
        printIndentedSingle(RESPONSE_INDENT, "> ");
        int taskNum = 0;
        try {
            taskNum = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            printIndented(RESPONSE_INDENT, "Please enter a valid task number.");
            return;
        }
        if (taskNum < 1 || taskNum > taskCounter) {
            printIndented(RESPONSE_INDENT, "No such task found.");
            return;
        }
        tasks.get(taskNum - 1).mark();
        printIndented(RESPONSE_INDENT, "Marked task " + taskNum + ": "
            + tasks.get(taskNum - 1).getDescription() + " as done.");
    }

    static void unmark(Scanner scanner) {
        printIndented(RESPONSE_INDENT, "Which task number do you want to unmark as done?");
        printIndentedSingle(RESPONSE_INDENT, "> ");
        int taskNum = 0;
        try {
            taskNum = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            printIndented(RESPONSE_INDENT, "Please enter a valid task number.");
            return;
        }
        if (taskNum < 1 || taskNum > taskCounter) {
            printIndented(RESPONSE_INDENT, "No such task found.");
            return;
        }
        tasks.get(taskNum - 1).unmark();
        printIndented(RESPONSE_INDENT, "Unmarked task " + taskNum + ": "
            + tasks.get(taskNum - 1).getDescription() + " as done.");
    }

    static void list() {
        if (taskCounter == 0) {
            printIndented(RESPONSE_INDENT, "Your task list is empty.");
            return;
        }
        printIndented(RESPONSE_INDENT, hString);
        for (int i = 0; i < taskCounter; i++) {
            printIndented(RESPONSE_INDENT, (i + 1) + ". " + tasks.get(i).toString());
        }
        printIndented(RESPONSE_INDENT, hString);
    }

    static void printIndented(int level, String message) {
        String[] lines = message.split("\n");
        for (String line : lines) {
            System.out.println("\t".repeat(level) + line);
        }
    }
    static void printIndentedSingle(int level, String message) {
        System.out.print("\t".repeat(level) + message);
    }
}
