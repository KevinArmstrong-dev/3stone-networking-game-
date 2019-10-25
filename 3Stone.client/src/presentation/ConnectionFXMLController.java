package presentation;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.stage.Stage;
import pkg3stone.network.NetworkServerBroadcaster;
import pkg3stone.network.NetworkServerFinder;
import pkg3stone.network.NetworkServerPlayer;

/**
 * ConnectionFXMLController Class
 *
 * @author Kevin Armstrong
 */
public class ConnectionFXMLController {

    private Stage primaryStage;
    private ArrayList<InetSocketAddress> foundServers;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="accordion"
    private Accordion accordion; // Value injected by FXMLLoader

    @FXML // fx:id="findServerPane"
    private TitledPane findServerPane; // Value injected by FXMLLoader

    @FXML // fx:id="serverComboBox"
    private ComboBox<String> serverComboBox; // Value injected by FXMLLoader

    @FXML // fx:id="broadcasterPortTxt"
    private TextField broadcasterPortTxt; // Value injected by FXMLLoader

    @FXML // fx:id="serverAddressTxt"
    private TextField serverAddressTxt; // Value injected by FXMLLoader

    @FXML // fx:id="serverPortTxt"
    private TextField serverPortTxt; // Value injected by FXMLLoader

    @FXML // fx:id="connectBtn"
    private Button connectBtn; // Value injected by FXMLLoader

    @FXML // fx:id="exitBtn"
    private Button exitBtn; // Value injected by FXMLLoader

    @FXML
    void connectFoundServerBtnHandler(ActionEvent event) {
        if (this.serverComboBox.getItems().isEmpty()) {
            return;
        }

        final int selectedServer = this.serverComboBox.getItems().indexOf(this.serverComboBox.getValue());
        startTheGame(this.foundServers.get(selectedServer));
    }

    @FXML
    void refreshBtnHandler(ActionEvent event) {
        findServers();
    }

    @FXML //Handler on the connect button
    void connectBtnHandler(ActionEvent event) {
        try {
            InetSocketAddress serverAddress = new InetSocketAddress(InetAddress.getByName(serverAddressTxt.getText()), Integer.parseInt(serverPortTxt.getText()));
            startTheGame(serverAddress);
        } catch (UnknownHostException ex) {
            Logger.getLogger(ConnectionFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML //Handler on the exit button
    void exitBtnHandler(ActionEvent event) {
        Platform.exit();
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert serverComboBox != null : "fx:id=\"serverComboBox\" was not injected: check your FXML file 'ConnectionFXML.fxml'.";
        assert broadcasterPortTxt != null : "fx:id=\"broadcasterPortTxt\" was not injected: check your FXML file 'ConnectionFXML.fxml'.";
        assert serverAddressTxt != null : "fx:id=\"serverAddressTxt\" was not injected: check your FXML file 'ConnectionFXML.fxml'.";
        assert serverPortTxt != null : "fx:id=\"serverPortTxt\" was not injected: check your FXML file 'ConnectionFXML.fxml'.";
        assert connectBtn != null : "fx:id=\"connectBtn\" was not injected: check your FXML file 'ConnectionFXML.fxml'.";
        assert exitBtn != null : "fx:id=\"exitBtn\" was not injected: check your FXML file 'ConnectionFXML.fxml'.";

        broadcasterPortTxt.setText(Integer.toString(NetworkServerBroadcaster.DEFAULT_PORT));
        serverAddressTxt.setText("localhost");
        serverPortTxt.setText(Integer.toString(NetworkServerPlayer.DEFAULT_PORT));

        findServers();
        
        accordion.setExpandedPane(findServerPane);
    }

    /**
     * Set PrimaryStage
     *
     * @param primaryStage
     */
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * This helper method will look for listening severs 
     * on a specific port number
     * 
     */
    private void findServers() {
        try {
            this.serverComboBox.getItems().clear();

            NetworkServerFinder finder = new NetworkServerFinder();
            this.foundServers = finder.findServers(NetworkServerBroadcaster.DEFAULT_PORT);
            this.foundServers.forEach((sa) -> {
                this.serverComboBox.getItems().add(sa.toString());
            });
            if (!this.foundServers.isEmpty()) {
                this.serverComboBox.setValue(this.foundServers.get(0).toString());
            }
        } catch (SocketException ex) {
            Logger.getLogger(ConnectionFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConnectionFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This helper method helps in starting the game by passing the 
     * inetAdderess or IP address
     * 
     * 
     * @param serverAddress 
     */
    private void startTheGame(InetSocketAddress serverAddress) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("GameViewFXML.fxml"));
            Parent root = loader.load();
            GameViewFXMLController controller = loader.getController();

            controller.connectToServer(serverAddress);

            Scene scene = new Scene(root);
            this.primaryStage.setScene(scene);
        } catch (IOException ex) {
            Logger.getLogger(ConnectionFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            Platform.exit();
        }
    }
}
