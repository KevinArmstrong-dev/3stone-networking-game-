/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg3stone.engine;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        Move block = bestPlaceToBlock(board);
        while (true) {
            if (block != null) {
                Logger.getLogger(Game.class.getName()).log(Level.INFO, "Tried To Block");
                return block;
            }
            Move move = new Move(r.nextInt(board.numberOfRows), r.nextInt(board.numberOfColumns));
            //Move move = board.getIdealMove(board, Piece.BLACK);
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

    /**
     *
     * @author Kevin Armstrong
     * @param board
     * @return tempMove
     */
    private Move blockLeft(Board board) {
        Move lastMove = board.getLastMove();

        if (lastMove.getColumn() + 1 < board.numberOfColumns) {
            Move tempMove = new Move(lastMove.getColumn() + 1, lastMove.getRow());
            if (board.getPiece(tempMove) == Piece.BLANK) {
                System.out.println("Play here " + tempMove);
                Logger.getLogger(Game.class.getName()).log(Level.INFO, "Play here {0}", tempMove);
                if (board.isPlayLegal(Piece.BLACK, tempMove)) {
                    return tempMove;
                }
                //return tempMove; 
            }
        } else {
            System.out.println("No where to play");
            Logger.getLogger(Game.class.getName()).log(Level.INFO, "No Spaces On the right");
        }
        return null;
    }

    private Move blockRight(Board board) {
        Move lastMove = board.getLastMove();

        if (lastMove.getColumn()-1  > 0) {
            Move tempMove = new Move(lastMove.getColumn() - 1, lastMove.getRow());
            if (board.getPiece(tempMove) == Piece.BLANK) {
                System.out.println("Play here " + tempMove);
                Logger.getLogger(Game.class.getName()).log(Level.INFO, "Play here {0}", tempMove);
                if (board.isPlayLegal(Piece.BLACK, tempMove)) {
                    return tempMove;
                }
                //return tempMove; 
            }
        } else {
            System.out.println("No where to play");
            Logger.getLogger(Game.class.getName()).log(Level.INFO, "No Spaces On the right");
        }
        return null;
    }

    /**
     * 
     * @param board
     * @return 
     */
    private Move blockUp(Board board) {
        Move lastMove = board.getLastMove();

        if (lastMove.getRow() + 1 < board.numberOfRows) {
            Move tempMove = new Move(lastMove.getColumn(), lastMove.getRow() + 1);
            if (board.getPiece(tempMove) == Piece.BLANK) {
                System.out.println("Play here " + tempMove);
                Logger.getLogger(Game.class.getName()).log(Level.INFO, "Play here {0}", tempMove);
                if (board.isPlayLegal(Piece.BLACK, tempMove)) {
                    return tempMove;
                }
            }
        } else {
            System.out.println("No where to play");
            Logger.getLogger(Game.class.getName()).log(Level.INFO, "No Spaces To Block On the bottom");
        }
        return null;
    }

    /**
     * This method will look for a best place to block and place the stone
     * it does not take into account the points but only the last position played at
     * 
     * @author Kevin Armstrong
     * @param board
     * @return 
     */
    private Move bestPlaceToBlock(Board board) {

        Move left = blockLeft(board);
        Move right = blockRight(board);
        Move up = blockUp(board);

        if (left != null && board.isPlayLegal(Piece.BLACK, left)) {
            return left;
        } else if (right != null && board.isPlayLegal(Piece.BLACK, right)) {
            return right;
        } else if (up != null && board.isPlayLegal(Piece.BLACK, up)) {
            return up;
        } else {
            return null;
        }
    }
}
