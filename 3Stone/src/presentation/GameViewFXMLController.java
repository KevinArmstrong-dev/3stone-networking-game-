/**
 * Sample Skeleton for 'GameViewFXML.fxml' Controller Class
 */

package presentation;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import pkg3stone.Piece;

public class GameViewFXMLController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="testLbl"
    private Label testLbl; // Value injected by FXMLLoader
    
    private Piece[][] pieces;
    @FXML // fx:id="gameGrid"
    private GridPane gameGrid; // Value injected by FXMLLoader


    @FXML
    void buttonHandler(ActionEvent event) {
         System.out.println("I have been Clicked");
            this.testLbl.setText("Clicked");
     }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert testLbl != null : "fx:id=\"testLbl\" was not injected: check your FXML file 'GameViewFXML.fxml'.";
//        assert testBtn1 != null : "fx:id=\"testBtn1\" was not injected: check your FXML file 'GameViewFXML.fxml'.";
        assert gameGrid != null : "fx:id=\"gameGrid\" was not injected: check your FXML file 'GameViewFXML.fxml'.";

        initGrid();
    }
    
    private void testGrid(){
        this.pieces = new Piece[11][11];
        
      //  for(int i =0)
    }
    private void initGrid(){
        for(int row = 0; row < 11; row++){
            for(int col = 0; col < 11 ; col++){
                Button stone= new Button("r"+row+"c"+col);
                stone.setId("stone");
                stone.setOnAction(event -> {
                    String pos =stone.getText();
                    this.testLbl.setText(pos);
                    coordinates(pos);
                });
                gameGrid.add(stone, col, row);
            }
        }
    }
    
    public int[] coordinates(String btn){
        int[] btnCord = new int[2];
        
        String[] temp = btn.split("c");

        
        btnCord[0] = Integer.parseInt(temp[0].substring(1));
        btnCord[1] = Integer.parseInt(temp[1]);
        System.out.println("Row " +btnCord[0] + " column" + btnCord[1]);
        return btnCord;
    }
}
