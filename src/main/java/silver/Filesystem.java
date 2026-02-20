package silver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

/**
 * Provides utility methods for file operations using JSON format.
 */
public class Filesystem {

    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(Task.class, new TaskAdapter())
            .registerTypeAdapter(java.time.LocalDate.class, new LocalDateAdapter())
            .create();

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
     * Saves tasks to a JSON file.
     * @param file the file to save to
     * @param tasks the list of tasks to save
     */
    public static void saveTasks(File file, List<Task> tasks) {
        assert file != null : "File cannot be null when saving tasks";
        assert tasks != null : "Task list cannot be null when saving";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            Type taskListType = new TypeToken<ArrayList<Task>>() {}.getType();
            gson.toJson(tasks, taskListType, writer);
        } catch (Exception e) {
            System.out.println("Error saving tasks to filesystem: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Loads tasks from a JSON file.
     * @param file the file to load from
     * @return the list of tasks
     */
    public static List<Task> loadTasks(File file) {
        assert file != null : "File cannot be null when loading tasks";

        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            Type taskListType = new TypeToken<ArrayList<Task>>() {}.getType();
            List<Task> tasks = gson.fromJson(reader, taskListType);
            return tasks != null ? tasks : new ArrayList<>();
        } catch (IOException e) {
            System.out.println("Error parsing tasks JSON: " + e.getMessage());
            handleCorruptedFile(file);
            return loadTasks(file);
        } catch (JsonSyntaxException e) {
            System.out.println("Error parsing tasks JSON syntax: " + e.getMessage());
            handleCorruptedFile(file);
            return loadTasks(file);
        }
    }

    /**
     * Saves notes to a JSON file.
     * @param file the file to save to
     * @param notes the map of notes to save (UID -> Note)
     */
    public static void saveNotes(File file, Map<String, Note> notes) {
        assert file != null : "File cannot be null when saving notes";
        assert notes != null : "Notes map cannot be null when saving";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            gson.toJson(notes, writer);
        } catch (IOException e) {
            System.out.println("Error saving notes to filesystem: " + e.getMessage());
        }
    }

    /**
     * Loads notes from a JSON file.
     * @param file the file to load from
     * @return the map of notes (UID -> Note)
     */
    public static Map<String, Note> loadNotes(File file) {
        assert file != null : "File cannot be null when loading notes";

        if (!file.exists() || file.length() == 0) {
            return new HashMap<>();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            Type notesMapType = new TypeToken<HashMap<String, Note>>() {}.getType();
            Map<String, Note> notes = gson.fromJson(reader, notesMapType);
            return notes != null ? notes : new HashMap<>();
        } catch (IOException e) {
            System.out.println("Error loading notes from filesystem: " + e.getMessage());
            return new HashMap<>();
        } catch (JsonIOException e) {
            System.out.println("Error parsing notes JSON: " + e.getMessage());
            return new HashMap<>();
        } catch (JsonSyntaxException e) {
            System.out.println("Error parsing notes JSON syntax: " + e.getMessage());
            handleCorruptedFile(file);
            return loadNotes(file);
        } 
    }

    /**
     * Handles a corrupted file by printing a warning, appending '-old' to the file name,
     * and creating a new blank file with the original name.
     * @param file
     */
    public static void handleCorruptedFile(File file) {
        System.out.println("Warning: Detected corrupted file at " + file.getPath() + ".");
        String newName = file.getPath() + "-old";
        File backupFile = new File(newName);
        if (backupFile.exists()) {
            System.out.println("Warning: Backup file " + newName + " already exists. Overwriting it.");
            backupFile.delete();
        }
        file.renameTo(backupFile);
        try {
            file.createNewFile();
        } catch (IOException e) {
            System.out.println("Error creating new file: " + e.getMessage());
        }
    }
}
