/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg3stone.engine;

/**
 *
 * @author svitl
 */
public class Game {

    private final IDisplay display;
    private final IPlayer whitePlayer;
    private final IPlayer blackPlayer;
    private final Board board;

    // constructor
    public Game(IDisplay display, IPlayer whitePlayer, IPlayer blackPlayer) {
        this.display = display;

        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;

        this.board = new Board();
    }

    private void makePlayerMove(IPlayer player) throws Exception {
        player.prepareMove(board);

        Move move = null;
        while (true) {
            move = player.chooseMove(board);
            if (board.isPlayLegal(player.getCurrentColor(), move)) {
                break;
            }
            player.moveOutcome(MoveType.ILLEGAL, move);
        }
        board.placeStone(player.getCurrentColor(), move);
        player.moveOutcome(MoveType.CONFIRMED, move);
        display.ShowBoard(board);
    }

    /**
     * play method that is responsible for the course of the game
     * @throws java.lang.Exception
     */
    public void play() throws Exception {
        display.ShowBoard(board);

        whitePlayer.startTheGame(Piece.WHITE);
        blackPlayer.startTheGame(Piece.BLACK);

        while (true) {
            makePlayerMove(whitePlayer);
            if (board.isGameOver()) {
                break;
            }

            makePlayerMove(blackPlayer);
            if (board.isGameOver()) {
                break;
            }
        }

        Result result = board.resultOfGame();
        display.ShowResult(result);

        whitePlayer.gameOver(board, result);
        blackPlayer.gameOver(board, result);

        whitePlayer.close();
        blackPlayer.close();
    }
}
