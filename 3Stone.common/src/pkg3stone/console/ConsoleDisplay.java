/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg3stone.console;

import pkg3stone.engine.Board;
import pkg3stone.engine.IDisplay;
import pkg3stone.engine.Result;

/**
 *
 * @author svitl
 */
public class ConsoleDisplay implements IDisplay{

    @Override
    public void ShowBoard(Board board) {
        System.out.println(board.toString());
    }

    @Override
    public void ShowResult(Result result) {
        System.out.println("Black - " + result.getBlackScore() + ". White - " + result.getWhiteScore() + ".");
    }
    
}
