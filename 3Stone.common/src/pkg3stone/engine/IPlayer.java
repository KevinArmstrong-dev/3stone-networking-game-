/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg3stone.engine;

/**
 *
 * @author Svitlana Myronova
 */
public interface IPlayer {

    /**
     * Called once by game before game begins to notify player about it's color
     *
     * @param piece
     * @throws java.lang.Exception
     */
    void startTheGame(Piece piece) throws Exception;

    /**
     * Returns player color assigned by game through startTheGame call.
     *
     * @return Piece
     */
    Piece getCurrentColor();

    /**
     * Called by game to ask player to prepare for new chooseMove call
     *
     * @param board
     * @throws java.lang.Exception
     */
    void prepareMove(Board board) throws Exception;

    /**
     * Function called by Game to ask player about its next move
     *
     * @param board
     * @return Move
     * @throws java.lang.Exception
     */
    Move chooseMove(Board board) throws Exception;

    /**
     * Called by game if Move returned by chooseMove is illegal for current game
     * state.
     *
     * @param moveType
     * @param move
     * @throws java.lang.Exception
     */
    void moveOutcome(MoveType moveType, Move move) throws Exception;

    /**
     * Called by game to notify about game result
     * @param board
     * @param result
     * @throws java.lang.Exception
     */
    public void gameOver(Board board, Result result) throws Exception;

    /**
     * Needs to be called to cleanup resources created by this player.
     */
    void close();
}