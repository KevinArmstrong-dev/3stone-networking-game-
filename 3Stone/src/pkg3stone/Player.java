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
public class Player {
    private final PlayerType type;
    private int points;
    
    public Player(PlayerType type)
    {
        this.type = type;
        this.points = 0;
    }
    
    public int getPoints()
    {
        return this.points;
    }
    
    public void addPoints(int add)
    {
        this.points += add;
    }
}
