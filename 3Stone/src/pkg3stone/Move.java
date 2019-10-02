/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg3stone;

/**
 *
 * @author svitl
 */
public class Move {
    private final int row;
    private final int column;

    public Move(int row, int column)
    {
        this.row = row;
        this.column = column;
    }
    
    public int getRow()
    {
        return this.row;
    }
        
    public int getColumn()
    {
        return this.column;
    }
    
    boolean isValidForBoard(Board b)
    {
        if(column < 0 || column >= b.numberOfColumns || row < 0 || row >= b.numberOfRows)
            return false;
        return true;
    }
}
