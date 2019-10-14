/**
 * Sample Skeleton for 'RootFXML.fxml' Controller Class
 */

package presentation;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class RootFXMLController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="rootPane"
    private StackPane rootPane; // Value injected by FXMLLoader
    
    private List<Pane> paneList= new ArrayList<Pane>();

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert rootPane != null : "fx:id=\"rootPane\" was not injected: check your FXML file 'RootFXML.fxml'.";
        initWelcomeWindow();
        initGameView();
    
    }
    private void initWelcomeWindow(){
      
        try {
            FXMLLoader loader = new FXMLLoader();           
            loader.setResources(resources);

            loader.setLocation(MainApp.class
                    .getResource("connectionFXML.fxml"));
            AnchorPane connectionGui = loader.load();
             paneList.add(connectionGui);
            // Give the controller the data object.
            ConnectionFXMLController connectionPane = loader.getController();
            connectionPane.getPaneList(paneList);
            rootPane.getChildren().add(connectionGui);
            
        } catch (IOException ex) {
            System.out.println("Error loading the welcome Pane");
            Platform.exit();
        }
    }
    
    private void initGameView(){
       
        try {
            FXMLLoader loader = new FXMLLoader();           
            loader.setResources(resources);

            loader.setLocation(MainApp.class
                    .getResource("GameViewFXML.fxml"));
            AnchorPane gamePane = loader.load();
            
            gamePane.setVisible(false);
            
            paneList.add(gamePane);
            // Give the controller the data object.
            GameViewFXMLController gameController = loader.getController();
              
            rootPane.getChildren().add(gamePane);
            
        } catch (IOException ex) {
            System.out.println("Game pane error");
            Platform.exit();
        }
    }
    
}
