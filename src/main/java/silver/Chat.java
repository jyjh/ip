package silver;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages the chat message log and handles grouping of consecutive messages from the same user.
 * For terminal UI, message grouping, profile pictures, and usernames are ignored.
 */
public class Chat {

    /**
     * Represents a single message in the chat log.
     */
    public static class Message {
        private final User user;
        private final String text;
        private final DialogBox.MessagePosition position;
        /**
         * Creates a new Message instance.
         * @param user
         * @param text
         * @param position
         */
        public Message(User user, String text, DialogBox.MessagePosition position) {
            this.user = user;
            this.text = text;
            this.position = position;
        }

        public User getUser() {
            return user;
        }

        public String getText() {
            return text;
        }

        public DialogBox.MessagePosition getPosition() {
            return position;
        }
    }

    /**
     * Callback interface for UI updates when messages are added to the chat.
     */
    public interface ChatCallback {
        /**
         * Called when a new message is added to the chat.
         * For terminal UI implementations, user and position can be ignored.
         *
         * @param message The message that was added
         */
        void onMessageAdded(Message message);

        /**
         * Called when a previous message needs to be updated (e.g., position changed during grouping).
         * For terminal UI, this can be ignored.
         *
         * @param message The updated message
         */
        void onMessageUpdated(Message message);
    }

    private final List<Message> messageLog;
    private User previousUser;
    private final ChatCallback callback;

    /**
     * Creates a new Chat instance with the specified callback.
     *
     * @param callback The callback for UI updates
     */
    public Chat(ChatCallback callback) {
        this.messageLog = new ArrayList<>();
        this.previousUser = null;
        this.callback = callback;
    }

    /**
     * Adds a new message to the chat. Handles message grouping and triggers appropriate callbacks.
     *
     * @param user The user sending the message
     * @param text The message text
     */
    public void addMessage(User user, String text) {
        DialogBox.MessagePosition position = determineMessagePosition(user);
        // Update previous message if needed (when continuing a group)
        if (previousUser != null && previousUser.equals(user)) {
            updatePreviousMessagePosition();
        }
        // Create and add the new message
        Message message = new Message(user, text, position);
        messageLog.add(message);
        // Notify callback of new message
        if (callback != null) {
            callback.onMessageAdded(message);
        }
        previousUser = user;
    }

    /**
     * Determines the position of a new message within a group based on the previous user.
     *
     * @param currentUser The user sending the new message
     * @return The MessagePosition for the new message
     */
    private DialogBox.MessagePosition determineMessagePosition(User currentUser) {
        if (previousUser == null || !previousUser.equals(currentUser)) {
            // Different user or first message - standalone
            return DialogBox.MessagePosition.STANDALONE;
        }
        // Same user as previous - new message is always last in group
        return DialogBox.MessagePosition.LAST_IN_GROUP;
    }

    /**
     * Updates the position of the previous message when a new message from the same user is added.
     * Handles the transition between STANDALONE -> FIRST_IN_GROUP -> MIDDLE_IN_GROUP.
     */
    private void updatePreviousMessagePosition() {
        if (messageLog.isEmpty()) {
            return;
        }
        int lastIndex = messageLog.size() - 1;
        Message previousMessage = messageLog.get(lastIndex);
        DialogBox.MessagePosition currentPosition = previousMessage.getPosition();
        // Determine new position
        DialogBox.MessagePosition newPosition;
        switch (currentPosition) {
        case STANDALONE:
            newPosition = DialogBox.MessagePosition.FIRST_IN_GROUP;
            break;
        case FIRST_IN_GROUP:
        case MIDDLE_IN_GROUP:
        case LAST_IN_GROUP:
            newPosition = DialogBox.MessagePosition.MIDDLE_IN_GROUP;
            break;
        default:
            newPosition = currentPosition;
        }
        // Create updated message
        Message updatedMessage = new Message(
            previousMessage.getUser(),
            previousMessage.getText(),
            newPosition
        );
        // Update message log
        messageLog.set(lastIndex, updatedMessage);
        // Notify callback of update
        if (callback != null) {
            callback.onMessageUpdated(updatedMessage);
        }
    }

    /**
     * Gets the current message log.
     *
     * @return The list of all messages in the chat
     */
    public List<Message> getMessageLog() {
        return new ArrayList<>(messageLog);
    }

    /**
     * Gets the number of messages in the chat.
     *
     * @return The message count
     */
    public int getMessageCount() {
        return messageLog.size();
    }
}
