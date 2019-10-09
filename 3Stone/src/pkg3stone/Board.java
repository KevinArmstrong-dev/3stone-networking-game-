/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg3stone;
import java.io.Serializable;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

/**
 *
 * @author svitl
 */
public class Board implements Serializable 
{
    private final Piece[][] pieces;
    
    private int playerWhitePieces = 15;
    private int playerBlackPieces = 15;
    
    private Piece lastStonePlayed = Piece.BLANK;
    private Move lastMove;
    
    private final int amountOfStones = 3;
    public final int numberOfRows = 11;
    public final int numberOfColumns = 11;
    
    
    //Constructor
    public Board() 
    {
        this.pieces = new Piece[numberOfRows][numberOfColumns];
        for(Piece[] row : this.pieces)
        {
            Arrays.fill(row, Piece.BLANK);
        }
        
        //place in the center of the board is barred
        this.pieces[numberOfRows/2][numberOfColumns/2] = Piece.BARRED;
    }
    
    //Method returns a deep copy of the Board object
    public Board deepCopy() 
    {
        try 
        {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);

            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (Board) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }
    
    protected Piece getLastStonePlayed()
    {
        return this.lastStonePlayed;
    }
    
    /**
     *
     * @param m
     * @return
     */
    public Piece getPiece(Move m)
    {
        return pieces[m.getRow()][m.getColumn()];
    }
    
    //Method checks if it is legal to make a move into position(row, column) of the board,
    //sets this position with value of plaerColore 
    //sets the last stone played
    public boolean isPlayLegal(Piece player, Move move)
    {
        if(!move.isValidForBoard(this))
            return false;
        
        if(player != Piece.WHITE && player != Piece.BLACK)
            return false;
        
        if(lastStonePlayed == player)
            return false;
        
        if(getPiece(move) != Piece.BLANK)
            return false;
        
        if(lastMove != null)
        {
            if(move.getRow() == lastMove.getRow() || move.getColumn() == lastMove.getColumn())
                return true;
        
            if(hasFreePlaceInColumn(lastMove.getColumn()) || hasFreePlaceInRow(lastMove.getRow()))
                return false;
        }
        return true;
    }
    
    //method sets the last stone played 
    public void placeStone(Piece player, Move move)
    {
        pieces[move.getRow()][move.getColumn()] = player;
        lastStonePlayed = player;
        lastMove = move;
        
        if(player == Piece.WHITE)
        {
            playerWhitePieces--;
        }
        else if(player == Piece.BLACK)
        {
            playerBlackPieces--;
        }
    }
    
    //method checks if there is free space in the column
    protected boolean hasFreePlaceInColumn(int column)
    {
        for(int i=0; i<numberOfRows; i++)
        {
           if (pieces[i][column] == Piece.BLANK)
               return true;
        }
        return false;
    }
    
    //method checks if there is free space in the row    
    protected boolean hasFreePlaceInRow(int row)
    {
        for(int j=0; j<numberOfColumns; j++)
        {
           if (pieces[row][j] == Piece.BLANK)
               return true;
        }
        return false;
    }  

    //Method finds three stones in right diagonals
    private void findThreeStonesInRightDiagonals(Result result)
    {
        for (int i = 0; i <= numberOfRows - amountOfStones; i++)
        {
            for (int j = amountOfStones - 1; j < numberOfColumns; j++)
            {
                Piece firstPiece = pieces[i][j];
                int count = 0;
                for (int k = 0; k < amountOfStones; k++)
                {
                    if (pieces[i + k][j - k] != firstPiece)
                        break;
                    count = count + 1;
                }
                if (count == amountOfStones)
                {
                    if(firstPiece == Piece.BLACK)
                        result.addBlackScore(1); 
                    else
                        result.addWhiteScore(1);
                }
            }
        }
    }
    
    private int findThreeStonesInRightDiagonalsString(Piece check, Board b)
    {
        int matches = 0;
        for (int i = 0; i <= b.numberOfRows - b.amountOfStones; i++)
        {
            for (int j = b.amountOfStones - 1; j < b.numberOfColumns; j++)
            {
                Piece firstPiece = b.pieces[i][j];
                int count = 0;
                for (int k = 0; k < b.amountOfStones; k++)
                {
                    if (b.pieces[i + k][j - k] != firstPiece)
                        break;
                    count = count + 1;
                }
                if (count == b.amountOfStones)
                {
                    if(firstPiece == check)
                    {
                        matches++;
                    }       
                }
            }
        }
        return matches;
    }

    //Method finds three stones in left diagonals
    private void findThreeStonesInLeftDiagonals(Result result)
    {
        for (int i = 0; i <= numberOfRows - amountOfStones; i++)
        {
            for (int j = 0; j <= numberOfColumns - amountOfStones; j++)
            {
                Piece firstPiece = pieces[i][j];
                int count = 0;
                for (int k = 0; k < amountOfStones; k++)
                {
                    if (pieces[i + k][j + k] != firstPiece)
                        break;
                    count = count + 1;
                }
                if (count == amountOfStones)
                {
                    if(firstPiece == Piece.BLACK)
                        result.addBlackScore(1); 
                    else
                        result.addWhiteScore(1);
                }
            }
        }
    }
    
    //Method finds three stones in left diagonals
    private int findThreeStonesInLeftDiagonalsString(Piece check, Board b)
    {
        int matches = 0;
        for (int i = 0; i <= b.numberOfRows - b.amountOfStones; i++)
        {
            for (int j = 0; j <= b.numberOfColumns - b.amountOfStones; j++)
            {
                Piece firstPiece = b.pieces[i][j];
                int count = 0;
                for (int k = 0; k < b.amountOfStones; k++)
                {
                    if (b.pieces[i + k][j + k] != firstPiece)
                        break;
                    count = count + 1;
                }
               if (count == b.amountOfStones)
                {
                    if(firstPiece == check)
                    {
                        matches++;
                    }       
                }
            }
        }
        return matches;
    }

    //Method finds three stones in columns
    private void findThreeStonesInColumns(Result result)
    {
        for (int i = 0; i <= numberOfRows - amountOfStones; i++)
        {
            for (int j = 0; j < numberOfColumns; j++)
            {
                Piece firstPiece = pieces[i][j];
                int count = 0;
                for (int k = 0; k < amountOfStones; k++)
                {
                    if (pieces[i + k][j] != firstPiece)
                        break;
                    count = count + 1;
                }
                if (count == amountOfStones)
                {
                    if(firstPiece == Piece.BLACK)
                        result.addBlackScore(1); 
                    else
                        result.addWhiteScore(1);
                }
            }
        }
    }
    
    //Method finds three stones in columns
    private int findThreeStonesInColumnsString(Piece check, Board b)
    {
        int matches = 0;
        for (int i = 0; i <= b.numberOfRows - b.amountOfStones; i++)
        {
            for (int j = 0; j < b.numberOfColumns; j++)
            {
                Piece firstPiece = b.pieces[i][j];
                int count = 0;
                for (int k = 0; k < b.amountOfStones; k++)
                {
                    if (b.pieces[i + k][j] != firstPiece)
                        break;
                    count = count + 1;
                }
                if (count == b.amountOfStones)
                {
                    if(firstPiece == check)
                    {
                        matches++;
                    }       
                }
            }
        }
        return matches;
    }

    //Method finds three stones in rows
    private void findThreeStonesInRows(Result result)
    {
        for (int i = 0; i < numberOfRows; i++)
        {
            for (int j = 0; j <= numberOfColumns - amountOfStones; j++)
            {
                Piece firstPiece = pieces[i][j];
                int count = 0;
                for (int k = 0; k < amountOfStones; k++)
                {
                    if (pieces[i][j + k] != firstPiece)
                        break;
                    count = count + 1;
                }
                if (count == amountOfStones)
                {
                    if(firstPiece == Piece.BLACK)
                        result.addBlackScore(1); 
                    else
                        result.addWhiteScore(1);
                }
            }
        }
    }
    
    //Method finds three stones in rows
    private int findThreeStonesInRowsString(Piece check, Board b)
    {
        int matches = 0;
        for (int i = 0; i < b.numberOfRows; i++)
        {
            for (int j = 0; j <= b.numberOfColumns - b.amountOfStones; j++)
            {
                Piece firstPiece = b.pieces[i][j];
                int count = 0;
                for (int k = 0; k < b.amountOfStones; k++)
                {
                    if (b.pieces[i][j + k] != firstPiece)
                        break;
                    count = count + 1;
                }
                if (count == b.amountOfStones)
                {
                    if(firstPiece == check)
                    {
                        matches++;
                    }
                }
            }
        }
        return matches;
    }
    
    protected int calculatePoints(Piece p, Move m)
    {
        Board b = this.deepCopy();
        b.placeStone(p, m);
        int maxPossible = 0;
        maxPossible += this.findThreeStonesInRowsString(p,b);
        maxPossible += this.findThreeStonesInColumnsString(p,b);
        maxPossible += this.findThreeStonesInLeftDiagonalsString(p,b);
        maxPossible += this.findThreeStonesInRightDiagonalsString(p,b);
        return maxPossible;
    }
    
    protected Move getIdealMove(Piece p)
    {
        Move idealMove = new Move(0,0);
        int maxPoints = 0;
        for (int i = 0;i < this.pieces.length;i++)
        {
            for (int j = 0;j < this.pieces[i].length;j++)
            {
                if (calculatePoints(p,new Move(i,j)) > maxPoints)
                {
                    maxPoints = calculatePoints(p,new Move(i,j));
                    idealMove = new Move(i,j);
                }
            }
        }
        return idealMove;
    }
    
    /**
     *
     * @return
     */
    public Result resultOfGame()
    {
        Result result = new Result();
        findThreeStonesInRows(result);
        findThreeStonesInColumns(result);
        findThreeStonesInLeftDiagonals(result);
        findThreeStonesInRightDiagonals(result);        
        return result;
    }
    

    //Method checks if the game is over
    public boolean isGameOver()
    {
        if (playerWhitePieces == 0 && playerBlackPieces == 0)
            return true;
        else
            return false;
    }
    
   //overriding toString
    @Override
    public String toString()
    {
        String board = "";
        for (Piece[] piece : this.pieces) {
            for (Piece piece1 : piece) {
                switch (piece1) {
                    case BLACK:
                        board += "B";
                        break;
                    case WHITE:
                        board += "W";
                        break;
                    case BLANK:
                        board += "O";
                        break;
                    case BARRED:
                        board += " ";
                        break;
                }
                board += " ";
            }
            board += "\n";
        }
        return board;
    }
}
