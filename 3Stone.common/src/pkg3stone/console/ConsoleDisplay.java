package pkg3stone.console;

import pkg3stone.engine.Board;
import pkg3stone.engine.IDisplay;
import pkg3stone.engine.Result;

/**
 * ConsoleDisplay Class
 * 
 * @author Svitlana Myronova
 */
public class ConsoleDisplay implements IDisplay{

    /**
    * Method shows the board
    * 
    *  @param board
    */
    @Override
    public void ShowBoard(Board board) {
        System.out.println(board.toString());
    }

    /**
    * Method shows the result of the game
    * 
    *  @param result
    */
    @Override
    public void ShowResult(Result result) {
        System.out.println("Black - " + result.getBlackScore() + ". White - " + result.getWhiteScore() + ".");
    }
    
}
