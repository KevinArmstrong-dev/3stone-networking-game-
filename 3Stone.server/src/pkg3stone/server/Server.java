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
        try {
            if(args.length < 1)
            {
                showHelp();
                return;
            }
            
            int port = Integer.parseInt(args[0]);
            
            IDisplay display = new ConsoleDisplay();
            IPlayer whitePlayer = new NetworkServerPlayer(port);
            IPlayer blackPlayer = new Computer();
            //IPlayer blackPlayer = new ConsolePlayer();
            Game g = new Game(display, whitePlayer, blackPlayer);
            g.play();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private static void showHelp()
    {
        System.out.println("Please provide port number.");
    }
    
    private static void playGame(int port)
    {
    }
}
