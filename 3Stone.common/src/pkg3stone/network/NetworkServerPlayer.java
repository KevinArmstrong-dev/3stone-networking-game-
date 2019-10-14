/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg3stone.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;
import pkg3stone.engine.AbstractPlayer;
import pkg3stone.engine.Board;
import pkg3stone.engine.IPlayer;
import pkg3stone.engine.Move;
import pkg3stone.engine.MoveType;
import pkg3stone.engine.Piece;

/**
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
     *
     * @param port
     * @throws IOException
     */
    public NetworkServerPlayer(int port) throws IOException {

        // Create a server socket to accept client connection requests
        serverSocket = new ServerSocket(port);
    }

    @Override
    public void startTheGame(Piece piece) {
        super.startTheGame(piece);
        try {
            connectedClientSocket = serverSocket.accept();     // Get client connection
            clientIn = connectedClientSocket.getInputStream();
            clientOut = connectedClientSocket.getOutputStream();
        } catch (IOException ex) {
            Logger.getLogger(NetworkServerPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Move chooseMove(Board board) {
        try {
            MoveMessage moveMessage = MoveMessage.read(clientIn);
            if(moveMessage.getMoveType() != MoveType.PROPOSED)
            {
                Logger.getLogger(NetworkServerPlayer.class.getName()).log(Level.SEVERE, "Wrong moveType received");
                return null;
            }
            return moveMessage.getMove();
        } catch (IOException ex) {
            Logger.getLogger(NetworkServerPlayer.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public void moveOutcome(MoveType moveType, Move move) {
        try {
            MoveMessage mm = new MoveMessage(moveType, move);
            mm.write(clientOut);
        } catch (IOException ex) {
            Logger.getLogger(NetworkServerPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

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
