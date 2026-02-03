package silver;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Main class to start the Silver application.
 */
public class Main extends Application {
    
    public Main(String filePathString) {
        // Silver silver = new Silver(filePathString);
        // silver.run();
    }

    public Main() {
        this(Silver.DATA_FILEPATH);
    }

    @Override
    public void start(Stage stage) {
        // JavaFX application entry point (if needed)
        Label helloWorld = new Label("Hello World!"); // Creating a new Label control
        Scene scene = new Scene(helloWorld); // Setting the scene to be our Label

        stage.setScene(scene); // Setting the stage to show our scene
        stage.show(); // Render the stage.
    }

}
