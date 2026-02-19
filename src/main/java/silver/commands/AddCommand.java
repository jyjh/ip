package silver.commands;

import java.time.LocalDate;

import silver.Deadline;
import silver.Events;
import silver.SilverUI;
import silver.TaskList;
import silver.Todo;

/**
 * Command to add a new task (todo, deadline, or event) to the task list.
 */
public class AddCommand extends Command {
    public AddCommand(String[] args) {
        super(args);
    }

    @Override
    public String getKeyword() {
        return "add";
    }

    /**
     * Joins array elements from startIndex to endIndex with spaces.
     */
    private String joinArguments(int startIndex, int endIndex) {
        StringBuilder sb = new StringBuilder();
        for (int i = startIndex; i < endIndex; i++) {
            if (i > startIndex) {
                sb.append(" ");
            }
            sb.append(args[i]);
        }
        return sb.toString();
    }

    @Override
    public void validateInput() throws IllegalArgumentException {
        if (args.length < 3) {
            throw new IllegalArgumentException("Invalid format. Usage: " + getUsage());
        }
        String taskType = args[1].toLowerCase();

        switch (taskType) {
        case "todo":
            if (args.length < 3) {
                throw new IllegalArgumentException("Invalid format. Usage: "
                    + getKeyword() + " todo [description]");
            }
            break;
        case "deadline":
            if (args.length < 4) {
                throw new IllegalArgumentException("Invalid format. Usage: "
                    + getKeyword() + " deadline [description] [due-date]");
            }
            String by = args[args.length - 1];
            if (by.isEmpty()) {
                throw new IllegalArgumentException("Deadline 'by' field cannot be empty.");
            }
            break;
        case "event":
            if (args.length < 5) {
                throw new IllegalArgumentException("Invalid format. Usage: " + getKeyword() + " event [description] "
                    + "[from-date] [to-date]");
            }
            String to = args[args.length - 1];
            String from = args[args.length - 2];
            if (from.isEmpty()) {
                throw new IllegalArgumentException("Event 'from' field cannot be empty.");
            }
            if (to.isEmpty()) {
                throw new IllegalArgumentException("Event 'to' field cannot be empty.");
            }
            break;
        default:
            throw new IllegalArgumentException("Unknown task type. Please use 'todo', 'deadline', or 'event'.");
        }
    }

    @Override
    public void execute(TaskList tasks, SilverUI ui) {
        try {
            validateInput();
        } catch (IllegalArgumentException e) {
            ui.printResponseMessage(e.getMessage());
            return;
        }

        String taskType = args[1].toLowerCase();
        String desc;

        try {
            switch (taskType) {
            case "todo":
                desc = joinArguments(2, args.length);
                if (desc.isEmpty()) {
                    ui.printResponseMessage("Task description cannot be empty.");
                    return;
                }
                tasks.add(new Todo(desc));
                break;
            case "deadline":
                desc = joinArguments(2, args.length - 1);
                if (desc.isEmpty()) {
                    ui.printResponseMessage("Task description cannot be empty.");
                    return;
                }
                String by = args[args.length - 1];
                LocalDate byDate = LocalDate.parse(by);
                tasks.add(new Deadline(desc, byDate));
                break;
            case "event":
                desc = joinArguments(2, args.length - 2);
                if (desc.isEmpty()) {
                    ui.printResponseMessage("Task description cannot be empty.");
                    return;
                }
                String from = args[args.length - 2];
                String to = args[args.length - 1];
                LocalDate toDate = LocalDate.parse(to);
                LocalDate fromDate = LocalDate.parse(from);
                tasks.add(new Events(desc, fromDate, toDate));
                break;
            default:
                // This should never happen due to validation
                break;
            }
        } catch (Exception e) {
            ui.printResponseMessage("Error parsing date: " + e.getMessage());
            return;
        }

        ui.printResponseMessage("Understood. I've added this task:\n> "
            + tasks.get(tasks.size() - 1).getDescription());
    }

    @Override
    public String getUsage() {
        return getKeyword() + " todo/deadline/event [description] [additional args]\n"
            + "  - todo: " + getKeyword() + " todo [description]\n"
            + "  - deadline: " + getKeyword() + " deadline [description] [due-date]\n"
            + "  - event: " + getKeyword() + " event [description] [from-date] [to-date]";
    }
}