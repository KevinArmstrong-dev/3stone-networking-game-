/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg3stone.engine;

/**
 *
 * @author svitl
 */
public enum Piece {
    BLANK(0), 
    WHITE(1), 
    BLACK(2),
    BARRED(3);
    
    private final int value;
    
    private Piece(int value)
    {
        this.value = value;
    }
    
    public int getValue()
    {
        return this.value;
    }
    
    static public Piece fromValue(int value)
    {
        for(Piece t : Piece.values())
        {
            if(t.getValue() == value)
                return t;
        }
        return BLANK;
    }

}
