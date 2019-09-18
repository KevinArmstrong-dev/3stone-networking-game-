/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg3stone;

/**
 *
 * @author 1733408
 */
public class Space {
    private final int row;
    private final int column;
    private boolean isOccupied;
    private PlayerType whoOccupied;
    private Stone stone;
    
    public Space(int row, int column)
    {
        this.row = row;
        this.column = column;
        this.isOccupied = false;
        this.whoOccupied = null;
        this.stone = null;
    }
    
    public int getRow()
    {
        return this.row;
    }
    
    public int getColumn()
    {
        return this.column;
    }
    
    public boolean getIsOccupied()
    {
        return this.isOccupied;
    }
    
    public Stone getStone()
    {
        Stone returnStone = null;
        if (this.isOccupied == true)
        {
            returnStone = this.stone;
        }
        return returnStone;
    }
    
    public void occupy(Stone stone)
    {
        this.isOccupied = true;
        this.stone = stone;
    }
    
    public void unoccupy()
    {
        this.isOccupied = false;
        this.whoOccupied = null;
    }
}
