package silver;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

    private DialogBox(String text, Image img) {
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
        
        // Style as bot message (gray bubble, left-aligned)
        dialog.setStyle("-fx-background-color: #182533; -fx-background-radius: 10; -fx-text-fill: white; -fx-wrap-text: true; -fx-padding: 10 15 10 15;");
    }
    
    /**
     * Styles the dialog box as a user message (green bubble, right-aligned).
     */
    private void styleAsUserMessage() {
        setAlignment(Pos.TOP_RIGHT);

        // Style as user message (green bubble, right-aligned)
        dialog.setStyle("-fx-background-color: #2b5278; -fx-background-radius: 10; "
            + "-fx-text-fill: white; -fx-wrap-text: true; -fx-padding: 10 15 10 15;");
    }

    public static DialogBox getUserDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.styleAsUserMessage();
        return db;
    }

    public static DialogBox getSilverDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.flip();
        return db;
    }
}
