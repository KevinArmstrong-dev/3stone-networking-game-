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
    
    private Player[] players;
    private Piece[] pieces;
    private Board board;

    // constructor
    public Game(Player whitePlayer, Player blackPlayer)
    {
        players = new Player[2];
        players[0] = whitePlayer;
        players[1] = blackPlayer;
        pieces = new Piece[2];
        pieces[0] = Piece.WHITE;
        pieces[1] = Piece.BLACK;
        board = new Board();
    }

    // play method that is responsible for the course of the game
    public Result Play()
    {
       return null;
    }

    // return first player
    public Player getFirstPlayer()
    {
        return players[0];
    }


    // return second player
    public Player getSecondPlayer()
    {
        return players[1];
    }

    // the method to display board
    private void DisplayBoard()
    {
        System.out.print(board.toString());
    }
    
    
}
