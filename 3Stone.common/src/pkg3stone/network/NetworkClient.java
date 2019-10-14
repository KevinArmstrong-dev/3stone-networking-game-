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
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import pkg3stone.engine.Board;
import pkg3stone.engine.Move;
import pkg3stone.engine.Piece;

/**
 *
 * @author KEVIN
 */
public class NetworkClient {

    private Socket socket;
    private InputStream in;
    private OutputStream out;

    private Piece currentColor;
    
    /**
     * Creates NetworkClient object and connects to requested server
     * @param hostServer
     * @param port
     */
    public NetworkClient(String hostServer, int port) {
        try {
            // Create socket that is connected to server on specified port
            this.socket = new Socket(hostServer, port);

            this.in = socket.getInputStream();
            this.out = socket.getOutputStream();
        } catch (IOException ex) {
            Logger.getLogger(NetworkServerPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Piece getCurrentColor()
    {
        return this.currentColor;
    }
    
    public void waitStartTheGameMessage()
    {
        try {
            StartTheGameMessage startTheGameMessage = StartTheGameMessage.read(in);
            this.currentColor = startTheGameMessage.getCurrentColor();
        } catch (IOException ex) {
            Logger.getLogger(NetworkClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public MoveMessage readMoveMessage()
    {
        try {
            return MoveMessage.read(in);
        } catch (IOException ex) {
            Logger.getLogger(NetworkClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void write(MoveMessage moveMessage)
    {
        try {
            moveMessage.write(out);
        } catch (IOException ex) {
            Logger.getLogger(NetworkClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
