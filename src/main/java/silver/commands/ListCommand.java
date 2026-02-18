package silver.commands;

import silver.Note;
import silver.SilverUI;
import silver.Task;
import silver.TaskList;

/**
 * Command to list all tasks in task list.
 */
public class ListCommand extends Command {
    private boolean showFull;

    public ListCommand(String[] args) {
        super(args);
        this.showFull = false;
    }

    @Override
    public String getKeyword() {
        return "list";
    }

    @Override
    public void validateInput() throws IllegalArgumentException {
        // Check for -full flag
        if (args.length > 1 && args[1].equals("-full")) {
            showFull = true;
        }
        // Any other arguments are invalid
        if (args.length > 2) {
            throw new IllegalArgumentException("Invalid format. Usage: " + getUsage());
        }
    }

    @Override
    public void execute(TaskList tasks, SilverUI ui) {
        if (tasks.size() == 0) {
            ui.printResponseMessage("Your task list is empty.");
            return;
        }

        if (showFull) {
            // Show tasks with full notes
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < tasks.size(); i++) {
                Task task = tasks.get(i);
                Note note = task.hasNote() ? tasks.getNote(task.getNoteUid()) : null;
                sb.append((i + 1)).append(". ").append(task.toFullString(note)).append("\n");
            }
            ui.printDivider();
            ui.printResponseMessage(sb.toString());
            ui.printDivider();
        } else {
            // Show tasks with note count only
            ui.printDivider();
            ui.printResponseMessage(tasks.listTasks());
            ui.printDivider();
        }
    }

    @Override
    public String getUsage() {
        return getKeyword() + " [-full]";
    }
}