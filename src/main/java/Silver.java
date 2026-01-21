import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Silver {

    static final int responseIndent = 1;
    static final int maxTasks = 100;        
    static List<Task> tasks = new ArrayList<>();
    static int taskCounter = 0;
    static String hString = "____________________________________________________________";
    public static void main(String[] args) {
        String logo = ""
                + " ____  _ _                \n"
                + "/ ___|(_) |_   _____ _ __ \n"
                + "\\___ \\| | \\ \\ / / _ \\ '__|\n"
                + " ___) | | |\\ V /  __/ |   \n"
                + "|____/|_|_| \\_/ \\___|_|   ";
        System.out.println("Hello. I'm \n" + logo);
        System.out.println(hString);
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
        if (taskCounter >= maxTasks) {
            printIndented(responseIndent, "Task list is full. Cannot add more tasks.");
            return;
        }
        try {
            printIndented(responseIndent, "Enter the task description:");
            printIndentedSingle(responseIndent, "> ");
            String desc = scanner.nextLine();
            if (desc.isEmpty()) {
                throw new IllegalArgumentException("Task description cannot be empty.");
            }
            printIndented(responseIndent, "And what type of task is this? (todo/deadline/event)");
            printIndentedSingle(responseIndent, "> ");
            String taskType = scanner.nextLine();
            switch (taskType) {
                case "todo":
                    tasks.add(new Todo(desc));
                    break;
                case "deadline":
                    printIndented(responseIndent, "Enter the due date/time (by):");
                    printIndentedSingle(responseIndent, "> ");
                    String by = scanner.nextLine();
                    if (by.isEmpty()) {
                        throw new IllegalArgumentException("Deadline 'by' field cannot be empty.");
                    }
                    tasks.add(new Deadline(desc, by));
                    break;
                case "event":
                    printIndented(responseIndent, "Enter the start date/time (from):");
                    printIndentedSingle(responseIndent, "> ");
                    String from = scanner.nextLine();
                    if (from.isEmpty()) {
                        throw new IllegalArgumentException("Event 'from' field cannot be empty.");
                    }
                    printIndented(responseIndent, "Enter the end date/time (to):");
                    printIndentedSingle(responseIndent, "> ");
                    String to = scanner.nextLine();
                    if (to.isEmpty()) {
                        throw new IllegalArgumentException("Event 'to' field cannot be empty.");
                    }
                    tasks.add(new Events(desc, from, to));
                    break;
                default:
                    printIndented(responseIndent, "Unknown task type. Please use 'todo', 'deadline', or 'event'.");
                    return;
            }
        } catch (IllegalArgumentException e) {
            printIndented(responseIndent, e.getMessage());
            return;
        }
        printIndented(responseIndent, "Understood. I've added this task:\n> " + tasks.get(taskCounter).getDescription());
        taskCounter++;
    }

    static void deleteTask(Scanner scanner) {
        printIndented(responseIndent, "Which task number do you want to delete?");
        printIndentedSingle(responseIndent, "> ");
        int taskNum = 0;
        try{
            taskNum = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e){
            printIndented(responseIndent, "Please enter a valid task number.");
            return;
        }
        if (taskNum < 1 || taskNum > taskCounter) {
            printIndented(responseIndent, "No such task found.");
            return;
        }
        Task removedTask = tasks.remove(taskNum - 1);
        taskCounter--;
        printIndented(responseIndent, "Deleted task " + taskNum + ": " + removedTask.getDescription());
    }

    static void bye() {
        printIndented(responseIndent, "Farewell. Until next time.");
    }

    static void unknownCommand() {
        printIndented(responseIndent, "I'm sorry, I don't understand that command. Please try again.");
    }

    static void blah() {
        printIndented(responseIndent, "Enter an actual command next time, please.");
    }

    static void mark(Scanner scanner) {
        printIndented(responseIndent, "Which task number do you want to mark as done?");
        printIndentedSingle(responseIndent, "> ");
        int taskNum = 0;
        try{
            taskNum = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e){
            printIndented(responseIndent, "Please enter a valid task number.");
            return;
        }
        if (taskNum < 1 || taskNum > taskCounter) {
            printIndented(responseIndent, "No such task found.");
            return;
        }
        tasks.get(taskNum - 1).mark();
        printIndented(responseIndent, "Marked task " + taskNum + ": " + tasks.get(taskNum - 1).getDescription() + " as done.");
    }

    static void unmark(Scanner scanner) {
        printIndented(responseIndent, "Which task number do you want to unmark as done?");
        printIndentedSingle(responseIndent, "> ");
        int taskNum = 0;
        try{
            taskNum = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e){
            printIndented(responseIndent, "Please enter a valid task number.");
            return;
        }
        if (taskNum < 1 || taskNum > taskCounter) {
            printIndented(responseIndent, "No such task found.");
            return;
        }
        tasks.get(taskNum - 1).unmark();
        printIndented(responseIndent, "Unmarked task " + taskNum + ": " + tasks.get(taskNum - 1).getDescription() + " as done.");
    }

    static void list() {
        if (taskCounter == 0) {
            printIndented(responseIndent, "Your task list is empty.");
            return;
        }
        printIndented(responseIndent, hString);
        for (int i = 0; i < taskCounter; i++) {
            printIndented(responseIndent, 
                (i + 1) + ". " + tasks.get(i).toString());
        }
        printIndented(responseIndent, hString);
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
