package pkg3stone.engine;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Board Class
 *
 * @author Svitlana Myronova
 */
public class Board {

    private Piece[][] pieces;

    private int playerWhitePieces = 15;
    private int playerBlackPieces = 15;

    private final int amountOfStones = 3;
    public final int numberOfRows = 11;
    public final int numberOfColumns = 11;

    private ArrayList<Move> playedMoves = new ArrayList<>();

    /**
     * Constructor
     */
    public Board() {
        this.pieces = new Piece[numberOfRows][numberOfColumns];
        for (Piece[] row : this.pieces) {
            Arrays.fill(row, Piece.BLANK);
        }

        //place in the center of the board is barred
        this.pieces[numberOfRows / 2][numberOfColumns / 2] = Piece.BARRED;
    }

    /**
     * Get Wite stone
     *
     * @return
     */
    public int getPlayerWhitePieces(){
        return this.playerWhitePieces;
    }
            
    /**
     * Get Black stone
     *
     * @return
     */
    public int getPlayerBlackPieces(){
        return this.playerBlackPieces;
    }
    
    /**
     * Returns last move
     *
     * @return Move
     */
    public Move getLastMove() {
        if (this.playedMoves.isEmpty()) {
            return null;
        }
        return this.playedMoves.get(this.playedMoves.size() - 1);
    }

    /**
     * Method takes Move and returns Piece
     *
     * @param m Move
     * @return Piece
     */
    public Piece getPiece(Move m) {
        return pieces[m.getRow()][m.getColumn()];
    }

    /**
     * Method checks if it is legal to make a move into position(row, column) of
     * the board,
     *
     * @param player
     * @param move
     * @return true or false
     */
    public boolean isPlayLegal(Piece player, Move move) {
        if (!move.isValidForBoard(this)) {
            return false;
        }

        if (player != Piece.WHITE && player != Piece.BLACK) {
            return false;
        }

        if (getPiece(move) != Piece.BLANK) {
            return false;
        }

        Move lastMove = getLastMove();
        if (lastMove == null) {
            return true;
        }

        if (getPiece(lastMove) == player) {
            return false;
        }

        if (move.getRow() == lastMove.getRow() || move.getColumn() == lastMove.getColumn()) {
            return true;
        }

        if (hasFreePlaceInColumn(lastMove.getColumn()) || hasFreePlaceInRow(lastMove.getRow())) {
            return false;
        }
        return true;
    }

    /**
     * Method sets the last stone played
     *
     * @param player
     * @param move
     */
    public void placeStone(Piece player, Move move) {
        pieces[move.getRow()][move.getColumn()] = player;

        if (player == Piece.WHITE) {
            playerWhitePieces--;
        } else if (player == Piece.BLACK) {
            playerBlackPieces--;
        }

        this.playedMoves.add(move);
    }

    void undoLastMove() {
        if (this.playedMoves.isEmpty()) {
            return;
        }

        Move lastMove = getLastMove();
        Piece lastPiecePlayed = getPiece(lastMove);

        if (lastPiecePlayed == Piece.WHITE) {
            playerWhitePieces++;
        } else if (lastPiecePlayed == Piece.BLACK) {
            playerBlackPieces++;
        }

        this.pieces[lastMove.getRow()][lastMove.getColumn()] = Piece.BLANK;
        this.playedMoves.remove(this.playedMoves.size() - 1);
    }

    /**
     * Method checks if there is free space in the column
     *
     * @param column
     * @return true or false
     */
    private boolean hasFreePlaceInColumn(int column) {
        for (int i = 0; i < numberOfRows; i++) {
            if (pieces[i][column] == Piece.BLANK) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method checks if there is free space in the row
     *
     * @param row
     * @return true or false
     */
    private boolean hasFreePlaceInRow(int row) {
        for (int j = 0; j < numberOfColumns; j++) {
            if (pieces[row][j] == Piece.BLANK) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method finds three stones in right diagonals and counts the result in
     * right diagonals
     *
     * @param result
     */
    private void findThreeStonesInRightDiagonals(Result result) {
        for (int i = 0; i <= numberOfRows - amountOfStones; i++) {
            for (int j = amountOfStones - 1; j < numberOfColumns; j++) {
                Piece firstPiece = pieces[i][j];
                if (firstPiece != Piece.WHITE && firstPiece != Piece.BLACK) {
                    continue;
                }
                int count = 0;
                for (int k = 0; k < amountOfStones; k++) {
                    if (pieces[i + k][j - k] != firstPiece) {
                        break;
                    }
                    count = count + 1;
                }
                if (count == amountOfStones) {
                    if (firstPiece == Piece.BLACK) {
                        result.addBlackScore(1);
                    } else {
                        result.addWhiteScore(1);
                    }
                }
            }
        }
    }

    /**
     * Method finds three stones in left diagonals and counts the result in left
     * diagonals
     *
     * @param result
     */
    private void findThreeStonesInLeftDiagonals(Result result) {
        for (int i = 0; i <= numberOfRows - amountOfStones; i++) {
            for (int j = 0; j <= numberOfColumns - amountOfStones; j++) {
                Piece firstPiece = pieces[i][j];
                if (firstPiece != Piece.WHITE && firstPiece != Piece.BLACK) {
                    continue;
                }
                int count = 0;
                for (int k = 0; k < amountOfStones; k++) {
                    if (pieces[i + k][j + k] != firstPiece) {
                        break;
                    }
                    count = count + 1;
                }
                if (count == amountOfStones) {
                    if (firstPiece == Piece.BLACK) {
                        result.addBlackScore(1);
                    } else {
                        result.addWhiteScore(1);
                    }
                }
            }
        }
    }

    /**
     * Method finds three stones in columns and counts the result in columns
     *
     * @param result
     */
    private void findThreeStonesInColumns(Result result) {
        for (int i = 0; i <= numberOfRows - amountOfStones; i++) {
            for (int j = 0; j < numberOfColumns; j++) {
                Piece firstPiece = pieces[i][j];
                if (firstPiece != Piece.WHITE && firstPiece != Piece.BLACK) {
                    continue;
                }
                int count = 0;
                for (int k = 0; k < amountOfStones; k++) {
                    if (pieces[i + k][j] != firstPiece) {
                        break;
                    }
                    count = count + 1;
                }
                if (count == amountOfStones) {
                    if (firstPiece == Piece.BLACK) {
                        result.addBlackScore(1);
                    } else {
                        result.addWhiteScore(1);
                    }
                }
            }
        }
    }

    /**
     * Method finds three stones in rows and counts the result in rows
     *
     * @param result
     */
    private void findThreeStonesInRows(Result result) {
        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j <= numberOfColumns - amountOfStones; j++) {
                Piece firstPiece = pieces[i][j];
                if (firstPiece != Piece.WHITE && firstPiece != Piece.BLACK) {
                    continue;
                }
                int count = 0;
                for (int k = 0; k < amountOfStones; k++) {
                    if (pieces[i][j + k] != firstPiece) {
                        break;
                    }
                    count = count + 1;
                }
                if (count == amountOfStones) {
                    if (firstPiece == Piece.BLACK) {
                        result.addBlackScore(1);
                    } else {
                        result.addWhiteScore(1);
                    }
                }
            }
        }
    }

    /**
     * Getter for Piece[][]
     *
     * @return Piece[][]
     */
    public Piece[][] getPieces() {
        return pieces;
    }

    /**
     * Returns result of the game
     *
     * @return Result
     */
    public Result resultOfGame() {
        Result result = new Result();
        findThreeStonesInRows(result);
        findThreeStonesInColumns(result);
        findThreeStonesInLeftDiagonals(result);
        findThreeStonesInRightDiagonals(result);
        return result;
    }

    /**
     * Checks if the Game is over
     *
     * @return true or false
     */
    public boolean isGameOver() {
        if (playerWhitePieces == 0 && playerBlackPieces == 0) {
            return true;
        } else {
            return false;
        }
    }
    
    public void resetBoard(){
      this.pieces = new Piece[numberOfRows][numberOfColumns];
        for (Piece[] row : this.pieces) {
            Arrays.fill(row, Piece.BLANK);
        }

        //place in the center of the board is barred
        this.pieces[numberOfRows / 2][numberOfColumns / 2] = Piece.BARRED;
    }

    /**
     * Override toString method
     *
     * @return String
     */
    @Override
    public String toString() {
        String board = "";
        for (Piece[] piece : this.pieces) {
            for (Piece piece1 : piece) {
                switch (piece1) {
                    case BLACK:
                        board += "B";
                        break;
                    case WHITE:
                        board += "W";
                        break;
                    case BLANK:
                        board += "O";
                        break;
                    case BARRED:
                        board += " ";
                        break;
                }
                board += " ";
            }
            board += "\n";
        }
        return board;
    }
}
