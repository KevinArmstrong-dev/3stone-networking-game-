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
public class MoveAndResult {
    public Move move;
    public Result result;

    public MoveAndResult(Move move, Result result) {
        this.move = move;
        this.result = result;
    }
}
