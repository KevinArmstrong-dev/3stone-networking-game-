package pkg3stone.engine;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Computer Class
 *
 * @author Svitlana Myronova
 */
public class SmartComputer extends AbstractPlayer {

    private final Random r;
    private final int maxLevel = 5;

    /**
     * Initializes computer logic
     */
    public SmartComputer() {
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
        MoveAndResult bestMoveAndResult = findBestMove(board, maxLevel);
        if (bestMoveAndResult != null) {
            return bestMoveAndResult.move;
        }
        return findRandomMove(board);
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
     * This Helper method will calculate the best move to make
     * give the board and the difficulty level of the game
     * 
     * @param board
     * @param level
     * @return 
     */
    private MoveAndResult findBestMove(final Board board, final int level) {
        if (board.isGameOver() || level == 0) {
            return new MoveAndResult(null, board.resultOfGame());
        }

        Move lastMove = board.getLastMove();
        final Piece currentPiece = board.getPiece(lastMove).getOpposite();

        MoveAndResult bestMoveAndResult = null;

        for (int col = 0; col < board.numberOfColumns; col++) {

            Move newMove = new Move(lastMove.getRow(), col);
            if (!board.isPlayLegal(currentPiece, newMove)) {
                continue;
            }

            if (level == maxLevel) {
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Evaluating move:" + newMove);
                if (bestMoveAndResult != null) {
                    Logger.getLogger(this.getClass().getName()).log(Level.INFO, " Current best:" + bestMoveAndResult.move + " " + bestMoveAndResult.result);
                }
            }

            board.placeStone(currentPiece, newMove);

            MoveAndResult newMoveAndResult = findBestMove(board, level - 1);
            if (newMoveAndResult != null) {
                if (level == maxLevel) {
                    Logger.getLogger(this.getClass().getName()).log(Level.INFO, " newMoveAndResult:" + newMoveAndResult.move + " " + newMoveAndResult.result);
                }
                if (bestMoveAndResult == null
                        || bestMoveAndResult.result.getScoreForPiece(getCurrentColor()) < newMoveAndResult.result.getScoreForPiece(getCurrentColor())) {
                    bestMoveAndResult = new MoveAndResult(newMove, newMoveAndResult.result);
                }
            }

            board.undoLastMove();
        }

        for (int row = 0; row < board.numberOfRows; row++) {
            Move newMove = new Move(row, lastMove.getColumn());
            if (!board.isPlayLegal(currentPiece, newMove)) {
                continue;
            }

            if (level == maxLevel) {
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Evaluating move:" + newMove);
                if (bestMoveAndResult != null) {
                    Logger.getLogger(this.getClass().getName()).log(Level.INFO, " Current best:" + bestMoveAndResult.move + " " + bestMoveAndResult.result);
                }
            }

            board.placeStone(currentPiece, newMove);

            MoveAndResult newMoveAndResult = findBestMove(board, level - 1);
            if (newMoveAndResult != null) {
                if (level == maxLevel) {
                    Logger.getLogger(this.getClass().getName()).log(Level.INFO, " newMoveAndResult:" + newMoveAndResult.move + " " + newMoveAndResult.result);
                }
                if (bestMoveAndResult == null
                        || bestMoveAndResult.result.getScoreForPiece(getCurrentColor()) < newMoveAndResult.result.getScoreForPiece(getCurrentColor())) {
                    bestMoveAndResult = new MoveAndResult(newMove, newMoveAndResult.result);
                }
            }

            board.undoLastMove();
        }

        return bestMoveAndResult;
    }

    /**
     * This method will play a random move in case there
     * is no appropriate move to play. Meaning if there is no good
     * blocking move or winning move, the move that is made by the server will be
     * random
     * 
     * @param board
     * @return 
     */
    private Move findRandomMove(Board board) {
        ArrayList<Move> possibleMoves = new ArrayList<>();
        for (int col = 0; col < board.numberOfColumns; col++) {
            Move newMove = new Move(board.getLastMove().getRow(), col);
            if (board.isPlayLegal(getCurrentColor(), newMove)) {
                possibleMoves.add(newMove);
            }
        }
        for (int row = 0; row < board.numberOfRows; row++) {
            Move newMove = new Move(row, board.getLastMove().getColumn());
            if (board.isPlayLegal(getCurrentColor(), newMove)) {
                possibleMoves.add(newMove);
            }
        }
        if (possibleMoves.isEmpty()) {
            return null;
        }
        int index = this.r.nextInt(possibleMoves.size());
        return possibleMoves.get(index);
    }
}
