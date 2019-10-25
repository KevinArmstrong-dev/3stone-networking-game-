package pkg3stone.engine;

/**
 *
 * @author Svitlana Myronova 
 * @author Tall_Optimist_GC
 */
public interface IDisplay {

    /**
    * Method shows the board
    * 
    *  @param board
    */
    public void ShowBoard(Board board);

    /**
    * Method shows the result of the game
    * 
    *  @param result
    */
    public void ShowResult(Result result);
}
