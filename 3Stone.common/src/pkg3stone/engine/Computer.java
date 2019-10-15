/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg3stone.engine;

import java.util.Random;

/**
 *
 * @author svitl
 */
public class Computer extends AbstractPlayer {

    private final Random r;

    /**
     * Initializes computer logic
     */
    public Computer() {
        this.r = new Random();
    }

    @Override
    public void prepareMove(Board board) {
    }

    @Override     //simple version for testing
    public Move chooseMove(Board board) {

        while (true) {
            //Move move = new Move(r.nextInt(board.numberOfRows), r.nextInt(board.numberOfColumns));
            Move move = board.getIdealMove(board, Piece.BLACK);
            if (board.getPiece(move) == Piece.BLANK) {
                return move;
            }
        }
    }

    @Override
    public void moveOutcome(MoveType moveType, Move move) {
    }

    @Override
    public void gameOver(Board board, Result result) {
    }

    @Override
    public void close() {
    }
}
