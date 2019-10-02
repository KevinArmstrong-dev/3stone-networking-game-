/**
 * Sample Skeleton for 'ConnectionFXML.fxml' Controller Class
 */

package presentation;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import networking.ClientPlayer;
import networking.Server;

public class ConnectionFXMLController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="connectBtn"
    private Button connectBtn; // Value injected by FXMLLoader

    @FXML // fx:id="ipTxt"
    private TextField ipTxt; // Value injected by FXMLLoader

    @FXML // fx:id="exitBtn"
    private Button exitBtn; // Value injected by FXMLLoader

    @FXML // fx:id="serverIpLbl"
    private Label serverIpLbl; // Value injected by FXMLLoader
    private ClientPlayer client;
    private List<Pane> panes;

    private Server server;
    @FXML
    void connectBtnHandler(ActionEvent event) {
        client = new ClientPlayer(ipTxt.getText());
    //This hides the welcome Pane
        panes.get(0).setVisible(false);
        
        //This displays the game window
        panes.get(1).setVisible(true);
    }

    @FXML
    void exitBtnHandler(ActionEvent event) {
        //Close the App
        System.out.println("Closing the Application");
        Platform.exit();
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        //server = new Server();
        assert connectBtn != null : "fx:id=\"connectBtn\" was not injected: check your FXML file 'ConnectionFXML.fxml'.";
        assert ipTxt != null : "fx:id=\"ipTxt\" was not injected: check your FXML file 'ConnectionFXML.fxml'.";
        assert exitBtn != null : "fx:id=\"exitBtn\" was not injected: check your FXML file 'ConnectionFXML.fxml'.";
        assert serverIpLbl != null : "fx:id=\"serverIpLbl\" was not injected: check your FXML file 'ConnectionFXML.fxml'.";

    }
    
    public void getPaneList(List panes){
        this.panes = panes;
    }
}
