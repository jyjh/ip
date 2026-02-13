package silver;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
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

    private User user;
    private User silverUser;
    private Stage stage;
    private boolean isMaximized = false;
    private double previousWidth = 0;
    private double previousHeight = 0;
    private double previousX = 0;
    private double previousY = 0;

    // Screen coordinates for mouse operations
    private double initialMouseScreenX = 0;
    private double initialMouseScreenY = 0;
    private double initialStageX = 0;
    private double initialStageY = 0;
    private double initialStageWidth = 0;
    private double initialStageHeight = 0;

    // Resize direction enum
    private enum ResizeDirection {
        NONE, N, S, E, W, NE, NW, SE, SW, DRAG
    }

    private ResizeDirection resizeDirection = ResizeDirection.NONE;
    private boolean isDragging = false;
    private final int RESIZE_BORDER = 8;
    private final int DRAG_HEIGHT = 30;
    private final double MIN_WIDTH = 300;
    private final double MIN_HEIGHT = 200;

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
                setupWindowControls();
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
     * Makes the window draggable and resizable by handling mouse events.
     */
    @FXML
    private void setupWindowControls() {
        AnchorPane root = (AnchorPane) stage.getScene().getRoot();

        // Mouse moved: Determine cursor direction
        root.addEventFilter(MouseEvent.MOUSE_MOVED, (MouseEvent event) -> {
            if (isMaximized) {
                root.setCursor(javafx.scene.Cursor.DEFAULT);
                return;
            }

            double x = event.getX();
            double y = event.getY();
            double width = root.getWidth();
            double height = root.getHeight();

            // Determine resize direction
            resizeDirection = ResizeDirection.NONE;

            if (y <= RESIZE_BORDER && x <= RESIZE_BORDER) {
                resizeDirection = ResizeDirection.NW;
                root.setCursor(javafx.scene.Cursor.NW_RESIZE);
            } else if (y <= RESIZE_BORDER && x >= width - RESIZE_BORDER) {
                resizeDirection = ResizeDirection.NE;
                root.setCursor(javafx.scene.Cursor.NE_RESIZE);
            } else if (y >= height - RESIZE_BORDER && x <= RESIZE_BORDER) {
                resizeDirection = ResizeDirection.SW;
                root.setCursor(javafx.scene.Cursor.SW_RESIZE);
            } else if (y >= height - RESIZE_BORDER && x >= width - RESIZE_BORDER) {
                resizeDirection = ResizeDirection.SE;
                root.setCursor(javafx.scene.Cursor.SE_RESIZE);
            } else if (y <= RESIZE_BORDER) {
                resizeDirection = ResizeDirection.N;
                root.setCursor(javafx.scene.Cursor.N_RESIZE);
            } else if (y >= height - RESIZE_BORDER) {
                resizeDirection = ResizeDirection.S;
                root.setCursor(javafx.scene.Cursor.S_RESIZE);
            } else if (x <= RESIZE_BORDER) {
                resizeDirection = ResizeDirection.W;
                root.setCursor(javafx.scene.Cursor.W_RESIZE);
            } else if (x >= width - RESIZE_BORDER) {
                resizeDirection = ResizeDirection.E;
                root.setCursor(javafx.scene.Cursor.E_RESIZE);
            } else if (y <= DRAG_HEIGHT) {
                resizeDirection = ResizeDirection.DRAG;
                root.setCursor(javafx.scene.Cursor.DEFAULT);
            } else {
                resizeDirection = ResizeDirection.NONE;
                root.setCursor(javafx.scene.Cursor.DEFAULT);
            }
        });

        // Mouse pressed: Record initial screen positions and set dragging flag
        root.addEventFilter(MouseEvent.MOUSE_PRESSED, (MouseEvent event) -> {
            initialMouseScreenX = event.getScreenX();
            initialMouseScreenY = event.getScreenY();
            initialStageX = stage.getX();
            initialStageY = stage.getY();
            initialStageWidth = stage.getWidth();
            initialStageHeight = stage.getHeight();
            isDragging = true;
        });

        // Mouse dragged: Handle dragging or resizing based on direction
        root.addEventFilter(MouseEvent.MOUSE_DRAGGED, (MouseEvent event) -> {
            if (isMaximized) {
                return;
            }

            if (resizeDirection == ResizeDirection.DRAG) {
                // Drag window using screen coordinates
                double deltaX = event.getScreenX() - initialMouseScreenX;
                double deltaY = event.getScreenY() - initialMouseScreenY;
                stage.setX(initialStageX + deltaX);
                stage.setY(initialStageY + deltaY);
            } else if (resizeDirection != ResizeDirection.NONE) {
                // Resize window using screen coordinates
                handleResize(event);
            }
        });

        // Mouse exited: Reset cursor and direction only if not currently dragging
        root.addEventFilter(MouseEvent.MOUSE_EXITED, (MouseEvent event) -> {
            if (!isDragging) {
                resizeDirection = ResizeDirection.NONE;
                root.setCursor(javafx.scene.Cursor.DEFAULT);
            }
        });

        // Mouse released: Reset dragging flag
        root.addEventFilter(MouseEvent.MOUSE_RELEASED, (MouseEvent event) -> {
            isDragging = false;
            resizeDirection = ResizeDirection.NONE;
            root.setCursor(javafx.scene.Cursor.DEFAULT);
        });
    }

    /**
     * Handles window resizing based on the current resize direction using screen coordinates.
     */
    private void handleResize(MouseEvent event) {
        double currentScreenX = event.getScreenX();
        double currentScreenY = event.getScreenY();
        double deltaX = currentScreenX - initialMouseScreenX;
        double deltaY = currentScreenY - initialMouseScreenY;

        switch (resizeDirection) {
        case E:
            // Right edge: only width changes
            double newWidthE = initialStageWidth + deltaX;
            if (newWidthE >= MIN_WIDTH) {
                stage.setWidth(newWidthE);
            }
            break;

        case W:
            // Left edge: both X and width change
            double newWidthW = initialStageWidth - deltaX;
            if (newWidthW >= MIN_WIDTH) {
                stage.setX(initialStageX + deltaX);
                stage.setWidth(newWidthW);
            }
            break;

        case S:
            // Bottom edge: only height changes
            double newHeightS = initialStageHeight + deltaY;
            if (newHeightS >= MIN_HEIGHT) {
                stage.setHeight(newHeightS);
            }
            break;

        case N:
            // Top edge: both Y and height change
            double newHeightN = initialStageHeight - deltaY;
            if (newHeightN >= MIN_HEIGHT) {
                stage.setY(initialStageY + deltaY);
                stage.setHeight(newHeightN);
            }
            break;

        case SE:
            // Bottom-right corner: width and height change
            double newWidthSE = initialStageWidth + deltaX;
            double newHeightSE = initialStageHeight + deltaY;
            if (newWidthSE >= MIN_WIDTH) {
                stage.setWidth(newWidthSE);
            }
            if (newHeightSE >= MIN_HEIGHT) {
                stage.setHeight(newHeightSE);
            }
            break;

        case SW:
            // Bottom-left corner: X, width, and height change
            double newWidthSW = initialStageWidth - deltaX;
            double newHeightSW = initialStageHeight + deltaY;
            if (newWidthSW >= MIN_WIDTH) {
                stage.setX(initialStageX + deltaX);
                stage.setWidth(newWidthSW);
            }
            if (newHeightSW >= MIN_HEIGHT) {
                stage.setHeight(newHeightSW);
            }
            break;

        case NE:
            // Top-right corner: Y, width, and height change
            double newWidthNE = initialStageWidth + deltaX;
            double newHeightNE = initialStageHeight - deltaY;
            if (newWidthNE >= MIN_WIDTH) {
                stage.setWidth(newWidthNE);
            }
            if (newHeightNE >= MIN_HEIGHT) {
                stage.setY(initialStageY + deltaY);
                stage.setHeight(newHeightNE);
            }
            break;

        case NW:
            // Top-left corner: X, Y, width, and height change
            double newWidthNW = initialStageWidth - deltaX;
            double newHeightNW = initialStageHeight - deltaY;
            if (newWidthNW >= MIN_WIDTH) {
                stage.setX(initialStageX + deltaX);
                stage.setWidth(newWidthNW);
            }
            if (newHeightNW >= MIN_HEIGHT) {
                stage.setY(initialStageY + deltaY);
                stage.setHeight(newHeightNW);
            }
            break;

        default:
            break;
        }
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