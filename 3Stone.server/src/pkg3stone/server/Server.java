/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg3stone.server;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import pkg3stone.engine.IDisplay;
import pkg3stone.engine.IPlayer;
import pkg3stone.console.ConsoleDisplay;
import pkg3stone.engine.Game;
import pkg3stone.engine.Computer;
import pkg3stone.network.NetworkServerPlayer;

/**
 *
 * @author Svitlana Myronova
 */
public class Server {

    /**
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
            IPlayer blackPlayer = new Computer();
            Game g = new Game(display, whitePlayer, blackPlayer);
            g.play();
        } catch (Exception ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
