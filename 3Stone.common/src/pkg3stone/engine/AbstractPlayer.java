/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg3stone.engine;

/**
 *
 * @author Svitlana Myronova
 */
public abstract class AbstractPlayer implements IPlayer {
    private Piece currentColor;
    
    @Override
    public void startTheGame(Piece piece) throws Exception
    {
        this.currentColor = piece;
    }
    
    @Override
    public Piece getCurrentColor()
    {
        return currentColor;
    }
}
