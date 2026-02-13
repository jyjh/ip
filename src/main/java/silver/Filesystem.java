package silver;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;




/**
 * Provides utility methods for file operations such as checking existence,
 * initializing files, saving and loading data.
 */
public class Filesystem {

    /**
     * Checks if a file exists at the specified file path.
     *
     * @param filePath the path to the file to check
     * @return true if the file exists, false otherwise
     */
    public static boolean fileExists(String filePath) {
        assert filePath != null && !filePath.trim().isEmpty() : "File path cannot be null or empty";
        File file = new File(filePath);
        return file.exists();
    }

    /**
     * Loads a file at the specified file path.
     * Creates a new file if it does not already exist.
     *
     * @param filePath the path to the file to initialize
     * @return the initialized File object
     */
    public static File initializeFile(String filePath) {
        assert filePath != null && !filePath.trim().isEmpty() : "File path cannot be null or empty";
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                Files.createDirectories(file.getParentFile().toPath());
                file.createNewFile();
            }
        } catch (Exception e) {
            System.out.println("Error initializing filesystem: " + e.getMessage());
        }
        File result = new File(filePath);
        assert result != null : "File initialization should not return null";
        return result;
    }

    /**
     * Saves the given TaskList data to the specified file.
     * @param file
     * @param data
     */
    public static void saveData(File file, TaskList data) {
        assert file != null : "File cannot be null when saving data";
        assert data != null : "TaskList data cannot be null when saving";
        try (BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(file))) {
            for (Task task : data.getAllTasks()) {
                assert task != null : "Task in TaskList cannot be null when saving";
                writer.write(task.saveState());
                writer.newLine();
            }
        } catch (Exception e) {
            System.out.println("Error saving data to filesystem: " + e.getMessage());
        }

    }

    /**
     * Loads TaskList data from the specified file.
     * @param file
     */
    public static TaskList loadData(File file) {
        assert file != null : "File cannot be null when loading data";
        // Implementation for loading data from a file
        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(file))) {
            ArrayList<Task> tasks = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                assert !line.trim().isEmpty() : "Line in file cannot be empty";
                String[] parts = line.split("\\|", 2);
                assert parts.length >= 1 : "Line must contain at least task type";
                String taskType = parts[0];
                switch (taskType) {
                case "T":
                    tasks.add(Todo.loadFromState(line));
                    break;
                case "D":
                    tasks.add(Deadline.loadFromState(line));
                    break;
                case "E":
                    tasks.add(Events.loadFromState(line));
                    break;
                default:
                    System.out.println("Unknown task type in saved data: " + taskType);
                    break;
                }
            }
            TaskList taskList = new TaskList();
            taskList.setTasks(tasks);
            return taskList;
        } catch (Exception e) {
            System.out.println("Error loading data from filesystem: " + e.getMessage());
            return new TaskList();
        }
    }
}
