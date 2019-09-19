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

/**
 *
 * @author svitl
 */
public class Board implements Serializable 
{
    private Piece[][] pieces;
    
    private Piece lastStonePlayed = Piece.BLANK;
    private int rowOfLastStonePlayed = 0;
    private int columnOfLastStonePlayed = 0;
    
    private final int amountOfStones = 3;
    private final int numberOfRows = 11;
    private final int numberOfColumns = 11;
    
    
    //Constructor
    public Board() 
    {
        this.pieces = new Piece[numberOfRows][ numberOfColumns];
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
        } catch (IOException e) {
                return null;
        } catch (ClassNotFoundException e) {
                return null;
        }
    }
    
    //Method checks if it is legal to make a move into position(row, column) of the board,
    //sets this position with value of plaerColore 
    //sets the last stone played
    public boolean isPlayLegal(Piece player, int column, int row)
    {
        if(column < 0 || column >= numberOfColumns || row < 0 || row >= numberOfRows)
            return false;
        if(lastStonePlayed == Piece.BLANK && pieces[row][column] == Piece.BLANK)
        {
            pieces[row][column] = player;
            setLastStonePlayed(player, column, row);
            return true;
        }
        if(lastStonePlayed != player && pieces[row][column] == Piece.BLANK && (row == rowOfLastStonePlayed || column == columnOfLastStonePlayed))
        {
            pieces[row][column] = player;
            setLastStonePlayed(player, column, row);
            return true;
        }
        if(lastStonePlayed != player && pieces[row][column] == Piece.BLANK && (isFreePlaceInColumn(columnOfLastStonePlayed) == false && isFreePlaceInRow(rowOfLastStonePlayed) == false))
        {
            pieces[row][column] = player;
            setLastStonePlayed(player, column, row);
            return true;
        }
        return false;
    }
    
    //method sets the last stone played 
    private void setLastStonePlayed(Piece player, int column, int row)
    {
        lastStonePlayed = player;
        rowOfLastStonePlayed = row;
        columnOfLastStonePlayed = column;
    }
    
    //method checks if there is free space in the column
    private boolean isFreePlaceInColumn(int column)
    {
        for(int i=0; i<numberOfRows; i++)
        {
           if (pieces[i][column] == Piece.BLANK)
               return true;
        }
        return false;
    }
    
    //method checks if there is free space in the row    
    private boolean isFreePlaceInRow(int row)
    {
        for(int j=0; j<numberOfColumns; j++)
        {
           if (pieces[row][j] == Piece.BLANK)
               return true;
        }
        return false;
    }  

    //Method finds three stones in right diagonals
    private Result FindThreeStonesInRightDiagonals(Piece player)
    {
        Result result = new Result();
        int countScore = 0;
        
        for (int i = 0; i <= numberOfRows - amountOfStones; i++)
        {
            for (int j = amountOfStones - 1; j < numberOfColumns; j++)
            {
                Piece firstPiece = pieces[i][j];
                if (firstPiece != player)
                    continue;
                int count = 0;
                for (int k = 0; k < amountOfStones; k++)
                {
                    if (pieces[i + k][j - k] != firstPiece)
                        break;
                    count = count + 1;
                }
                if (count == amountOfStones)
                {
                    countScore++;
                }
            }
        }
        
        if(player == Piece.BLACK)
            result.setBlackScore(countScore); 
        else
            result.setWhiteScore(countScore);
        return result;
    }

    //Method finds three stones in left diagonals
    private Result FindThreeStonesInLeftDiagonals(Piece player)
    {
        Result result = new Result();
        int countScore = 0;
        
        for (int i = 0; i <= numberOfRows - amountOfStones; i++)
        {
            for (int j = 0; j <= numberOfColumns - amountOfStones; j++)
            {
                Piece firstPiece = pieces[i][j];
                if (firstPiece != player)
                    continue;
                int count = 0;
                for (int k = 0; k < amountOfStones; k++)
                {
                    if (pieces[i + k][j + k] != firstPiece)
                        break;
                    count = count + 1;
                }
                if (count == amountOfStones)
                {
                     countScore++;
                }
            }
        }
        if(player == Piece.BLACK)
            result.setBlackScore(countScore); 
        else
            result.setWhiteScore(countScore);
        return result;
    }

    //Method finds three stones in columns
    private Result FindThreeStonesInColumns(Piece player)
    {
        Result result = new Result();
        int countScore = 0;
        
        for (int i = 0; i <= numberOfRows - amountOfStones; i++)
        {
            for (int j = 0; j < numberOfColumns; j++)
            {
                Piece firstPiece = pieces[i][j];
                if (firstPiece != player)
                    continue;
                int count = 0;
                for (int k = 0; k < amountOfStones; k++)
                {
                    if (pieces[i + k][j] != firstPiece)
                        break;
                    count = count + 1;
                }
                if (count == amountOfStones)
                {
                     countScore++;
                }
            }
        }
        
        if(player == Piece.BLACK)
            result.setBlackScore(countScore); 
        else
            result.setWhiteScore(countScore);
        return result;
    }

    //Method finds three stones in rows
    private Result FindThreeStonesInRows(Piece player)
    {
        Result result = new Result();
        int countScore = 0;
        
        for (int i = 0; i < numberOfRows; i++)
        {
            for (int j = 0; j <= numberOfColumns - amountOfStones; j++)
            {
                Piece firstPiece = pieces[i][j];
                if (firstPiece != player)
                    continue;
                int count = 0;
                for (int k = 0; k < amountOfStones; k++)
                {
                    if (pieces[i][j + k] != firstPiece)
                        break;
                    count = count + 1;
                }
                if (count == amountOfStones)
                {
                    countScore++;
                }
            }
        }
        
        if(player == Piece.BLACK)
            result.setBlackScore(countScore); 
        else
            result.setWhiteScore(countScore);
        return result;
    }

    //Method checks if the game is over
    private boolean IsGameIsOver()
    {
        for(int i=0; i<numberOfRows; i++)
        {
            for(int j=0; j<numberOfColumns; j++)
            {
                if (pieces[i][j] == Piece.BLANK)
                {
                    return false;
                }
            }
        }
        return true;
    }
    
   //overriding toString
    public String toString()
    {
        String board = "";
            for (int i = 0; i< this.pieces.length; i--)
            {
                for (int j = 0; j < this.pieces[i].length; j++)
                {
                    switch (this.pieces[i][j])
                    {
                        case BLACK:
                            board += "B";
                            break;
                        case WHITE:
                            board += "W";
                            break;
                        case BLANK:
                            board += "O";
                            break;
                    }
                    board += " ";
                }
                board += "\n\n";
            }
            return board;
    }
}
