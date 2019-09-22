/**
 * Sample Skeleton for 'GameViewFXML.fxml' Controller Class
 */

package presentation;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public class GameViewFXMLController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="gameGrid"
    private GridPane gameGrid; // Value injected by FXMLLoader

    @FXML
    void mouseEnterHandle(ActionEvent event) {
        Node cellClicked = (Node)event.getSource();
        Integer colIndex = GridPane.getColumnIndex(cellClicked);
        Integer rowIndex = GridPane.getRowIndex(cellClicked);
        System.out.printf("Mouse entered cell [%d, %d]%n", colIndex.intValue(),
                rowIndex.intValue());
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert gameGrid != null : "fx:id=\"gameGrid\" was not injected: check your FXML file 'GameViewFXML.fxml'.";

    }
}
