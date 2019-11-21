package pkg3stone.console;

import java.util.logging.Level;
import java.util.logging.Logger;
import pkg3stone.engine.Board;
import pkg3stone.engine.IDisplay;
import pkg3stone.engine.Result;

/**
 * ConsoleDisplay Class
 *
 * @author Svitlana Myronova
 */
public class LogDisplay implements IDisplay {

    /**
     * Logger
     */
    private static final Logger LOG = Logger.getLogger(LogDisplay.class.getName());

    /**
     * Method shows the board
     *
     * @param board
     */
    @Override
    public void ShowBoard(Board board) {
        LOG.log(Level.INFO, board.toString());
    }

    /**
     * Method shows the result of the game
     *
     * @param result
     */
    @Override
    public void ShowResult(Result result) {
        LOG.log(Level.INFO, "Black - {0}. White - {1}.", new Object[]{result.getBlackScore(), result.getWhiteScore()});
    }

}
