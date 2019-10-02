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
//class for testing purpose only
public class ConsolePlayer implements IPlayer 
{
    private final Scanner scan;

    public ConsolePlayer() {
        this.scan = new Scanner(System.in);
    }
    
    @Override
    public Move ChooseMove(Board board)
    {
        System.out.println("choose the move");
        String moveStr = scan.nextLine();  
        String[] coordinatsStr = moveStr.split(" ");
        return new Move(Integer.parseInt(coordinatsStr[0]),Integer.parseInt(coordinatsStr[1]));
    }    
}
