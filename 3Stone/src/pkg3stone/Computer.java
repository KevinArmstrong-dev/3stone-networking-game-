/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg3stone;

import java.util.Random;

/**
 *
 * @author svitl
 */
public class Computer implements IPlayer
{
    private final Random r;
    private Piece color;

    public Computer() {
        this.r = new Random();
    }
    
    public void StartTheGame(Piece piece)
    {
        this.color = piece;
    }
        
    /*@Override
    public Move ChooseMove(Board board) {
        
        while(true)
        {
            Move move = new Move(r.nextInt(board.numberOfRows), r.nextInt(board.numberOfColumns));
            if(board.getPiece(move) == Piece.BLANK)
                return move;
        }
    }*/
    
    /**
     *
     * @param board
     * @return
     */
    @Override     //simple version for testing
    public Move ChooseMove(Board board) {
        
        while(true)
        {
            Move move = new Move(r.nextInt(board.numberOfRows), r.nextInt(board.numberOfColumns));
            if(board.getPiece(move) == Piece.BLANK)
                return move;
        }
    }
}
