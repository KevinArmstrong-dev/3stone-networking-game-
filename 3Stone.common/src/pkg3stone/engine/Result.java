/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg3stone.engine;

/**
 *
 * @author svitl
 */
public class Result {
    
    private int blackScore = 0;
    private int whiteScore = 0;
    
    public void addBlackScore(int number)
    {
        this.blackScore += number;
    }
    public int getBlackScore()
    {
        return this.blackScore;
    }
    
    public void addWhiteScore(int number)
    {
        this.whiteScore += number;
    }
    
    public int getWhiteScore()
    {
        return this.whiteScore;
    }
}
