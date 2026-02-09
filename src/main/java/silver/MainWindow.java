package silver;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane implements SilverUI.MessageCallback {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Silver silver;
    private SilverGraphicalUI ui;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.png"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/silver.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        ui = new SilverGraphicalUI();
        ui.setMessageCallback(this);
        silver = new Silver(Silver.DATA_FILEPATH, ui);
        silver.initialize();
    }

    @Override
    public void onMessage(String message) {
        // Add Silver's response message immediately as it's generated
        dialogContainer.getChildren().add(
            DialogBox.getSilverDialog(message, dukeImage)
        );
    }

    /**
     * Handles the window close event to save data.
     */
    public void handleWindowClose() {
        silver.processCommand("bye");
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Silver's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();

        // Display user input immediately
        dialogContainer.getChildren().add(
            DialogBox.getUserDialog(input, userImage)
        );

        // Process command - responses will be displayed via callback
        silver.processCommand(input);

        userInput.clear();
    }
}
