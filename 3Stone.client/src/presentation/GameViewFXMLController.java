package presentation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import pkg3stone.engine.Move;
import pkg3stone.engine.Piece;
import pkg3stone.engine.Result;
import pkg3stone.network.INetworkClientClient;
import pkg3stone.network.NetworkClient;

/**
 * This controller will display the grid, which has the same dimension as the board.
 * This controller will be displayed if the client was able to connect to a server
 * 
 * @author KEVIN
 */
public class GameViewFXMLController implements INetworkClientClient {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
    @FXML // fx:id="blackLbl"
    private Label blackLbl; // Value injected by FXMLLoader
     
    @FXML // fx:id="white"
    private Label white; // Value injected by FXMLLoader
    
    @FXML // fx:id="scoreLbl"
    private Label scoreLbl; // Value injected by FXMLLoader

    @FXML // fx:id="black"
    private Label blackL; // Value injected by FXMLLoader
    
    @FXML // fx:id="whiteLbl"
    private Label whiteLbl; // Value injected by FXMLLoader
    
    @FXML // fx:id="gameGrid"
    private GridPane gameGrid; // Value injected by FXMLLoader

    private Piece[][] pieces;
    private Button[][] buttons;
    private Button lastPlacedStone;

    @FXML // fx:id="exitButton"
    private Button exitButton; // Value injected by FXMLLoader

    @FXML
    void exitButtonHandle(ActionEvent event) {
        System.out.println("Closing the Game");
        Platform.exit();
    }
    private NetworkClient networkClient;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert gameGrid != null : "fx:id=\"gameGrid\" was not injected: check your FXML file 'GameViewFXML.fxml'.";
         assert whiteLbl != null : "fx:id=\"whiteLbl\" was not injected: check your FXML file 'GameViewFXML.fxml'.";
        initGrid();
    }

    private void testGrid() {
        this.pieces = new Piece[11][11];
    }

    private void onButtonClicked(Button button, int row, int col) {
        try {
            Move move = new Move(row, col);
            networkClient.makeMove(move, this);
        } catch (IOException ex) {
            Logger.getLogger(GameViewFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            Platform.exit();
        }
    }
    
    /**
     * This helper method helps in populating the grid with buttons
     * IT attaches event listeners on the buttons as it is being created
     * 
     */
    private void initGrid() {
         
        this.buttons = new Button[11][11];

        for (int row = 0; row < 11; row++) {
            for (int col = 0; col < 11; col++) {

                int savedRow = row;
                int savedCol = col;
                Button stone = new Button("        ");
                stone.setId("stone");
                stone.getStyleClass().add("allStone");
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
    }

    @Override
    public void placeStone(Piece piece, Move move) {
        if (this.lastPlacedStone != null) {
            this.lastPlacedStone.setBorder(Border.EMPTY);
        }

        Button button = this.buttons[move.getRow()][move.getColumn()];
        switch (piece) {
            case BLANK:
                button.setText("        ");
                button.setId("stone");
                break;
            case WHITE:
                button.setText("  W  ");
                button.setId("whiteStone");
                button.getStyleClass().add("white");
                break;
            case BLACK:
                button.setText("  B  ");
                button.setId("blackStone");
                button.getStyleClass().add("black");
                break;
            case BARRED:
                button.setText("  X  ");
                button.setId("barred");
                break;
        }
        button.setDisable(true);

        this.lastPlacedStone = button;
        this.lastPlacedStone.setBorder(new Border(new BorderStroke(Color.RED,
                BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(3))));
    }

    @Override
    public void reportIllegalMove(Move move) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Your move is illegal");
        alert.setHeaderText("Your move is illegal");
        alert.setContentText("Your move is illegal");
        alert.showAndWait();
    }

    /**
     * 
     * @param result 
     */
    @Override
    public void reportResult(Result result) {
        disableAllButtons();
        
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Game is over");
        alert.setHeaderText("Game is over");
        alert.setContentText("Game is over. Result: " + result);
        alert.showAndWait();

         whiteLbl.setText(result.getWhiteScore()+"");
         blackLbl.setText(result.getBlackScore()+"");
         
    }

    /**
     * This method when called will disable all the buttons
     * inside the grid
     */
    private void disableAllButtons() {
        for (Button[] row : this.buttons) {
            for (Button b : row) {
                b.setDisable(true);
            }
        }
    }
}
