/**
 * Sample Skeleton for 'GameViewFXML.fxml' Controller Class
 */
package presentation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import pkg3stone.engine.Move;
import pkg3stone.engine.MoveType;
import pkg3stone.engine.Piece;
import pkg3stone.network.MoveMessage;
import pkg3stone.network.NetworkClient;

public class GameViewFXMLController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="gameGrid"
    private GridPane gameGrid; // Value injected by FXMLLoader

    private Piece[][] pieces;
    private Button[][] buttons;

    private NetworkClient networkClient;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert gameGrid != null : "fx:id=\"gameGrid\" was not injected: check your FXML file 'GameViewFXML.fxml'.";

        initGrid();
    }

    private void testGrid() {
        this.pieces = new Piece[11][11];
    }

    private void onButtonClicked(Button button, int row, int col) {
        //Propose move to server
        MoveMessage moveMessage = new MoveMessage(MoveType.PROPOSED, new Move(row, col));
        networkClient.write(moveMessage);

        //Read server responce
        moveMessage = networkClient.readMoveMessage();

        if (moveMessage.getMoveType() == MoveType.ILLEGAL) {
            //TODO: show error here
            return;
        }
        if( moveMessage.getMoveType() != MoveType.CONFIRMED)
        {
            throw new IllegalStateException("Wrong responce received");
        }

        if (networkClient.getCurrentColor() == Piece.WHITE) {
            button.setText("W");
        } else {
            button.setText("B");
        }
       
        //Read move of other player
        moveMessage = networkClient.readMoveMessage();
        if (moveMessage.getMoveType() != MoveType.LAST_MOVE) {
            throw new IllegalStateException("Reeived wrong move message");
        }
        Button otherPlayerButton = this.buttons[moveMessage.getMove().getRow()][moveMessage.getMove().getColumn()];
        if (networkClient.getCurrentColor() == Piece.WHITE) {
            otherPlayerButton.setText("B");
        } else {
            otherPlayerButton.setText("W");
        }
    }

    private void initGrid() {
        this.buttons = new Button[11][11];

        for (int row = 0; row < 11; row++) {
            for (int col = 0; col < 11; col++) {

                int savedRow = row;
                int savedCol = col;
                Button stone = new Button("");
                stone.setOnAction(event -> {
                    onButtonClicked(stone, savedRow, savedCol);
                });
                gameGrid.add(stone, col, row);

                this.buttons[row][col] = stone;
            }
        }
    }

    public void connectToServer(String address, int port) throws IOException {
        this.networkClient = new NetworkClient(address, port);
        this.networkClient.waitStartTheGameMessage();
    }
}
