package silver;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane implements SilverUI.MessageCallback, Chat.ChatCallback {

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
    private Chat chat;
    private WindowControlHandler windowControlHandler;

    private User user;
    private User silverUser;
    private Stage stage;
    private boolean isMaximized = false;
    private double previousWidth = 0;
    private double previousHeight = 0;
    private double previousX = 0;
    private double previousY = 0;

    /**
     * Initializes main window and sets up the Silver application.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        // Create users
        Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.png"));
        Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/silver.png"));
        user = new User("User", userImage, User.Side.RIGHT);
        silverUser = new User("Silver", dukeImage, User.Side.LEFT);
        // Create chat with this as callback
        chat = new Chat(this);
        ui = new SilverGraphicalUI();
        ui.setMessageCallback(this);
        silver = new Silver(Silver.DATA_FILEPATH, ui);
        silver.initialize();

        // Setup window controls after a short delay to ensure stage is set
        javafx.application.Platform.runLater(() -> {
            if (stage != null) {
                windowControlHandler = new WindowControlHandler();
                windowControlHandler.setup(stage);
            }
        });
    }

    @Override
    public void onMessage(String message) {
        // Add Silver's response message via Chat
        chat.addMessage(silverUser, message);
    }

    /**
     * Sets the stage reference for window control.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Handles the window close event to save data.
     */
    public void handleWindowClose() {
        silver.processCommand("bye");
    }

    /**
     * Minimizes window.
     */
    @FXML
    private void handleMinimize() {
        stage.setIconified(true);
    }

    /**
     * Toggles maximize/restore state of window.
     */
    @FXML
    private void handleMaximize() {
        if (isMaximized) {
            // Restore window
            stage.setWidth(previousWidth);
            stage.setHeight(previousHeight);
            stage.setX(previousX);
            stage.setY(previousY);
            stage.setResizable(true);
            isMaximized = false;
        } else {
            // Maximize window
            previousWidth = stage.getWidth();
            previousHeight = stage.getHeight();
            previousX = stage.getX();
            previousY = stage.getY();
            stage.setWidth(javafx.stage.Screen.getPrimary().getVisualBounds().getWidth());
            stage.setHeight(javafx.stage.Screen.getPrimary().getVisualBounds().getHeight());
            stage.setX(0);
            stage.setY(0);
            stage.setResizable(false);
            isMaximized = true;
        }
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleClose() {
        handleWindowClose();
        stage.close();
    }

    /**
     * Creates a dialog box for user input and then processes the command.
     * Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        // Display user input via Chat
        chat.addMessage(user, input);
        // Process command - responses will be displayed via callback
        silver.processCommand(input);
        userInput.clear();
    }

    // Chat.ChatCallback implementation

    @Override
    public void onMessageAdded(Chat.Message message) {
        // Add new dialog box to container
        dialogContainer.getChildren().add(
            DialogBox.getDialog(
                message.getUser(),
                message.getText(),
                message.getPosition()
            )
        );
    }

    @Override
    public void onMessageUpdated(Chat.Message message) {
        // Replace the last dialog box with updated version
        int lastIndex = dialogContainer.getChildren().size() - 1;
        if (lastIndex >= 0) {
            DialogBox updatedDialog = DialogBox.getDialog(
                message.getUser(),
                message.getText(),
                message.getPosition()
            );
            dialogContainer.getChildren().set(lastIndex, updatedDialog);
        }
    }
}
