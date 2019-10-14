/**
 * Sample Skeleton for 'ConnectionFXML.fxml' Controller Class
 */

package presentation;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

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

    @FXML
    void connectBtnHandler(ActionEvent event) {
        
    }

    @FXML
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