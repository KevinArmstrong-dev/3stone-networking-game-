/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import java.io.IOException;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author 1638876
 */
public class MainApp extends Application {
    
    @Override
    public void start(Stage primaryStage) {
         
        try {
//            // Load root layout from fxml file.
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("GameViewFXML.fxml"));
//
//           Parent root = loader.load();
//           GameViewFXMLController controller = loader.getController();
            
           FXMLLoader loader = new FXMLLoader(getClass().getResource("RootFXML.fxml"));

           Parent root = loader.load();
           RootFXMLController controller = loader.getController();
          
           Scene scene = new Scene(root);
           primaryStage.setTitle("3 stone Game");
           primaryStage.setScene(scene);
           primaryStage.show();
            //rootLayout = (AnchorPane) loader.load();

        } catch (IOException ex) {
           // errorAlert("initRootLayout()");
           System.out.print("Error in the lodader");
            Platform.exit();
        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
