/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg3stone.server;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import pkg3stone.console.LogDisplay;
import pkg3stone.engine.Game;
import pkg3stone.engine.IDisplay;
import pkg3stone.engine.IPlayer;
import pkg3stone.engine.SmartComputer;
import pkg3stone.network.NetworkServerPlayer;

/**
 *
 * @author Svitlana Myronova
 */
public class ServerGame implements Runnable {

    /**
     * Logger
     */
    private static final Logger LOG = Logger.getLogger(ServerGame.class.getName());

    private final Socket connectedClientSocket;

    public ServerGame(Socket connectedClientSocket) {
        this.connectedClientSocket = connectedClientSocket;
    }

    @Override
    public void run() {
        try {
            LOG.log(Level.INFO, "Creating network player, computer and game.");
            IDisplay display = new LogDisplay();
            IPlayer whitePlayer = new NetworkServerPlayer(connectedClientSocket);
            IPlayer blackPlayer = new SmartComputer();
            Game g = new Game(display, whitePlayer, blackPlayer);
            g.play();
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        try {
            this.connectedClientSocket.close();
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }
}
