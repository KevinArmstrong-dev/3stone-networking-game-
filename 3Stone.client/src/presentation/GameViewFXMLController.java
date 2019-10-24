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
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
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
 * GameViewFXMLController Class
 *
 * @author Kevin Armstrong
 */
public class GameViewFXMLController implements INetworkClientClient {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="whiteScoreLbl"
    private Label whiteScoreLbl; // Value injected by FXMLLoader

    @FXML // fx:id="blackScoreLbl"
    private Label blackScoreLbl; // Value injected by FXMLLoader

    @FXML // fx:id="whiteStonesLbl"
    private Label whiteStonesLbl; // Value injected by FXMLLoader

    @FXML // fx:id="blackStonesLbl"
    private Label blackStonesLbl; // Value injected by FXMLLoader

    @FXML // fx:id="gameGrid"
    private GridPane gameGrid; // Value injected by FXMLLoader

    private Piece[][] pieces;
    private Button[][] buttons;
    private Button lastPlacedStone;

    @FXML // fx:id="exitButton"
    private Button exitButton; // Value injected by FXMLLoader

    
    @FXML // fx:id="gamesPlayedlbl"
    private Label gamesPlayedlbl; // Value injected by FXMLLoader
    
    @FXML // Handler on exit button
    void exitButtonHandle(ActionEvent event) {
        System.out.println("Closing the Game");
        Platform.exit();
    }
    private NetworkClient networkClient;

    @FXML // fx:id="restartBtn"
    private Button restartBtn; // Value injected by FXMLLoader
    
    int gameCount = 1;

    @FXML
    void handleRestartBtn(ActionEvent event) {
        gameCount+=1;
         gamesPlayedlbl.setText(gameCount+ "");
        gameGrid.getChildren().clear();
        initGrid();
        enableAllButtons();
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert gameGrid != null : "fx:id=\"gameGrid\" was not injected: check your FXML file 'GameViewFXML.fxml'.";
     
        initGrid();
       gamesPlayedlbl.setText(gameCount+ "");
    }

    /**
     * Action on a click button
     *
     * @param button
     * @param row
     * @param col
     */
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
     * Initialize Grid
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
        //this.buttons[5][5].setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
        this.buttons[5][5].setId("barred");
        this.buttons[5][5].setDisable(true);
    }

    /**
     * Connect to server
     *
     * @param address
     * @param port
     * @throws IOException
     */
    public void connectToServer(String address, int port) throws IOException {
        this.networkClient = new NetworkClient(address, port);
    }

    /**
     * Method will be called to show result
     *
     * @param result
     */
    @Override
    public void updateResult(Result result, int whiteStones, int blackStones) {
        this.whiteScoreLbl.setText("" + result.getWhiteScore());
        this.blackScoreLbl.setText("" + result.getBlackScore());
        this.whiteStonesLbl.setText("" + whiteStones);
        this.blackStonesLbl.setText("" + blackStones);
    }

    /**
     * Method will be called to place the stone on the board
     *
     * @param piece
     * @param move
     */
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

    /**
     * Method will be called to report about an illegal move
     *
     * @param move
     */
    @Override
    public void reportIllegalMove(Move move) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Your move is illegal");
        alert.setHeaderText("Your move is illegal");
        alert.setContentText("Your move is illegal");
        alert.showAndWait();
    }

    /**
     * Method will be called to report result
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
    }

    /**
     * Disable all buttons
     */
    private void disableAllButtons() {
        for (Button[] row : this.buttons) {
            for (Button b : row) {
                b.setDisable(true);
            }
        }
    }
    
        /**
     * Disable all buttons
     */
    private void enableAllButtons() {
        for (Button[] row : this.buttons) {
            for (Button b : row) {
                b.setDisable(false);
            }
        }
    }
    
}
