package pkg3stone.engine;

/**
 * Piece Enum
 *
 * @author Svitlana Myronova
 */
public enum Piece {
    BLANK(0), 
    WHITE(1), 
    BLACK(2),
    BARRED(3);
    
    private final int value;
    
    /**
     * Constructor
     * 
     * @param value
     */
    private Piece(int value)
    {
        this.value = value;
    }
    
    /**
     * Value getter
     *
     * @return
     */
    public int getValue()
    {
        return this.value;
    }
    
    /**
     * Get Piece from the value
     *
     * @param value
     * @return Piece
     */
    static public Piece fromValue(int value)
    {
        for(Piece t : Piece.values())
        {
            if(t.getValue() == value)
                return t;
        }
        return BLANK;
    }

    public Piece getOpposite()
    {
        if(this == WHITE)
            return BLACK;
        if(this == BLACK)
            return WHITE;
        throw new IllegalStateException("Piece state does not have opposite.");
    }
}
