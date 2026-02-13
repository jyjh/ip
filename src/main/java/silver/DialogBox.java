package silver;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Represents a dialog box consisting of an ImageView to represent the speaker's face
 * and a label containing text from the speaker.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    /**
     * Represents the position of a message within a group of consecutive messages from the same speaker.
     */
    public enum MessagePosition {
        STANDALONE,      // Message is alone in its group
        FIRST_IN_GROUP,  // First message in a group of consecutive messages
        MIDDLE_IN_GROUP, // Middle message in a group of consecutive messages
        LAST_IN_GROUP    // Last message in a group of consecutive messages
    }

    private DialogBox(String text, Image img, MessagePosition position) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text);
        displayPicture.setImage(img);
        dialog.setWrapText(true);
        dialog.setTextOverrun(OverrunStyle.CLIP);
        
        // Apply bot styling based on message position (default, will be overridden for user messages)
        applyPositionStyling(position);
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     * Styles it as a bot response message.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
    }
    
    /**
     * Styles the dialog box as a user message (green bubble, right-aligned).
     * Also applies user-specific position-based styling.
     */
    private void styleAsUserMessage(MessagePosition position) {
        setAlignment(Pos.TOP_RIGHT);
        applyUserPositionStyling(position);
    }
    
    /**
     * Applies styling based on the message's position within a group.
     * This controls avatar visibility, bubble corner radius, and padding.
     */
    private void applyPositionStyling(MessagePosition position) {
        switch (position) {
        case STANDALONE:
            // Show avatar, all corners rounded, normal spacing
            displayPicture.setVisible(true);
            displayPicture.setOpacity(1.0);
            setPadding(new Insets(10.0, 10.0, 10.0, 10.0));
            dialog.setStyle("-fx-background-color: #182533; -fx-background-radius: 10; "
                + "-fx-text-fill: white; -fx-wrap-text: true; -fx-padding: 10 15 10 15;");
            break;
        case FIRST_IN_GROUP:
            // Show avatar, bottom corners squared, reduced bottom spacing
            displayPicture.setVisible(true);
            displayPicture.setOpacity(1.0);
            setPadding(new Insets(10.0, 10.0, 2.0, 10.0));
            dialog.setStyle("-fx-background-color: #182533; -fx-background-radius: 10 10 2 2; "
                + "-fx-text-fill: white; -fx-wrap-text: true; -fx-padding: 10 15 10 15;");
            break;
        case MIDDLE_IN_GROUP:
            // Hide avatar (but keep it in layout for alignment), no rounded corners, reduced spacing
            displayPicture.setVisible(true);
            displayPicture.setOpacity(0.0);
            setPadding(new Insets(2.0, 10.0, 2.0, 10.0));
            dialog.setStyle("-fx-background-color: #182533; -fx-background-radius: 0; "
                + "-fx-text-fill: white; -fx-wrap-text: true; -fx-padding: 10 15 10 15;");
            break;
        case LAST_IN_GROUP:
            // Hide avatar (but keep it in layout for alignment), top corners squared, reduced top spacing
            displayPicture.setVisible(true);
            displayPicture.setOpacity(0.0);
            setPadding(new Insets(2.0, 10.0, 10.0, 10.0));
            dialog.setStyle("-fx-background-color: #182533; -fx-background-radius: 2 2 10 10; "
                + "-fx-text-fill: white; -fx-wrap-text: true; -fx-padding: 10 15 10 15;");
            break;
        }
    }
    
    /**
     * Applies user message styling based on the message's position within a group.
     */
    private void applyUserPositionStyling(MessagePosition position) {
        switch (position) {
        case STANDALONE:
            // Show avatar, all corners rounded, normal spacing
            displayPicture.setVisible(true);
            displayPicture.setOpacity(1.0);
            setPadding(new Insets(10.0, 10.0, 10.0, 10.0));
            dialog.setStyle("-fx-background-color: #2b5278; -fx-background-radius: 10; "
                + "-fx-text-fill: white; -fx-wrap-text: true; -fx-padding: 10 15 10 15;");
            break;
        case FIRST_IN_GROUP:
            // Show avatar, bottom corners squared, reduced bottom spacing
            displayPicture.setVisible(true);
            displayPicture.setOpacity(1.0);
            setPadding(new Insets(10.0, 10.0, 2.0, 10.0));
            dialog.setStyle("-fx-background-color: #2b5278; -fx-background-radius: 10 10 2 2; "
                + "-fx-text-fill: white; -fx-wrap-text: true; -fx-padding: 10 15 10 15;");
            break;
        case MIDDLE_IN_GROUP:
            // Hide avatar (but keep it in layout for alignment), no rounded corners, reduced spacing
            displayPicture.setVisible(true);
            displayPicture.setOpacity(0.0);
            setPadding(new Insets(2.0, 10.0, 2.0, 10.0));
            dialog.setStyle("-fx-background-color: #2b5278; -fx-background-radius: 0; "
                + "-fx-text-fill: white; -fx-wrap-text: true; -fx-padding: 10 15 10 15;");
            break;
        case LAST_IN_GROUP:
            // Hide avatar (but keep it in layout for alignment), top corners squared, reduced top spacing
            displayPicture.setVisible(true);
            displayPicture.setOpacity(0.0);
            setPadding(new Insets(2.0, 10.0, 10.0, 10.0));
            dialog.setStyle("-fx-background-color: #2b5278; -fx-background-radius: 2 2 10 10; "
                + "-fx-text-fill: white; -fx-wrap-text: true; -fx-padding: 10 15 10 15;");
            break;
        }
    }
    
    /**
     * Creates a dialog box for a given user with the specified message and position.
     * The dialog box styling and alignment are determined by the user's side (LEFT or RIGHT).
     *
     * @param user The user sending the message
     * @param text The message text
     * @param position The position of the message within a group
     * @return A DialogBox instance styled appropriately for the user
     */
    public static DialogBox getDialog(User user, String text, MessagePosition position) {
        var db = new DialogBox(text, user.getProfilePicture(), position);
        
        // Style and position based on user's side
        if (user.getSide() == User.Side.RIGHT) {
            db.styleAsUserMessage(position);
        } else {
            db.flip();
        }
        
        return db;
    }
    
    /**
     * Gets the dialog label containing the message text.
     */
    public Label getDialogLabel() {
        return dialog;
    }
    
    /**
     * Gets the display picture (avatar) ImageView.
     */
    public ImageView getDisplayPicture() {
        return displayPicture;
    }
}
