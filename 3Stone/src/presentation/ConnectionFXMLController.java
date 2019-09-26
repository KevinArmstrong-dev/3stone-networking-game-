/**
 * Sample Skeleton for 'ConnectionFXML.fxml' Controller Class
 */

package presentation;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ConnectionFXMLController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="serverIpLbl"
    private Label serverIpLbl; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert serverIpLbl != null : "fx:id=\"serverIpLbl\" was not injected: check your FXML file 'ConnectionFXML.fxml'.";

    }
}
