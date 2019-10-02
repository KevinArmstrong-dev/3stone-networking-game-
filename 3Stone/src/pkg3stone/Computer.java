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

    public Computer() {
        this.r = new Random();
    }
    
    /**
     *
     * @param board
     * @return
     */
    @Override
    public Move ChooseMove(Board board) {
        
        while(true)
        {
            Move move = new Move(r.nextInt(board.numberOfRows), r.nextInt(board.numberOfColumns));
            if (board.getPiece(move) == Piece.WHITE)
            {
                Move horizontalCheck = this.checkHorizontalAdjacent(board, move);
                Move verticalCheck = this.checkVerticalAdjacent(board, move);
                if (horizontalCheck != null && verticalCheck == null)
                {
                    return horizontalCheck;
                }
                else if (horizontalCheck == null && verticalCheck != null)
                {
                    return verticalCheck;
                }
                else
                {
                    return null;
                }
            }
            else if (board.getPiece(move) == Piece.BLACK)
            {
                Move horizontalCheck = this.checkHorizontalAdjacent(board, move);
                Move verticalCheck = this.checkVerticalAdjacent(board, move);
                if (horizontalCheck != null && verticalCheck == null)
                {
                    return horizontalCheck;
                }
                else if (horizontalCheck == null && verticalCheck != null)
                {
                    return verticalCheck;
                }
                else
                {
                    return null;
                }
            }
            else if(board.getPiece(move) == Piece.BLANK)
            {
                return new Move(move.getRow(), move.getColumn());
            }
            else
            {
                return move;
            }
        }
    }
    
    protected Move checkHorizontalAdjacent(Board board, Move move)
    {
        if (board.hasFreePlaceInRow(move.getRow()) && board.isPlayLegal(board.getPiece(move), move))
        {
            if (board.hasFreePlaceInColumn(move.getColumn() + 1) && !(board.hasFreePlaceInColumn(move.getColumn() -1)))
            {
                return new Move(move.getRow(), move.getColumn() + 1);
            }
            else if (board.hasFreePlaceInColumn(move.getColumn() - 1) && !(board.hasFreePlaceInColumn(move.getColumn() + 1)))
            {
                return new Move(move.getRow(),move.getColumn() - 1);
            }
            else if (board.hasFreePlaceInColumn(move.getColumn() + 1) && board.hasFreePlaceInColumn(move.getColumn() -1))
            {
                return (r.nextInt(2) < 0.5) ? new Move(move.getRow(), move.getColumn() + 1) : new Move(move.getRow(),move.getColumn() - 1);
            }
            else
            {
                return null;
            }
        }
        return null;
    }
    
    protected Move checkVerticalAdjacent(Board board, Move move)
    {
        if (board.hasFreePlaceInColumn(move.getColumn()) && board.isPlayLegal(board.getPiece(move), move))
        {
            if (board.hasFreePlaceInRow(move.getRow() + 1) && !(board.hasFreePlaceInRow(move.getRow() -1)))
            {
                return new Move(move.getRow() + 1, move.getColumn());
            }
            else if (board.hasFreePlaceInRow(move.getRow() - 1) && !(board.hasFreePlaceInRow(move.getRow() + 1)))
            {
                return new Move(move.getRow() - 1,move.getColumn());
            }
            else if (board.hasFreePlaceInRow(move.getRow() - 1) && board.hasFreePlaceInRow(move.getRow() + 1))
            {
                return (r.nextInt(2) < 0.5) ? new Move(move.getRow() + 1, move.getColumn()) : new Move(move.getRow() - 1,move.getColumn());
            }
            else
            {
                return null;
            }
        }
        return null;
    }
}
