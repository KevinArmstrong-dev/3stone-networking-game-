/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg3stone;

/**
 *
 * @author 1733408
 */
public class Board {
    private final int rows = 11;
    private final int columns = 11;
    private Space[][] spaces;
    private PlayerType playerTurn;
    private Player client;
    private Player server;


    public Board()
    {
        this.spaces = new Space[rows][columns];
        for (int i = 0;i < spaces.length;i++)
        {
            for (int j = 0;j < spaces[i].length;j++)
            {
                spaces[i][j] = new Space(i,j);
            }
        }
        this.playerTurn = PlayerType.CLIENT;
        this.client = new Player(PlayerType.CLIENT);
        this.server = new Player(PlayerType.SERVER);
    }
    
    public boolean checkIfSquare()
    {
        return this.rows == this.columns;
    }
    
    public void occupy(int row, int column, Stone stone)
    {
        this.spaces[row][column].occupy(stone);
    }
    
    public void unoccupy(int row, int column)
    {
        this.spaces[row][column].unoccupy();
    }
    
    public void changeTurn(PlayerType type)
    {
        this.playerTurn = type;
    }
    
    public PlayerType getTurn()
    {
        return this.playerTurn;
    }
    
    public void checkFor3Space()
    {
        for (int i = 0;i < spaces.length;i++)
        {
            for (int j = 0;j < spaces[i].length;j++)
            {
                if ((spaces[i][j].getIsOccupied() == true && spaces[i+1][j].getIsOccupied() == true
                   && spaces[i][j].getStone().getWhoPlayed() == spaces[i+1][j].getStone().getWhoPlayed()
                   && spaces[i+2][j].getIsOccupied() == true
                   && spaces[i+1][j].getStone().getWhoPlayed() == spaces[i+2][j].getStone().getWhoPlayed())
                    ||
                   ((spaces[i][j].getIsOccupied() == true && spaces[i][j+1].getIsOccupied() == true
                   && spaces[i][j].getStone().getWhoPlayed() == spaces[i][j+1].getStone().getWhoPlayed()
                   && spaces[i][j+2].getIsOccupied() == true
                   && spaces[i][j+1].getStone().getWhoPlayed() == spaces[i][j+2].getStone().getWhoPlayed()))
                        ||
                   ((spaces[i][j].getIsOccupied() == true && spaces[i+1][j+1].getIsOccupied() == true
                   && spaces[i][j].getStone().getWhoPlayed() == spaces[i+1][j+1].getStone().getWhoPlayed()
                   && spaces[i+2][j+2].getIsOccupied() == true
                   && spaces[i+1][j+1].getStone().getWhoPlayed() == spaces[i+2][j+2].getStone().getWhoPlayed())))
                   {
                       if (spaces[i][j].getStone().getWhoPlayed() == PlayerType.CLIENT)
                       {
                           this.client.addPoints(1);
                       }
                       else
                       {
                           this.server.addPoints(1);
                       }
                   }
            }
        } 
    }
}
