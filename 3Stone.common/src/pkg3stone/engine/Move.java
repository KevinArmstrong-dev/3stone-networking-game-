package pkg3stone.engine;

/**
 * Move Class
 *
 * @author Svitlana Myronova
 */
public class Move {
    private final int row;
    private final int column;

    /**
     * Constructor
     *
     * @param row
     * @param column
     */
    public Move(int row, int column)
    {
        this.row = row;
        this.column = column;
    }
    
    /**
     *  Row getter 
     * 
     * @return int
     */
    public int getRow()
    {
        return this.row;
    }
        
    /**
     * Column getter
     *
     * @return int
     */
    public int getColumn()
    {
        return this.column;
    }
    
    /**
     * Check if the column and row are valid for the board
     * 
     * @param b
     * @return true or false
     */
    public boolean isValidForBoard(Board b)
    {
        if(column < 0 || column >= b.numberOfColumns || row < 0 || row >= b.numberOfRows)
            return false;
        return true;
    }    
    
    /**
     * Override toString method
     * @return String
     */
    @Override
    public String toString()
    {
        return "[" + this.row + ":" + this.column + "]";
    }
}
