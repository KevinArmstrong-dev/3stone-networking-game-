package pkg3stone.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import pkg3stone.console.LogDisplay;
import pkg3stone.engine.AbstractPlayer;
import pkg3stone.engine.Board;
import pkg3stone.engine.Move;
import pkg3stone.engine.MoveType;
import pkg3stone.engine.Piece;
import pkg3stone.engine.Result;

/**
 * NetworkServerPlayer Class
 *
 * @author Svitlana Myronova
 */
public class NetworkServerPlayer extends AbstractPlayer {

    /**
     * Logger
     */
    private static final Logger LOG = Logger.getLogger(NetworkServerPlayer.class.getName());

    public static final int DEFAULT_PORT = 50000;

    private final Socket connectedClientSocket;
    private InputStream clientIn;
    private OutputStream clientOut;

    /**
     * Constructor
     *
     * @param connectedClientSocket
     */
    public NetworkServerPlayer(Socket connectedClientSocket) {
        this.connectedClientSocket = connectedClientSocket;
    }

    /**
     * Called once by Game before game begins to notify player about it's color
     *
     * @param piece
     * @throws java.lang.Exception
     */
    @Override
    public void startTheGame(Piece piece) throws Exception {
        super.startTheGame(piece);

        LOG.log(Level.INFO, "Waiting player connection");
        clientIn = connectedClientSocket.getInputStream();
        clientOut = connectedClientSocket.getOutputStream();

        LOG.log(Level.INFO, "Sending startTheGame");
        StartTheGameMessage startTheGameMessage = new StartTheGameMessage(piece);
        startTheGameMessage.write(clientOut);

    }

    /**
     * Method called by Game to ask player about its next move
     *
     * @param board
     * @return Move
     * @throws java.io.IOException
     */
    @Override
    public Move chooseMove(Board board) throws IOException {
        LOG.log(Level.INFO, "Waiting the MoveMessge from client");
        MoveMessage moveMessage = MoveMessage.read(clientIn);
        if (moveMessage.getMoveType() != MoveType.PROPOSED) {
            LOG.log(Level.SEVERE, "Wrong moveType received");
            return null;
        }
        LOG.log(Level.INFO, "Got MoveMessge from client: {0}", moveMessage.getMove());
        return moveMessage.getMove();
    }

    /**
     * Called by Game if Move returned by chooseMove is illegal for current game
     * state.
     *
     * @param moveType
     * @param move
     * @throws java.io.IOException
     */
    @Override
    public void moveOutcome(MoveType moveType, Move move) throws IOException {
        LOG.log(Level.INFO, "Sending moveOutcome");
        MoveMessage mm = new MoveMessage(moveType, getCurrentColor(), move);
        mm.write(clientOut);
    }

    /**
     * Called by Game to ask player to prepare for new chooseMove call
     *
     * @param board
     * @throws java.io.IOException
     */
    @Override
    public void prepareMove(Board board) throws IOException {
        Move lastMove = board.getLastMove();
        if (lastMove != null) {
            LOG.log(Level.INFO, "Sending last move");
            MoveMessageWithResult moveMessageWithResult = new MoveMessageWithResult(MoveType.LAST_MOVE_AND_CONTINUE,
                    board.getPiece(lastMove), lastMove, board.resultOfGame(), board.getPlayerBlackPieces(), board.getPlayerWhitePieces());
            moveMessageWithResult.write(clientOut);
        }
    }

    /**
     * Called by Game to notify about game result
     *
     * @param board
     * @param result
     * @throws java.io.IOException
     */
    @Override
    public void gameOver(Board board, Result result) throws IOException {
        LOG.log(Level.INFO, "Sending game over");
        Move lastMove = board.getLastMove();
        MoveMessageWithResult moveMessageWithResult = new MoveMessageWithResult(MoveType.LAST_MOVE_AND_GAME_OVER,
                board.getPiece(lastMove), lastMove, result, board.getPlayerBlackPieces(), board.getPlayerWhitePieces());
        moveMessageWithResult.write(clientOut);

        LOG.log(Level.INFO, "Sending result");
        ResultMessage resultMessage = new ResultMessage(result);
        resultMessage.write(clientOut);
    }

    /**
     * Needs to be called to cleanup resources created by this player.
     */
    @Override
    public void close() {
        try {
            clientIn.close();
            clientOut.close();
            connectedClientSocket.close();
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method will close the client resources like the streams and socket
     */
    public void closeClient() {
        try {
            clientIn.close();
            clientOut.close();
            connectedClientSocket.close();
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }

    }
}
