public class Silver {

    static final int responseIndent = 1;
    static String[] tasks = new String[100];
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
        while (input()){
        }

    }

    static boolean input() {
        String userInput = System.console().readLine();
        switch (userInput) {
            case "add":
                addTask(userInput);
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

    static void addTask(String task) {
        if (taskCounter >= tasks.length) {
            printIndented(responseIndent, "Task list is full. Cannot add more tasks.");
            return;
        }
        printIndented(responseIndent, "What task do you want to add?");
        printIndentedSingle(responseIndent, "> ");
        tasks[taskCounter] = System.console().readLine();
        printIndented(responseIndent, "Understood. I've added this task:\n> " + tasks[taskCounter]);
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

    static void list() {
        if (taskCounter == 0) {
            printIndented(responseIndent, "Your task list is empty.");
            return;
        }
        printIndented(responseIndent, hString);
        for (int i = 0; i < taskCounter; i++) {
            printIndented(responseIndent, (i + 1) + ". " + tasks[i]);
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
