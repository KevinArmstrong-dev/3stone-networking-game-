package pkg3stone.network;

import pkg3stone.engine.Move;
import pkg3stone.engine.Piece;
import pkg3stone.engine.Result;

/**
 * INetworkClientClient Interface
 *
 * @author Svitlana Myronova
 */
public interface INetworkClientClient {

    /**
     * Method will be called to place the stone on the board
     *
     * @param piece
     * @param move
     */
    public void placeStone(Piece piece, Move move);
    
    /**
     * Method will be called to report about an illegal move
     *
     * @param move
     */
    public void reportIllegalMove(Move move);
    
    /**
     * Method will be called to report result
     *
     * @param result
     */
    public void reportResult(Result result);
    
    /**
     * Method will be called to update result
     *
     * @param result
     */
    public void updateResult(Result result, int blackStones, int whiteStones);
}
