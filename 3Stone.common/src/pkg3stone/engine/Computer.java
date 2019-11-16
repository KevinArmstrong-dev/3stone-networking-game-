package pkg3stone.engine;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import pkg3stone.console.LogDisplay;

/**
 * Computer Class
 *
 * @author Svitlana Myronova
 */
public class Computer extends AbstractPlayer {

    /**
     * Logger
     */
    private static final Logger LOG = Logger.getLogger(Computer.class.getName());

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

                return block;
            }
            return betterMove(board);

//            Move move = new Move(r.nextInt(board.numberOfRows), r.nextInt(board.numberOfColumns));
//            if (board.getPiece(move) == Piece.BLANK) {
//                return move;
//            }
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
     *
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
     * Block player from the left side
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
                LOG.log(Level.INFO, "Play here {0}", tempMove);
                if (board.isPlayLegal(Piece.BLACK, tempMove)) {
                    return tempMove;
                }
            }
        } else {
            LOG.log(Level.INFO, "No Spaces On the right");
        }
        return null;
    }

    /**
     * Block player from the right side
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
                LOG.log(Level.INFO, "Play here {0}", tempMove);
                if (board.isPlayLegal(Piece.BLACK, tempMove)) {
                    return tempMove;
                }
                //return tempMove; 
            }
        } else {
            LOG.log(Level.INFO, "No Spaces On the right");
        }
        return null;
    }

    /**
     * Block player from the top
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
                LOG.log(Level.INFO, "Play here {0}", tempMove);
                if (board.isPlayLegal(Piece.BLACK, tempMove)) {
                    return tempMove;
                }
            }
        } else {
            LOG.log(Level.INFO, "No Spaces To Block On the bottom");
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
        int pointLeft = countPointLeft(board);
        int pointRight = countPointRight(board);
        Move left = blockLeft(board);
        Move across = betterBlockMoveAcross(board);
        Move upDown = betterBlockMoveUpDown(board);
        Move right = blockRight(board);
        Move up = blockUp(board);

        if (upDown != null && board.isPlayLegal(Piece.BLACK, upDown)) {
            return across;
        } else if (across != null && board.isPlayLegal(Piece.BLACK, across)) {
            return across;
        } else if (left != null && board.isPlayLegal(Piece.BLACK, left) && pointLeft >= 2) {
            return left;
        } else if (right != null && board.isPlayLegal(Piece.BLACK, right) && pointRight >= 2) {
            return right;
        } else if (up != null && board.isPlayLegal(Piece.BLACK, up)) {
            return up;
        } else {
            return null;
        }
    }

    /**
     * Choose the better move
     *
     * @author Kevin Armstrong
     * @param board
     * @param move
     * @return Move
     */
    private Move betterMove(Board board) {
        return betterMoveUpDown(board);
    }

    /**
     * Find the better move in the row
     *
     * @author Kevin Armstrong
     * @param board
     * @param move
     * @return Move
     */
    private Move betterMoveAcross(Board board) {
        Move lastMove = board.getLastMove();
        Move tempMove = null;
        int pointX = 0;
        Piece[][] pieces = board.getPieces();
        for (int i = 0; i < 11; i++) {
            if (i + 1 < 11) {
                tempMove = new Move(lastMove.getRow(), i + 1);
                if (pieces[lastMove.getRow()][i] == Piece.BLACK && board.isPlayLegal(Piece.BLACK, tempMove)) {
                    pointX += 1;
                    if (pointX >= 2) {
                        return tempMove;
                    }
                }
            }
        }
        int pointYX = 0;
        for (int i = 10; i >= 0; i--) {
            if (i - 1 >= 0) {
                tempMove = new Move(lastMove.getRow(), i - 1);
                if (pieces[lastMove.getRow()][i] == Piece.BLACK && board.isPlayLegal(Piece.BLACK, tempMove)) {
                    pointYX += 1;
                    if (pointYX >= 2) {
                        return tempMove;
                    }
                }
            }
        }
        return goodMoveUpDown(board);

    }

    /**
     * Find better move in the column
     *
     * @param board
     * @param goodMove
     * @return
     */
    private Move betterMoveUpDown(Board board) {
        Move lastMove = board.getLastMove();
        Move tempMove = null;
        int pointX = 0;
        Piece[][] pieces = board.getPieces();
        for (int i = 0; i < 11; i++) {
            if (i + 1 < 11) {
                tempMove = new Move(i + 1, lastMove.getColumn());
                if (pieces[i][lastMove.getColumn()] == Piece.BLACK && board.isPlayLegal(Piece.BLACK, tempMove)) {
                    pointX += 1;
                    if (pointX >= 2) {
                        return tempMove;
                    }
                }
            }
        }
        int pointYX = 0;
        for (int i = 10; i >= 0; i--) {
            if (i - 1 >= 0) {
                tempMove = new Move(i - 1, lastMove.getColumn());
                if (pieces[i][lastMove.getColumn()] == Piece.BLACK && board.isPlayLegal(Piece.BLACK, tempMove)) {
                    pointYX += 1;
                    if (pointYX >= 2) {
                        return tempMove;
                    }
                }
            }
        }
        //if there is no good move on the x-axis try finding one the y-axis
        return betterMoveAcross(board);
    }

    /**
     * This helper method will helper in counting points on the right
     *
     * @param board
     * @return
     */
    private int countPointRight(Board board) {
        int point = 0;
        int row = board.getLastMove().getRow();
        Piece[][] piece = board.getPieces();
        for (int col = 0; col < 11; col++) {
            if (col + 1 < 11) {
                if (piece[row][col] == Piece.WHITE && piece[row][col + 1] == Piece.BLANK) {
                    point += 1;
                }
            }

        }
        LOG.log(Level.INFO, "number of available points on the Right {0}", point);
        return point;
    }

    /**
     * This helper method scans the board and return how many stones are close
     *
     * @param board
     * @return
     */
    private int countPointLeft(Board board) {
        int point = 0;
        int row = board.getLastMove().getRow();
        Piece[][] piece = board.getPieces();
        for (int col = 10; col >= 0; col--) {
            if (col - 1 >= 0) {
                if (piece[row][col] == Piece.WHITE && piece[row][col - 1] == Piece.BLANK) {
                    point += 1;
                }
            }

        }
        LOG.log(Level.INFO, "number of available points on the left {0}", point);
        return point;
    }

    /**
     * This method will do the computation for a move
     *
     * @param board
     * @return
     */
    private Move calculateMove(Board board) {

        Move lastMove = board.getLastMove();

        return bestPlaceToBlock(board);
    }

    /**
     * This method will find a good across
     *
     * @param board
     * @return
     */
    private Move goodMoveAcross(Board board) {
        Move lastMove = board.getLastMove();
        Move tempMove = null;
        int pointX = 0;
        Piece[][] pieces = board.getPieces();
        for (int i = 0; i < 11; i++) {
            if (i + 1 < 11) {
                tempMove = new Move(lastMove.getRow(), i + 1);
                if (pieces[lastMove.getRow()][i] == Piece.BLACK && board.isPlayLegal(Piece.BLACK, tempMove)) {
                    pointX += 1;
                    if (pointX == 1) {
                        return tempMove;
                    }
                }
            }
        }
        int pointYX = 0;
        for (int i = 10; i >= 0; i--) {
            if (i - 1 >= 0) {
                tempMove = new Move(lastMove.getRow(), i - 1);
                if (pieces[lastMove.getRow()][i] == Piece.BLACK && board.isPlayLegal(Piece.BLACK, tempMove)) {
                    pointYX += 1;
                    if (pointYX == 1) {
                        return tempMove;
                    }
                }
            }
        }

        //If there is no option play at a random allowed space
        while (true) {
            Move move = new Move(r.nextInt(board.numberOfRows), r.nextInt(board.numberOfColumns));
            if (board.getPiece(move) == Piece.BLANK) {
                return move;
            }
        }
    }

    /**
     * This helper method will play the stone next to one Black stone in case it
     * doesn't find two black stones in a row
     *
     * @param board
     * @return tempMove
     */
    private Move goodMoveUpDown(Board board) {
        Move lastMove = board.getLastMove();
        Move tempMove = null;
        int pointX = 0;
        Piece[][] pieces = board.getPieces();
        for (int i = 0; i < 11; i++) {
            if (i + 1 < 11) {
                tempMove = new Move(i, lastMove.getColumn());
                if (pieces[i][lastMove.getColumn()] == Piece.BLACK && board.isPlayLegal(Piece.BLACK, tempMove)) {
                    pointX += 1;
                    if (pointX == 1) {
                        return tempMove;
                    }
                }
            }
        }
        int pointYX = 0;
        for (int i = 10; i >= 0; i--) {
            if (i - 1 >= 0) {
                tempMove = new Move(i, lastMove.getColumn());
                if (pieces[i][lastMove.getColumn()] == Piece.BLACK && board.isPlayLegal(Piece.BLACK, tempMove)) {
                    pointYX += 1;
                    if (pointYX == 1) {
                        return tempMove;
                    }
                }
            }
        }
        return goodMoveAcross(board);
    }

    /**
     * This method will block the client player on the y-axis
     *
     * @param board
     * @return
     */
    private Move betterBlockMoveAcross(Board board) {
        Move lastMove = board.getLastMove();
        Move tempMove = null;
        int pointX = 0;
        Piece[][] pieces = board.getPieces();
        for (int i = 0; i < 11; i++) {
            if (i + 1 < 11) {
                tempMove = new Move(lastMove.getRow(), i + 1);
                if (pieces[lastMove.getRow()][i] == Piece.WHITE && board.isPlayLegal(Piece.BLACK, tempMove)) {
                    pointX += 1;
                    if (pointX == 2) {
                        return tempMove;
                    }
                } else if (i + 2 < 11) {
                    if (pieces[lastMove.getRow()][i] == Piece.WHITE && pieces[lastMove.getRow()][i + 2] == Piece.WHITE
                            && board.isPlayLegal(Piece.BLACK, tempMove)) {
                        return tempMove;
                    }
                }
            }
        }
        int pointYX = 0;
        for (int i = 10; i >= 0; i--) {
            if (i - 1 >= 0) {
                tempMove = new Move(lastMove.getRow(), i - 1);
                if (pieces[lastMove.getRow()][i] == Piece.WHITE && board.isPlayLegal(Piece.BLACK, tempMove)) {
                    pointYX += 1;
                    if (pointYX == 2) {
                        return tempMove;
                    }
                } else if (i - 2 >= 0) {
                    if (pieces[lastMove.getRow()][i] == Piece.WHITE && pieces[lastMove.getRow()][i - 2] == Piece.WHITE
                            && board.isPlayLegal(Piece.BLACK, tempMove)) {
                        return tempMove;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Find better move in the column
     *
     * @param board
     * @param goodMove
     * @return
     */
    private Move betterBlockMoveUpDown(Board board) {
        Move lastMove = board.getLastMove();
        Move tempMove = null;
        int pointX = 0;
        Piece[][] pieces = board.getPieces();
        for (int i = 0; i < 11; i++) {
            if (i + 1 < 11) {
                tempMove = new Move(i + 1, lastMove.getColumn());
                if (pieces[i][lastMove.getColumn()] == Piece.WHITE && board.isPlayLegal(Piece.BLACK, tempMove)) {
                    pointX += 1;
                    if (pointX >= 2) {
                        return tempMove;
                    }
                } else if (i + 2 < 11) {
                    if (pieces[i][lastMove.getColumn()] == Piece.WHITE && pieces[i + 2][lastMove.getColumn()] == Piece.WHITE
                            && board.isPlayLegal(Piece.BLACK, tempMove)) {
                        return tempMove;
                    }
                }
            }
        }
        int pointYX = 0;
        for (int i = 10; i >= 0; i--) {
            if (i - 1 >= 0) {
                tempMove = new Move(i - 1, lastMove.getColumn());
                if (pieces[i][lastMove.getColumn()] == Piece.WHITE && board.isPlayLegal(Piece.BLACK, tempMove)) {
                    pointYX += 1;
                    if (pointYX >= 2) {
                        return tempMove;
                    }
                } else if (i - 2 > 0) {
                    if (pieces[i][lastMove.getColumn()] == Piece.WHITE && pieces[i - 2][lastMove.getColumn()] == Piece.WHITE
                            && board.isPlayLegal(Piece.BLACK, tempMove)) {
                        return tempMove;
                    }
                }
            }
        }
        return null;
    }
}
