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
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                Files.createDirectories(file.getParentFile().toPath());
                file.createNewFile();
            }
        } catch (Exception e) {
            System.out.println("Error initializing filesystem: " + e.getMessage());
        }
        return new File(filePath);
    }

    /**
     * Saves the given TaskList data to the specified file.
     * @param file
     * @param data
     */
    public static void saveData(File file, TaskList data) {
        try (BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(file))) {
            for (Task task : data.getAllTasks()) {
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
        // Implementation for loading data from a file
        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(file))) {
            ArrayList<Task> tasks = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|", 2);
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
