/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author 1638876
 */
public class MainApp extends Application {

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
        primaryStage.getIcons().add(
                new Image(MainApp.class
                        .getResourceAsStream("/img/images.jpg")));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
