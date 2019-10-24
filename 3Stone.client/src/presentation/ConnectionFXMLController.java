package presentation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * ConnectionFXMLController Class
 *
 * @author Kevin Armstrong
 */
public class ConnectionFXMLController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="serverAddressTxt"
    private TextField serverAddressTxt; // Value injected by FXMLLoader

    @FXML // fx:id="connectBtn"
    private Button connectBtn; // Value injected by FXMLLoader

    @FXML // fx:id="exitBtn"
    private Button exitBtn; // Value injected by FXMLLoader

    @FXML // fx:id="serverPortTxt"
    private TextField serverPortTxt; // Value injected by FXMLLoader
    
    @FXML // fx:id="serverIp"
    private Label serverIp; // Value injected by FXMLLoader

    private Stage primaryStage;
    
    @FXML // fx:id="restartBtn"
    private Button restartBtn; // Value injected by FXMLLoader
    
    @FXML
    void handleRestartBtn(ActionEvent event) {

    }

    /**
     * Set PrimaryStage
     *
     * @param primaryStage
     */
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML //Handler on the connect button
    void connectBtnHandler(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("GameViewFXML.fxml"));
            Parent root = loader.load();
            GameViewFXMLController controller = loader.getController();

            controller.connectToServer(serverAddressTxt.getText(), Integer.parseInt(serverPortTxt.getText()));

            Scene scene = new Scene(root);
            this.primaryStage.setScene(scene);
        } catch (IOException ex) {
            Logger.getLogger(ConnectionFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            Platform.exit();
        }
    }

    @FXML //Handler on the exit button
    void exitBtnHandler(ActionEvent event) {
        Platform.exit();
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert serverAddressTxt != null : "fx:id=\"serverAddressTxt\" was not injected: check your FXML file 'ConnectionFXML.fxml'.";
        assert connectBtn != null : "fx:id=\"connectBtn\" was not injected: check your FXML file 'ConnectionFXML.fxml'.";
        assert exitBtn != null : "fx:id=\"exitBtn\" was not injected: check your FXML file 'ConnectionFXML.fxml'.";
        assert serverPortTxt != null : "fx:id=\"serverPortTxt\" was not injected: check your FXML file 'ConnectionFXML.fxml'.";

    }
}
