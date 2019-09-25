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
    private final Piece[][] pieces;
    
    private int playerWhitePieces = 15;
    private int playerBlackPieces = 15;
    
    protected Piece lastStonePlayed = Piece.BLANK;
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
        } catch (IOException | ClassNotFoundException e) {
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
        
        if(player != Piece.WHITE && player != Piece.BLACK)
            return false;
        
        if(lastStonePlayed == player)
            return false;
        
        if(pieces[row][column] != Piece.BLANK)
            return false;
        
        if(row == rowOfLastStonePlayed || column == columnOfLastStonePlayed)
            return true;
        
        if(hasFreePlaceInColumn(columnOfLastStonePlayed) || hasFreePlaceInRow(rowOfLastStonePlayed))
            return false;
        return true;
    }
    
    //method sets the last stone played 
    private void placeStone(Piece player, int column, int row)
    {
        pieces[row][column] = player;
        lastStonePlayed = player;
        rowOfLastStonePlayed = row;
        columnOfLastStonePlayed = column;
        
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
    private boolean hasFreePlaceInColumn(int column)
    {
        for(int i=0; i<numberOfRows; i++)
        {
           if (pieces[i][column] == Piece.BLANK)
               return true;
        }
        return false;
    }
    
    //method checks if there is free space in the row    
    private boolean hasFreePlaceInRow(int row)
    {
        for(int j=0; j<numberOfColumns; j++)
        {
           if (pieces[row][j] == Piece.BLANK)
               return true;
        }
        return false;
    }  

    //Method finds three stones in right diagonals
    private Result findThreeStonesInRightDiagonals(Piece player)
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
    private Result findThreeStonesInLeftDiagonals(Piece player)
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
    private Result findThreeStonesInColumns(Piece player)
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
    private Result findThreeStonesInRows(Piece player)
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
    protected boolean isGameOver()
    {
        if (playerWhitePieces == 0 && playerBlackPieces == 0)
            return true;
        else
            return false;
    }
    
   //overriding toString
    public String toString()
    {
        String board = "";
        for (int i = 0; i < this.pieces.length; i++)
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
            board += "\n";
        }
        return board;
    }
}
