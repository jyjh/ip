package silver;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Gson adapter for serializing and deserializing Task polymorphically.
 * Handles Todo, Deadline, and Events task types.
 */
public class TaskAdapter implements JsonSerializer<Task>, JsonDeserializer<Task> {

    @Override
    public JsonElement serialize(Task task, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        
        // Add type identifier
        if (task instanceof Todo) {
            jsonObject.addProperty("type", "todo");
        } else if (task instanceof Deadline) {
            jsonObject.addProperty("type", "deadline");
        } else if (task instanceof Events) {
            jsonObject.addProperty("type", "event");
        } else {
            throw new JsonParseException("Unknown task type: " + task.getClass().getSimpleName());
        }
        
        // Add common fields
        jsonObject.addProperty("description", task.getDescription());
        jsonObject.addProperty("isDone", task.isDone);
        jsonObject.add("noteUid", context.serialize(task.getNoteUid()));
        
        // Add type-specific fields
        if (task instanceof Deadline) {
            jsonObject.add("by", context.serialize(((Deadline) task).by));
        } else if (task instanceof Events) {
            jsonObject.add("from", context.serialize(((Events) task).from));
            jsonObject.add("to", context.serialize(((Events) task).to));
        }
        
        return jsonObject;
    }

    @Override
    public Task deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) 
            throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        
        // Handle legacy data format without type field - default to "todo"
        String type;
        JsonElement typeElement = jsonObject.get("type");
        if (typeElement == null || typeElement.isJsonNull()) {
            type = "todo"; // Default legacy tasks to todo
        } else {
            type = typeElement.getAsString();
        }
        
        String description = jsonObject.get("description").getAsString();
        boolean isDone = jsonObject.get("isDone").getAsBoolean();
        
        JsonElement noteUidElement = jsonObject.get("noteUid");
        String noteUid = (noteUidElement == null || noteUidElement.isJsonNull()) ? null : noteUidElement.getAsString();
        
        switch (type) {
            case "todo":
                Todo todo = new Todo(description, isDone, noteUid);
                return todo;
            case "deadline":
                String by = jsonObject.get("by").getAsString();
                Deadline deadline = new Deadline(description, isDone, noteUid, java.time.LocalDate.parse(by));
                return deadline;
            case "event":
                String from = jsonObject.get("from").getAsString();
                String to = jsonObject.get("to").getAsString();
                Events event = new Events(description, isDone, noteUid, 
                    java.time.LocalDate.parse(from), java.time.LocalDate.parse(to));
                return event;
            default:
                throw new JsonParseException("Unknown task type: " + type);
        }
    }
}