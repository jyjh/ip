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
        printIndented(responseIndent, "What kind of task do you want to add?");
        printIndentedSingle(responseIndent, "> ");
        String taskType = scanner.nextLine();
        switch (taskType) {
            case "todo":
                printIndented(responseIndent, "Enter the description of the ToDo task:");
                printIndentedSingle(responseIndent, "> ");
                String todoDesc = scanner.nextLine();
                tasks.add(new Todo(todoDesc));
                break;
            case "deadline":
                printIndented(responseIndent, "Enter the description of the Deadline task:");
                printIndentedSingle(responseIndent, "> ");
                String deadlineDesc = scanner.nextLine();
                printIndented(responseIndent, "Enter the due date/time (by):");
                printIndentedSingle(responseIndent, "> ");
                String by = scanner.nextLine();
                tasks.add(new Deadline(deadlineDesc, by));
                break;
            case "event":
                printIndented(responseIndent, "Enter the description of the Event task:");
                printIndentedSingle(responseIndent, "> ");
                String eventDesc = scanner.nextLine();
                printIndented(responseIndent, "Enter the start date/time (from):");
                printIndentedSingle(responseIndent, "> ");
                String from = scanner.nextLine();
                printIndented(responseIndent, "Enter the end date/time (to):");
                printIndentedSingle(responseIndent, "> ");
                String to = scanner.nextLine();
                tasks.add(new Events(eventDesc, from, to));
                break;
            default:
                printIndented(responseIndent, "Unknown task type. Please use 'todo', 'deadline', or 'event'.");
                return;
        }
        printIndented(responseIndent, "Understood. I've added this task:\n> " + tasks.get(taskCounter).getDescription());
        taskCounter++;
    }

    static void bye() {
        printIndented(responseIndent, "Farewell. Until next time.");
    }

    static void unknownCommand() {
        printIndented(responseIndent, "I'm sorry, I don't understand that command. Please try again.");
    }

    static void blah() {
        printIndented(responseIndent, "blah");
    }

    static void mark(Scanner scanner) {
        printIndented(responseIndent, "Which task number do you want to mark as done?");
        printIndentedSingle(responseIndent, "> ");
        int taskNum = Integer.parseInt(scanner.nextLine());
        if (taskNum < 1 || taskNum > taskCounter) {
            printIndented(responseIndent, "Invalid task number.");
            return;
        }
        tasks.get(taskNum - 1).mark();
        printIndented(responseIndent, "Marked task " + taskNum + ": " + tasks.get(taskNum - 1).getDescription() + " as done.");
    }

    static void unmark(Scanner scanner) {
        printIndented(responseIndent, "Which task number do you want to unmark as done?");
        printIndentedSingle(responseIndent, "> ");
        int taskNum = Integer.parseInt(scanner.nextLine());
        if (taskNum < 1 || taskNum > taskCounter) {
            printIndented(responseIndent, "Invalid task number.");
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
