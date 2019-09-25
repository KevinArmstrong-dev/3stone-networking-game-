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
public class Game {    
    
    private IPlayer[] players;
    private Piece[] pieces;
    private Board board;
    //private IPlayer turn;

    // constructor
    public Game(Client whitePlayer, Computer blackPlayer)
    {
        players = new IPlayer[2];
        players[0] = whitePlayer;
        players[1] = blackPlayer;
        pieces = new Piece[2];
        pieces[0] = Piece.WHITE;
        pieces[1] = Piece.BLACK;
        board = new Board();
        //turn = whitePlayer;
    }

    // play method that is responsible for the course of the game
    public Result Play()
    {
        while (this.board.isGameOver() == false)
        {
            if (board.lastStonePlayed == Piece.WHITE)
            {
                this.players[1].ChooseMove(board);
            }
            else
            {
                this.players[0].ChooseMove(board);
            }
        }
        return null;
    }


    // return first player
    public IPlayer getFirstPlayer()
    {
        return players[0];
    }


    // return second player
    public IPlayer getSecondPlayer()
    {
        return players[1];
    }

    // the method to display board
    private void DisplayBoard()
    {
        System.out.print(board.toString());
    }
    
    
}
