package pkg3stone.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    private static final int BUFSIZE = 32;

    private final ServerSocket serverSocket;
    private Socket connectedClientSocket;
    private InputStream clientIn;
    private OutputStream clientOut;

    /**
     * Constructor
     *
     * @param port
     * @throws IOException
     */
    public NetworkServerPlayer(int port) throws IOException {

        // Create a server socket to accept client connection requests
        serverSocket = new ServerSocket(port);
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

        Logger.getLogger(NetworkServerPlayer.class.getName()).log(Level.INFO, "Waiting player connection");
        connectedClientSocket = serverSocket.accept();     // Get client connection
        clientIn = connectedClientSocket.getInputStream();
        clientOut = connectedClientSocket.getOutputStream();

        Logger.getLogger(NetworkServerPlayer.class.getName()).log(Level.INFO, "Sending startTheGame");
        StartTheGameMessage startTheGameMessage = new StartTheGameMessage(piece);
        startTheGameMessage.write(clientOut);
    }

    /**
     * Method called by Game to ask player about its next move
     *
     * @param board
     * @return Move
     * @throws java.lang.Exception
     */
    @Override
    public Move chooseMove(Board board) throws IOException {
        Logger.getLogger(NetworkServerPlayer.class.getName()).log(Level.INFO, "Waiting the MoveMessge from client");
        MoveMessage moveMessage = MoveMessage.read(clientIn);
        if (moveMessage.getMoveType() != MoveType.PROPOSED) {
            Logger.getLogger(NetworkServerPlayer.class.getName()).log(Level.SEVERE, "Wrong moveType received");
            return null;
        }
        Logger.getLogger(NetworkServerPlayer.class.getName()).log(Level.INFO, "Got MoveMessge from client: {0}", moveMessage.getMove());
        return moveMessage.getMove();
    }

    /**
     * Called by Game if Move returned by chooseMove is illegal for current game
     * state.
     *
     * @param moveType
     * @param move
     * @throws java.lang.Exception
     */
    @Override
    public void moveOutcome(MoveType moveType, Move move) throws IOException {
        Logger.getLogger(NetworkServerPlayer.class.getName()).log(Level.INFO, "Sending moveOutcome");
        MoveMessage mm = new MoveMessage(moveType, getCurrentColor(), move);
        mm.write(clientOut);
    }

    /**
     * Called by Game to ask player to prepare for new chooseMove call
     *
     * @param board
     * @throws java.lang.Exception
     */
    @Override
    public void prepareMove(Board board) throws IOException {
        if (board.getLastMove() != null) {
            Logger.getLogger(NetworkServerPlayer.class.getName()).log(Level.INFO, "Sending last move");
            MoveMessage mvoeMessage = new MoveMessage(MoveType.LAST_MOVE_AND_CONTINUE, board.getLastStonePlayed(), board.getLastMove());
            mvoeMessage.write(clientOut);
        }
    }

    /**
     * Called by Game to notify about game result
     * @param board
     * @param result
     * @throws java.lang.Exception
     */
    @Override
    public void gameOver(Board board, Result result) throws IOException {
        Logger.getLogger(NetworkServerPlayer.class.getName()).log(Level.INFO, "Sending game over");
        MoveMessage mm = new MoveMessage(MoveType.LAST_MOVE_AND_GAME_OVER, board.getLastStonePlayed(), board.getLastMove());
        mm.write(clientOut);

        Logger.getLogger(NetworkServerPlayer.class.getName()).log(Level.INFO, "Sending result");
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
            serverSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(NetworkServerPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
