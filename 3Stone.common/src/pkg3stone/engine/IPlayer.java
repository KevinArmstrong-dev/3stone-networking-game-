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
     */
    void startTheGame(Piece piece);

    /**
     * Returns player color assigned by game through startTheGame call.
     *
     * @return Piece
     */
    Piece getCurrentColor();

    /**
     * Function called by Game to ask player about its next move
     *
     * @param board
     * @return Move
     */
    Move chooseMove(Board board);

    /**
     * Called by game if Move returned by chooseMove is illegal for current game
     * state.
     *
     * @param moveType
     * @param move
     */
    void moveOutcome(MoveType moveType, Move move);

    /**
     * Needs to be called to cleanup resources created by this player.
     */
    void close();
}
