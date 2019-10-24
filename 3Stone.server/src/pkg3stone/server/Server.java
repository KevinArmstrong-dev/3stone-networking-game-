package pkg3stone.server;

import java.util.logging.Level;
import java.util.logging.Logger;
import pkg3stone.engine.IDisplay;
import pkg3stone.engine.IPlayer;
import pkg3stone.console.ConsoleDisplay;
import pkg3stone.engine.Game;
import pkg3stone.engine.Computer;
import pkg3stone.engine.SmartComputer;
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
        int port = 50000;

        try {
            port = Integer.parseInt(args[0]);
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException ex) {
            System.out.println("Port number is not provided. Will use port " + port);
        }

        try {
            IDisplay display = new ConsoleDisplay();
            IPlayer whitePlayer = new NetworkServerPlayer(port);
            IPlayer blackPlayer = new SmartComputer();
            Game g = new Game(display, whitePlayer, blackPlayer);
            g.play();
        } catch (Exception ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
