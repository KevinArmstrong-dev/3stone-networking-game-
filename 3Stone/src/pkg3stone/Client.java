/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg3stone;

/**
 *
 * @author svitl, Tall_Optimist_GC
 */
public class Client implements IPlayer 
{   
    public Move ChooseMove(Board board)
    {
        return new Move(1,1);
    }
}
