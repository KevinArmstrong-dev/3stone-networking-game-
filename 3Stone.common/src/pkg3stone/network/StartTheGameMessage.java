/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg3stone.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import pkg3stone.engine.Piece;

/**
 *
 * @author Svitlana Myronova
 */
public class StartTheGameMessage {

    private final Piece currentColor;
    
    public StartTheGameMessage(Piece currentColor)
    {
        this.currentColor = currentColor;
    }

    public Piece getCurrentColor()
    {
        return this.currentColor;
    }
    
    public void write(OutputStream os) throws IOException
    {
        os.write(this.currentColor.getValue());
    }
    
    static StartTheGameMessage read(InputStream is) throws IOException
    {
        Piece currentColor = Piece.fromValue(is.read());
        return new StartTheGameMessage(currentColor);
    }
}
