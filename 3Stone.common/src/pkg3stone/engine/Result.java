package pkg3stone.engine;

/**
 * Result Class
 *
 * @author Svitlana Myronova
 */
public class Result {
    
    private int blackScore = 0;
    private int whiteScore = 0;

    /**
     * Default constructor. Initialized Result to 0,0
     */
    public Result()
    {
        this(0, 0);
    }
    
    /**
     * Constructor
     *
     * @param whiteScore
     * @param blackScore
     */
    public Result(int whiteScore, int blackScore) {
        this.whiteScore = whiteScore;
        this.blackScore = blackScore;
    }
    
    /**
     * Add black score
     *
     * @param number
     */
    public void addBlackScore(int number)
    {
        this.blackScore += number;
    }

    /**
     * Black score getter
     *
     * @return int
     */
    public int getBlackScore()
    {
        return this.blackScore;
    }
    
    /**
     * Add white score
     *
     * @param number
     */
    public void addWhiteScore(int number)
    {
        this.whiteScore += number;
    }
    
    /**
     * White score getter
     *
     * @return int
     */
    public int getWhiteScore()
    {
        return this.whiteScore;
    }
    
    /**
     * Override toString method
     * @return String
     */
    @Override
    public String toString()
    {
        return "White:" + this.whiteScore + " Black:" + this.blackScore;
    }
}
