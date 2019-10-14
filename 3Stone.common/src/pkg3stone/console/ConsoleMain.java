/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg3stone.console;

import pkg3stone.engine.Computer;
import pkg3stone.engine.Game;
import pkg3stone.engine.IDisplay;
import pkg3stone.engine.IPlayer;

/**
 *
 * @author svitl
 */
public class ConsoleMain {
    
    
    public static void main(String[] args)
    {
        IDisplay display = new ConsoleDisplay();
        IPlayer whitePlayer = new ConsolePlayer();
        IPlayer blackPlayer = new Computer();
        //IPlayer blackPlayer = new ConsolePlayer();
        Game g = new Game(display, whitePlayer, blackPlayer);
        g.play();        
    }    
}
