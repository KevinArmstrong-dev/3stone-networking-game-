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
public class Stone {
    private final PlayerType whoPlayed;
    private final StoneColor color;
    
    public Stone(PlayerType who)
    {
        this.whoPlayed = who;
        if (who == PlayerType.CLIENT) { this.color = StoneColor.WHITE; }
        else { this.color = StoneColor.BLACK; }
    }
    
    public PlayerType getWhoPlayed()
    {
        return this.whoPlayed;
    }
}
