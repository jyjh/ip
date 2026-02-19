package silver.commands;

import silver.Note;
import silver.SilverUI;
import silver.Task;
import silver.TaskList;

/**
 * Command to attach a note to a task.
 * Each task can have at most one note.
 */
public class AttachCommand extends Command {
    public AttachCommand(String[] args) {
        super(args);
    }

    @Override
    public String getKeyword() {
        return "attach";
    }

    @Override
    public void validateInput() throws IllegalArgumentException {
        if (args.length < 3) {
            throw new IllegalArgumentException("Invalid format. Usage: " + getUsage());
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

        try {
            int taskIndex = Integer.parseInt(args[1]);
            if (taskIndex < 1 || taskIndex > tasks.size()) {
                ui.printResponseMessage("Invalid task index. Please provide a number between 1 and "
                    + tasks.size() + ".");
                return;
            }

            // Join the remaining arguments as the note content
            StringBuilder noteContent = new StringBuilder();
            for (int i = 2; i < args.length; i++) {
                if (i > 2) {
                    noteContent.append(" ");
                }
                noteContent.append(args[i]);
            }

            String noteText = noteContent.toString().trim();
            if (noteText.isEmpty()) {
                ui.printResponseMessage("Note content cannot be empty.");
                return;
            }

            try {
                // Create note and add to task list
                Note note = new Note(noteText);
                String noteUid = tasks.addNote(note);
                // Attach note to task
                Task task = tasks.get(taskIndex - 1);
                // If task already has a note, remove the old one first
                if (task.hasNote()) {
                    String oldNoteUid = task.getNoteUid();
                    tasks.removeNote(oldNoteUid);
                }
                task.setNoteUid(noteUid);
                ui.printResponseMessage("Attached note to task " + taskIndex + ": \"" + noteText + "\"");
            } catch (IllegalArgumentException e) {
                ui.printResponseMessage("Error adding note: " + e.getMessage());
            }

        } catch (NumberFormatException e) {
            ui.printResponseMessage("Invalid task index. Please provide a valid number.");
        }
    }

    @Override
    public String getUsage() {
        return getKeyword() + " <task-index> <note-text>";
    }
}
