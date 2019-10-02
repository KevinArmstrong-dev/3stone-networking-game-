/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg3stone;

import java.util.Scanner;

/**
 *
 * @author svitl
 */
public class Game {    
    
    private final IDisplay display;
    private final IPlayer whitePlayer;
    private final IPlayer blackPlayer;
    private final Board board;

    // constructor
    public Game(IDisplay display, IPlayer whitePlayer, IPlayer blackPlayer)
    {
        this.display = display;

        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
        
        this.board = new Board();
    }

    private void MakePlayerMove(IPlayer player, Piece piece)
    {
        Move move = null;
        while(true)
        {                
            move = player.ChooseMove(board);
            if(board.isPlayLegal(piece, move))
                break;
        }
        board.placeStone(piece, move);
        display.ShowBoard(board);
    }
    
    // play method that is responsible for the course of the game
    public void play()
    {    
        display.ShowBoard(board);
        while(true)
        {     
            MakePlayerMove(whitePlayer, Piece.WHITE);
            if(board.isGameOver())
            {
               break;
            }    

            MakePlayerMove(blackPlayer, Piece.BLACK);
            if(board.isGameOver())
            {
               break;
            }    
        }

        Result result = board.resultOfGame(); 
        display.ShowResult(result);
    }      
 }