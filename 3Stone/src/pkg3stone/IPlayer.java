/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg3stone;

/**
 *
 * @author svitl
 */
public interface IPlayer {
        
    void StartTheGame(Piece piece);
    Move ChooseMove(Board board);
}