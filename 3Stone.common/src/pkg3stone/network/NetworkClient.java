/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg3stone.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import pkg3stone.engine.Move;
import pkg3stone.engine.MoveType;
import pkg3stone.engine.Piece;

/**
 *
 * @author KEVIN
 */
public class NetworkClient {

    private final Socket socket;
    private final InputStream in;
    private final OutputStream out;

    private Piece currentColor;

    /**
     * Creates NetworkClient object and connects to requested server
     *
     * @param hostServer
     * @param port
     * @throws java.io.IOException
     */
    public NetworkClient(String hostServer, int port) throws IOException {
        // Create socket that is connected to server on specified port
        this.socket = new Socket(hostServer, port);

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
        MoveMessage moveMessage = new MoveMessage(MoveType.PROPOSED, getCurrentColor(), move);
        write(moveMessage);

        //Read server responce
        moveMessage = readMoveMessage();

        if (moveMessage.getMoveType() == MoveType.ILLEGAL) {
            client.reportIllegalMove(move);
            return;
        }
        if (moveMessage.getMoveType() != MoveType.CONFIRMED) {
            throw new IllegalStateException("Wrong responce received");
        }

        client.placeStone(this.currentColor, move);

        //Read move of other player
        moveMessage = readMoveMessage();
        if (moveMessage.getMoveType() != MoveType.LAST_MOVE_AND_CONTINUE
                && moveMessage.getMoveType() != MoveType.LAST_MOVE_AND_GAME_OVER) {
            throw new IllegalStateException("Reeived wrong move message");
        }
        client.placeStone(moveMessage.getPiece(), moveMessage.getMove());

        if(moveMessage.getMoveType() == MoveType.LAST_MOVE_AND_CONTINUE)
            return;
        
        ResultMessage resultMessage = readResultMessage();
        client.reportResult(resultMessage.getResult());
    }

    public Piece getCurrentColor() {
        return this.currentColor;
    }

    private void waitStartTheGameMessage() throws IOException {
        StartTheGameMessage startTheGameMessage = StartTheGameMessage.read(in);
        this.currentColor = startTheGameMessage.getCurrentColor();
    }

    private MoveMessage readMoveMessage() throws IOException {
        return MoveMessage.read(in);
    }

    private ResultMessage readResultMessage() throws IOException {
        return ResultMessage.read(in);
    }

    private void write(MoveMessage moveMessage) throws IOException {
        moveMessage.write(out);
    }
}
