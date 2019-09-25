/**
 * Sample Skeleton for 'GameViewFXML.fxml' Controller Class
 */

package presentation;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class GameViewFXMLController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="testLbl"
    private Label testLbl; // Value injected by FXMLLoader

//    @FXML // fx:id="testBtn1"
//    private Button testBtn1; // Value injected by FXMLLoader

    @FXML // fx:id="gameGrid"
    private GridPane gameGrid; // Value injected by FXMLLoader

    @FXML
    void mouseEnterHandle(ActionEvent event) {
    }

//    @FXML
//    void buttonHandler(ActionEvent event) {
//         System.out.println("I have been Clicked");
//            this.testLbl.setText("Clicked");
//    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert testLbl != null : "fx:id=\"testLbl\" was not injected: check your FXML file 'GameViewFXML.fxml'.";
//        assert testBtn1 != null : "fx:id=\"testBtn1\" was not injected: check your FXML file 'GameViewFXML.fxml'.";
        assert gameGrid != null : "fx:id=\"gameGrid\" was not injected: check your FXML file 'GameViewFXML.fxml'.";

        initGrid();
    }
    
    private void initGrid(){
        for(int row = 0; row < 11; row++){
            for(int col = 0; col < 11 ; col++){
                Button stone= new Button(row+""+col);
                stone.setOnAction(event -> {
                    String pos = "Clicked "+stone;
                    this.testLbl.setText(pos);
                });
                gameGrid.add(stone, col, row);
            }
        }
    }
}
