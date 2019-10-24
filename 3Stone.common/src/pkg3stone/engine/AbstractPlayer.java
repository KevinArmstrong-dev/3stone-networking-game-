package pkg3stone.engine;

/**
 * AbstractPlayer Class
 * 
 * @author Svitlana Myronova
 */
public abstract class AbstractPlayer implements IPlayer {
    private Piece currentColor;
    
    /**
     * Called once by Game before game begins to notify player about it's color
     *
     * @param piece
     * @throws java.lang.Exception
     */
    @Override
    public void startTheGame(Piece piece) throws Exception
    {
        this.currentColor = piece;
    }
    
    /**
     * Returns player color assigned by Game through startTheGame call.
     *
     * @return Piece
     */
    @Override
    public Piece getCurrentColor()
    {
        return currentColor;
    }
}
