/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg3stone.console;

import java.util.Scanner;
import pkg3stone.engine.AbstractPlayer;
import pkg3stone.engine.Board;
import pkg3stone.engine.Move;
import pkg3stone.engine.MoveType;
import pkg3stone.engine.Piece;

/**
 *
 * @author svitl
 */
//class for testing purpose only
public class ConsolePlayer extends AbstractPlayer {

    private final Scanner scan;

    /**
     *
     */
    public ConsolePlayer() {
        this.scan = new Scanner(System.in);
    }

    @Override
    public Move chooseMove(Board board) {
        System.out.println("Choose the move");
        String moveStr = scan.nextLine();
        String[] coordinatsStr = moveStr.split(" ");
        return new Move(Integer.parseInt(coordinatsStr[0]), Integer.parseInt(coordinatsStr[1]));
    }

    @Override
    public void moveOutcome(MoveType moveType, Move move) {
        if (moveType == MoveType.ILLEGAL) {
            System.out.println("Your move is illegal.");
        } else if (moveType == MoveType.CONFIRMED) {
            System.out.println("Your move is confirmed.");
        }
    }

    @Override
    public void close() {
        scan.close();
    }

    @Override
    public void lastMove(Piece lastStonePlayed, Move lastMove) {
        System.out.println("Last stone: " + lastStonePlayed + " move:" + lastMove.getRow() + "x" + lastMove.getColumn());
    }
}
