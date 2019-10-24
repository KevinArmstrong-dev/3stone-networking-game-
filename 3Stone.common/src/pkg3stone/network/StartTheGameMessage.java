package pkg3stone.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import pkg3stone.engine.Piece;

/**
 * StartTheGameMessage Class
 *
 * @author Svitlana Myronova
 */
public class StartTheGameMessage {

    private final Piece currentColor;

    /**
     * Constructor
     * 
     * @param currentColor
     */
    public StartTheGameMessage(Piece currentColor) {
        this.currentColor = currentColor;
    }

    /**
     * CurrentColor getter
     *
     * @return Piece
     */
    public Piece getCurrentColor() {
        return this.currentColor;
    }

    /**
     * Write OutputStream
     *
     * @param os
     * @throws IOException
     */
    public void write(OutputStream os) throws IOException {
        os.write(this.currentColor.getValue());
    }

    /**
     * Read InputStream
     *
     * @param is
     * @return StartTheGameMessage
     * @throws IOException
     */
    public static StartTheGameMessage read(InputStream is) throws IOException {
        Piece currentColor = Piece.fromValue(is.read());
        return new StartTheGameMessage(currentColor);
    }
}
