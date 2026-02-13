package silver;

import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Handles window dragging and resizing functionality.
 * Encapsulates all mouse event handling for window controls.
 */
public class WindowControlHandler {

    // Resize direction enum
    public enum ResizeDirection {
        NONE, N, S, E, W, NE, NW, SE, SW, DRAG
    }

    private Stage stage;
    private AnchorPane root;

    // State for window control
    private ResizeDirection resizeDirection = ResizeDirection.NONE;
    private boolean isDragging = false;

    // Screen coordinates for mouse operations
    private double initialMouseScreenX = 0;
    private double initialMouseScreenY = 0;
    private double initialStageX = 0;
    private double initialStageY = 0;
    private double initialStageWidth = 0;
    private double initialStageHeight = 0;

    // Constants
    private final int resizeBorder = 8;
    private final int dragHeight = 30;
    private final double minWidth = 300;
    private final double minHeight = 200;

    /**
     * Sets up window control event handlers.
     *
     * @param stage The stage to control
     */
    public void setup(Stage stage) {
        this.stage = stage;
        this.root = (AnchorPane) stage.getScene().getRoot();

        setupEventFilters();
    }

    /**
     * Registers all event filters for window control.
     */
    private void setupEventFilters() {
        root.addEventFilter(MouseEvent.MOUSE_MOVED, this::handleMouseMoved);
        root.addEventFilter(MouseEvent.MOUSE_PRESSED, this::handleMousePressed);
        root.addEventFilter(MouseEvent.MOUSE_DRAGGED, this::handleMouseDragged);
        root.addEventFilter(MouseEvent.MOUSE_EXITED, this::handleMouseExited);
        root.addEventFilter(MouseEvent.MOUSE_RELEASED, this::handleMouseReleased);
    }

    /**
     * Handles mouse moved events to determine cursor direction.
     */
    private void handleMouseMoved(MouseEvent event) {
        double x = event.getX();
        double y = event.getY();
        double width = root.getWidth();
        double height = root.getHeight();

        resizeDirection = ResizeDirection.NONE;

        if (y <= resizeBorder && x <= resizeBorder) {
            resizeDirection = ResizeDirection.NW;
            root.setCursor(Cursor.NW_RESIZE);
        } else if (y <= resizeBorder && x >= width - resizeBorder) {
            resizeDirection = ResizeDirection.NE;
            root.setCursor(Cursor.NE_RESIZE);
        } else if (y >= height - resizeBorder && x <= resizeBorder) {
            resizeDirection = ResizeDirection.SW;
            root.setCursor(Cursor.SW_RESIZE);
        } else if (y >= height - resizeBorder && x >= width - resizeBorder) {
            resizeDirection = ResizeDirection.SE;
            root.setCursor(Cursor.SE_RESIZE);
        } else if (y <= resizeBorder) {
            resizeDirection = ResizeDirection.N;
            root.setCursor(Cursor.N_RESIZE);
        } else if (y >= height - resizeBorder) {
            resizeDirection = ResizeDirection.S;
            root.setCursor(Cursor.S_RESIZE);
        } else if (x <= resizeBorder) {
            resizeDirection = ResizeDirection.W;
            root.setCursor(Cursor.W_RESIZE);
        } else if (x >= width - resizeBorder) {
            resizeDirection = ResizeDirection.E;
            root.setCursor(Cursor.E_RESIZE);
        } else if (y <= dragHeight) {
            resizeDirection = ResizeDirection.DRAG;
            root.setCursor(Cursor.DEFAULT);
        } else {
            resizeDirection = ResizeDirection.NONE;
            root.setCursor(Cursor.DEFAULT);
        }
    }

    /**
     * Handles mouse pressed events to record initial positions.
     */
    private void handleMousePressed(MouseEvent event) {
        initialMouseScreenX = event.getScreenX();
        initialMouseScreenY = event.getScreenY();
        initialStageX = stage.getX();
        initialStageY = stage.getY();
        initialStageWidth = stage.getWidth();
        initialStageHeight = stage.getHeight();
        isDragging = true;
    }

    /**
     * Handles mouse dragged events for dragging or resizing.
     */
    private void handleMouseDragged(MouseEvent event) {
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
    }

    /**
     * Handles mouse exited events to reset cursor and direction.
     */
    private void handleMouseExited(MouseEvent event) {
        if (!isDragging) {
            resizeDirection = ResizeDirection.NONE;
            root.setCursor(Cursor.DEFAULT);
        }
    }

    /**
     * Handles mouse released events to reset dragging flag.
     */
    private void handleMouseReleased(MouseEvent event) {
        isDragging = false;
        resizeDirection = ResizeDirection.NONE;
        root.setCursor(Cursor.DEFAULT);
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
            if (newWidthE >= minWidth) {
                stage.setWidth(newWidthE);
            }
            break;

        case W:
            // Left edge: both X and width change
            double newWidthW = initialStageWidth - deltaX;
            if (newWidthW >= minWidth) {
                stage.setX(initialStageX + deltaX);
                stage.setWidth(newWidthW);
            }
            break;

        case S:
            // Bottom edge: only height changes
            double newHeightS = initialStageHeight + deltaY;
            if (newHeightS >= minHeight) {
                stage.setHeight(newHeightS);
            }
            break;

        case N:
            // Top edge: both Y and height change
            double newHeightN = initialStageHeight - deltaY;
            if (newHeightN >= minHeight) {
                stage.setY(initialStageY + deltaY);
                stage.setHeight(newHeightN);
            }
            break;

        case SE:
            // Bottom-right corner: width and height change
            double newWidthSE = initialStageWidth + deltaX;
            double newHeightSE = initialStageHeight + deltaY;
            if (newWidthSE >= minWidth) {
                stage.setWidth(newWidthSE);
            }
            if (newHeightSE >= minHeight) {
                stage.setHeight(newHeightSE);
            }
            break;

        case SW:
            // Bottom-left corner: X, width, and height change
            double newWidthSW = initialStageWidth - deltaX;
            double newHeightSW = initialStageHeight + deltaY;
            if (newWidthSW >= minWidth) {
                stage.setX(initialStageX + deltaX);
                stage.setWidth(newWidthSW);
            }
            if (newHeightSW >= minHeight) {
                stage.setHeight(newHeightSW);
            }
            break;

        case NE:
            // Top-right corner: Y, width, and height change
            double newWidthNE = initialStageWidth + deltaX;
            double newHeightNE = initialStageHeight - deltaY;
            if (newWidthNE >= minWidth) {
                stage.setWidth(newWidthNE);
            }
            if (newHeightNE >= minHeight) {
                stage.setY(initialStageY + deltaY);
                stage.setHeight(newHeightNE);
            }
            break;

        case NW:
            // Top-left corner: X, Y, width, and height change
            double newWidthNW = initialStageWidth - deltaX;
            double newHeightNW = initialStageHeight - deltaY;
            if (newWidthNW >= minWidth) {
                stage.setX(initialStageX + deltaX);
                stage.setWidth(newWidthNW);
            }
            if (newHeightNW >= minHeight) {
                stage.setY(initialStageY + deltaY);
                stage.setHeight(newHeightNW);
            }
            break;

        default:
            break;
        }
    }
}