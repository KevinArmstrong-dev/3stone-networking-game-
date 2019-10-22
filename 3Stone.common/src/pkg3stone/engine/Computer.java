package pkg3stone.engine;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Computer Class
 * 
 * @author Svitlana Myronova
 */
public class Computer extends AbstractPlayer {

    private final Random r;

    /**
     * Initializes computer logic
     */
    public Computer() {
        this.r = new Random();
    }

    /**
     * Called by Game to ask to prepare for new chooseMove call
     *
     * @param board
     */
    @Override
    public void prepareMove(Board board) {
    }

    /**
     * Method called by Game to ask about next move
     *
     * @param board
     * @return Move
     */
    @Override    
    public Move chooseMove(Board board) {
        Move block = bestPlaceToBlock(board);

        while (true) {
            if (block != null) {
                int temp =betterMove(board,block);
                Logger.getLogger(Game.class.getName()).log(Level.INFO, "Tried To Block {0}",temp);
                return block;
            }
            Move move = new Move(r.nextInt(board.numberOfRows), r.nextInt(board.numberOfColumns));
            if (board.getPiece(move) == Piece.BLANK) {
                int temp =betterMove(board,block);
                Logger.getLogger(Game.class.getName()).log(Level.INFO, "Tried To random {0}",temp);
                return move;
            }
        }
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
    }

    /**
     * Blocks player from the left side
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

    /**
     * Blocks player from the right side
     * 
     * @author Kevin Armstrong
     * @param board
     * @return tempMove
     */
    private Move blockRight(Board board) {
        Move lastMove = board.getLastMove();

        if (lastMove.getColumn() - 1 > 0) {
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
     * Blocks player from the top
     * 
     * @author Kevin Armstrong
     * @param board
     * @return tempMove
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
     * This method will look for a best place to block and place the stone it
     * does not take into account the points but only the last position played
     * at
     *
     * @author Kevin Armstrong
     * @param board
     * @return Move
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

    /**
     * Chooses the better move
     *
     * @author Kevin Armstrong
     * @param board
     * @param move
     * @return int
     */
    private int betterMove(Board board, Move move){
        int count1  = betterMoveAcross(board, move);
        int count2 = betterMoveUpDown(board, move);
        return count1>count2?count1:count2;
    }
    private int betterMoveAcross(Board board, Move goodMove) {
        Move lastMove = board.getLastMove();
        int countBlack = 0;

        for (int col = 0; col < 11; col++) {

            switch (board.getPieces()[lastMove.getRow()][col]) {
                case BLACK:
                    countBlack += 1;
                    break;
                case WHITE:
                    countBlack = 0;
                    break;
                case BLANK:
                    if (countBlack == 2) {
                       Logger.getLogger(Game.class.getName()).log(Level.INFO, "Found a great spot to play at {0} {1}", 
                               new Object[]{lastMove.getRow(), lastMove.getRow()});
                        return 1;
                    }
                    break;
                default:
                    return 0;
            }
        }
        return -404;
    }
    
     private int betterMoveUpDown(Board board, Move goodMove) {
        Move lastMove = board.getLastMove();
        int countBlack = 0;

        for (int row = 0; row < 11; row++) {

            switch (board.getPieces()[row][lastMove.getRow()]) {
                case BLACK:
                    countBlack += 1;
                    break;
                case WHITE:
                    countBlack = 0;
                    break;
                case BLANK:
                    if (countBlack == 2) {
                       Logger.getLogger(Game.class.getName()).log(Level.INFO, "Found a great spot to play at {0} {1}", 
                               new Object[]{lastMove.getRow(), lastMove.getRow()});
                        return 1;
                    }
                    break;
                case BARRED:
                    break;
                default:
                    return 0;
            }
        }
        return -404;
    }
    
    
}
