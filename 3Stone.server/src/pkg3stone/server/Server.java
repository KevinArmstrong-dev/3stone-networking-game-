package pkg3stone.server;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import pkg3stone.engine.IDisplay;
import pkg3stone.engine.IPlayer;
import pkg3stone.console.ConsoleDisplay;
import pkg3stone.engine.Game;
import pkg3stone.engine.Computer;
import pkg3stone.engine.SmartComputer;
import pkg3stone.network.NetworkServerBroadcaster;
import pkg3stone.network.NetworkServerPlayer;

/**
 * Server Class
 *
 * @author Svitlana Myronova
 */
public class Server {

    /**
     * Main method of the Server Class
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int serverPort = NetworkServerPlayer.DEFAULT_PORT;
        try {
            serverPort = Integer.parseInt(args[0]);
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException ex) {
            System.out.println("Server port number is not provided. Will use port " + serverPort);
        }

        int boardcasterPort = NetworkServerBroadcaster.DEFAULT_PORT;
        try {
            boardcasterPort = Integer.parseInt(args[1]);
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException ex) {
            System.out.println("Broadcaster port number is not provided. Will use port " + boardcasterPort);
        }

        try {
            NetworkServerBroadcaster broadcaster = new NetworkServerBroadcaster(boardcasterPort, serverPort);
            broadcaster.start();

            IDisplay display = new ConsoleDisplay();
            IPlayer whitePlayer = new NetworkServerPlayer(serverPort);
            IPlayer blackPlayer = new SmartComputer();
            Game g = new Game(display, whitePlayer, blackPlayer);
            while (true) {
                g.play();
                g.Restart();
            }
        } catch (Exception ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
