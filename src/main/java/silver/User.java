package silver;

import javafx.scene.image.Image;

/**
 * Represents a user in the chat system with a name, profile picture, and chat side position.
 */
public class User {
    /**
     * Represents the side of the chat area where messages from this user appear.
     */
    public enum Side {
        LEFT, RIGHT
    }

    private final String name;
    private final Image profilePicture;
    private final Side side;

    /**
     * Creates a new User with the specified name, profile picture, and chat side.
     *
     * @param name The name of the user
     * @param profilePicture The profile picture image for the user
     * @param side The side of the chat area where this user's messages appear
     */
    public User(String name, Image profilePicture, Side side) {
        this.name = name;
        this.profilePicture = profilePicture;
        this.side = side;
    }

    /**
     * Gets the name of the user.
     *
     * @return The user's name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the profile picture of the user.
     *
     * @return The user's profile picture image
     */
    public Image getProfilePicture() {
        return profilePicture;
    }

    /**
     * Gets the side of the chat area where this user's messages appear.
     *
     * @return The side (LEFT or RIGHT) where messages appear
     */
    public Side getSide() {
        return side;
    }
}
