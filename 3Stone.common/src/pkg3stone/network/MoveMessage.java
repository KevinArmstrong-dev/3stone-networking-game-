/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg3stone.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import pkg3stone.engine.Move;
import pkg3stone.engine.MoveType;

/**
 *
 * @author Svitlana Myronova
 */
public class MoveMessage {

    private final MoveType moveType;
    private final Move move;

    /**
     * Creates MoveMessage object;
     *
     * @param moveType
     * @param move
     */
    public MoveMessage(MoveType moveType, Move move) {
        this.moveType = moveType;
        this.move = move;
    }

    /**
     * Returns stored moveType information
     *
     * @return
     */
    public MoveType getMoveType() {
        return this.moveType;
    }

    /**
     * Returns stored move;
     *
     * @return
     */
    public Move getMove() {
        return this.move;
    }

    /**
     * Writes current message into output stream
     *
     * @param os
     * @throws IOException
     */
    public void write(OutputStream os) throws IOException {
        os.write(this.moveType.getValue());
        os.write(this.move.getRow());
        os.write(this.move.getColumn());
    }

    /**
     * Reads MoveMessage object from input stream
     *
     * @param is
     * @throws IOException
     */
    static MoveMessage read(InputStream is) throws IOException {
        MoveType moveType = MoveType.fromValue(is.read());
        int row = is.read();
        int column = is.read();
        return new MoveMessage(moveType, new Move(row, column));
    }
}
