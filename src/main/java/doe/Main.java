package doe;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * JavaFX entry point for Doe's FXML-based graphical interface.
 */
public class Main extends Application {
    private final doe chatbot = new doe("todo.txt");

    /**
     * Loads the main window and injects the existing Doe application into its controller.
     *
     * @param stage Primary JavaFX stage.
     * @throws IOException If the FXML layout cannot be loaded.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
        AnchorPane root = loader.load();
        Scene scene = new Scene(root);

        stage.setTitle("Doe");
        stage.setScene(scene);
        stage.setMinHeight(680);
        stage.setMinWidth(480);
        loader.<MainWindow>getController().setDoe(chatbot);
        stage.show();
    }
}
