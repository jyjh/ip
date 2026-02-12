package silver;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Main class to start the Silver application in GUI mode.
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            // Set undecorated style to remove system window decorations
            stage.initStyle(StageStyle.UNDECORATED);
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            // Pass the stage to the controller for window control
            MainWindow controller = fxmlLoader.getController();
            controller.setStage(stage);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
