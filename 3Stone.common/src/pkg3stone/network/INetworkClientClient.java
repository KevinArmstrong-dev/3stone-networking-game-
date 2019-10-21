/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg3stone.network;

import pkg3stone.engine.Move;
import pkg3stone.engine.Piece;
import pkg3stone.engine.Result;

/**
 *
 * @author Svitlana Myronova
 */
public interface INetworkClientClient {
    public void placeStone(Piece piece, Move move);
    public void reportIllegalMove(Move move);
    public void reportResult(Result result);
}
