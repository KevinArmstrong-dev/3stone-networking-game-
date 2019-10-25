package pkg3stone.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import pkg3stone.engine.Board;
import pkg3stone.engine.Move;
import pkg3stone.engine.MoveType;
import pkg3stone.engine.Piece;
import pkg3stone.engine.Result;

/**
 * MoveMessage Class
 *
 * @author Svitlana Myronova
 */
public class MoveMessageWithResult {

    private final MoveType moveType;
    private final Piece piece;
    private final Move move;
    private final Result result;
    private final int blackStones;
    private final int whiteStones;

    /**
     * Creates MoveMessage object;
     *
     * @param moveType
     * @param move
     */
    public MoveMessageWithResult(MoveType moveType, Piece piece, Move move, Result result, int blackStones, int whiteStones) {
        this.moveType = moveType;
        this.piece = piece;
        this.move = move;
        this.result = result;
        this.blackStones = blackStones;
        this.whiteStones = whiteStones;
    }

    /**
     * Returns stored moveType information
     *
     * @return MoveType
     */
    public MoveType getMoveType() {
        return this.moveType;
    }

    /**
     * Get move
     * 
     * @return move
     */
    public Move getMove() {
        return this.move;
    }
    
    /**
     * Returns stored piece
     *
     * @return Piece
     */
    public Piece getPiece() {
        return this.piece;
    }

    /**
     * Returns result;
     *
     * @return Result
     */
    public Result getResult() {
        return this.result;
    }
    
    /**
     * Get blackStones
     *
     * @return
     */
    public int getBlackStones(){
        return this.blackStones;
    }
    
    /**
     * Get whiteStones
     *
     * @return
     */
    public int getWhiteStones(){
        return this.whiteStones;
    }
    
    /**
     * Writes current message into output stream
     *
     * @param os
     * @throws IOException
     */
    public void write(OutputStream os) throws IOException {
        os.write(this.moveType.getValue());
        os.write(this.piece.getValue());
        os.write(this.move.getRow());
        os.write(this.move.getColumn());
        os.write(this.result.getBlackScore());
        os.write(this.result.getWhiteScore());
        os.write(this.blackStones);
        os.write(this.whiteStones);
    } 

    /**
     * Reads MoveMessage object from input stream
     *
     * @param is
     * @return MoveMessage
     * @throws IOException
     */
    static MoveMessageWithResult read(InputStream is) throws IOException {
        MoveType moveType = MoveType.fromValue(is.read());
        Piece piece = Piece.fromValue(is.read());
        int row = is.read();
        int column = is.read();
        int blackScore = is.read();
        int whiteScore = is.read();
        int blackStones = is.read();
        int whiteStones = is.read();        
        return new MoveMessageWithResult(moveType, piece, new Move(row, column), new Result(whiteScore, blackScore), blackStones, whiteStones);
    }
}

