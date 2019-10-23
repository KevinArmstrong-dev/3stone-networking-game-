package presentation;

import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * MainApp Class
 *
 * @author Kevin Armstrong
 */
public class MainApp extends Application {

    /**
    * Start application
    *
    * @param  primaryStage
    */
    @Override
    public void start(Stage primaryStage) {

        try {
            showConnectionForm(primaryStage);
        } catch (IOException ex) {
            // errorAlert("initRootLayout()");
            System.out.print("Error in the lodader");
            Platform.exit();
        }
    }

    /**
     * Shows connection form;
     *
     * @param primaryStage
     * @throws IOException
     */
    public void showConnectionForm(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ConnectionFXML.fxml"));
        Parent root = loader.load();
        ConnectionFXMLController controller = loader.getController();
        controller.setPrimaryStage(primaryStage);

        Scene scene = new Scene(root);
        primaryStage.setTitle("3 stone Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Main method of the MainApp Class
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
