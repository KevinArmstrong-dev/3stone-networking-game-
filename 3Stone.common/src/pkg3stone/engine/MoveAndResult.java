
package pkg3stone.engine;

/**
 *
 * @author Svitlana Myronova
 */
public class MoveAndResult {
    public Move move;
    public Result result;

    /**
     * This Method will help in determining the result obtained by making
     * a certain move
     * 
     * @param move
     * @param result 
     */
    public MoveAndResult(Move move, Result result) {
        this.move = move;
        this.result = result;
    }
}
