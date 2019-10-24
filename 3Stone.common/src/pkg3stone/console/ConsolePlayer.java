package pkg3stone.console;

import java.util.Scanner;
import pkg3stone.engine.AbstractPlayer;
import pkg3stone.engine.Board;
import pkg3stone.engine.Move;
import pkg3stone.engine.MoveType;
import pkg3stone.engine.Result;

/**
 * ConsolePlayer Class
 * 
 * @author Svitlana Myronova
 */
//class for testing purpose only
public class ConsolePlayer extends AbstractPlayer {

    private final Scanner scan;

    /**
     * Constructor
     */
    public ConsolePlayer() {
        this.scan = new Scanner(System.in);
    }

    /**
     * Called by Game to ask player to prepare for new chooseMove call
     *
     * @param board
     */
    @Override
    public void prepareMove(Board board) {
        System.out.println("Last stone: " + board.getLastStonePlayed() + " move:" + board.getLastMove());
    }

    /**
     * Method called by Game to ask player about its next move
     *
     * @param board
     * @return Move
     */
    @Override
    public Move chooseMove(Board board) {
        System.out.println("Choose the move");
        String moveStr = scan.nextLine();
        String[] coordinatsStr = moveStr.split(" ");
        return new Move(Integer.parseInt(coordinatsStr[0]), Integer.parseInt(coordinatsStr[1]));
    }

    /**
     * Called by Game if Move returned by chooseMove is illegal for current game
     * state.
     *
     * @param moveType
     * @param move
     */
    @Override
    public void moveOutcome(MoveType moveType, Move move) {
        if (moveType == MoveType.ILLEGAL) {
            System.out.println("Your move is illegal.");
        } else if (moveType == MoveType.CONFIRMED) {
            System.out.println("Your move is confirmed.");
        }
    }

    /**
     * Called by Game to notify about game result
     * @param board
     * @param result
     */
    @Override
    public void gameOver(Board board, Result result) {
    }

    /**
     * Needs to be called to cleanup resources created by this player.
     */
    @Override
    public void close() {
        scan.close();
    }
}
