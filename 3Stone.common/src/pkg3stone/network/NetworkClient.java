package pkg3stone.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import pkg3stone.engine.Move;
import pkg3stone.engine.MoveType;
import pkg3stone.engine.Piece;

/**
 * NetworkClient Class
 *
 * @author Kevin Armstrong
 */
public class NetworkClient {

        /**
     * Logger
     */
    private static final Logger LOG = Logger.getLogger(NetworkClient.class.getName());

    private final Socket socket;
    private final InputStream in;
    private final OutputStream out;

    private Piece currentColor;

    /**
     * Creates NetworkClient object and connects to requested server
     *
     * @param serverAddress
     * @throws java.io.IOException
     */
    public NetworkClient(InetSocketAddress serverAddress) throws IOException {
        // Create socket that is connected to server on specified port
        this.socket = new Socket(serverAddress.getAddress(), serverAddress.getPort());

        this.in = socket.getInputStream();
        this.out = socket.getOutputStream();

        waitStartTheGameMessage();
    }

    /**
     * Executes one move by player
     *
     * @param move
     * @param client
     * @throws IOException
     */
    public void makeMove(Move move, INetworkClientClient client) throws IOException {
        //Propose move to server                                                           
        {
            MoveMessage moveMessage = new MoveMessage(MoveType.PROPOSED, getCurrentColor(), move);
            write(moveMessage);
        }

        {
            //Read server responce
            MoveMessage moveMessage = readMoveMessage();

            if (moveMessage.getMoveType() == MoveType.ILLEGAL) {
                client.reportIllegalMove(move);
                return;
            }
            if (moveMessage.getMoveType() != MoveType.CONFIRMED) {
                throw new IllegalStateException("Wrong responce received");
            }
        }

        client.placeStone(this.currentColor, move);

        {
            //Read move of other player and current result
            MoveMessageWithResult moveMessageWithResult = readMoveMessageWithResult();
            client.updateResult(moveMessageWithResult.getResult(), moveMessageWithResult.getBlackStones(), moveMessageWithResult.getWhiteStones());

            if (moveMessageWithResult.getMoveType() != MoveType.LAST_MOVE_AND_CONTINUE
                    && moveMessageWithResult.getMoveType() != MoveType.LAST_MOVE_AND_GAME_OVER) {
                throw new IllegalStateException("Reeived wrong move message");
            }
            client.placeStone(moveMessageWithResult.getPiece(), moveMessageWithResult.getMove());

            if (moveMessageWithResult.getMoveType() == MoveType.LAST_MOVE_AND_CONTINUE) {
                return;
            }
        }

        {
            ResultMessage resultMessage = readResultMessage();
            client.reportResult(resultMessage.getResult());
        }
    }

    /**
     * Current control getter
     *
     * @return
     */
    public Piece getCurrentColor() {
        return this.currentColor;
    }

    /**
     * Wait start the Game message
     *
     * @throws IOException
     */
    private void waitStartTheGameMessage() throws IOException {
        StartTheGameMessage startTheGameMessage = StartTheGameMessage.read(in);
        this.currentColor = startTheGameMessage.getCurrentColor();
    }

    /**
     * Read MoveMessage
     *
     * @throws IOException
     */
    private MoveMessage readMoveMessage() throws IOException {
        return MoveMessage.read(in);
    }

    /**
     * Read MoveMessageWithResult
     *
     * @throws IOException
     */
    private MoveMessageWithResult readMoveMessageWithResult() throws IOException {
        return MoveMessageWithResult.read(in);
    }

    /**
     * Read ResultMessage
     *
     * @throws IOException
     */
    private ResultMessage readResultMessage() throws IOException {
        return ResultMessage.read(in);
    }

    /**
     * Write MoveMessage
     *
     * @param moveMessage
     * @throws IOException
     */
    public void write(MoveMessage moveMessage) throws IOException {
        moveMessage.write(out);
    }

    /**
     *This method will close the resources used by the client
     */
    public void closeConnection() {
        try {
            socket.close();
            in.close();
            out.close();
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "Failed to close resource", ex);
        }

    }
}
