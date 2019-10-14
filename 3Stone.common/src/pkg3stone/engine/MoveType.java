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
public enum MoveType {
    PROPOSED(0),
    ILLEGAL(1),
    CONFIRMED(2);
    
    private final int value;
    
    private MoveType(int value)
    {
        this.value = value;
    }
    
    public int getValue()
    {
        return this.value;
    }
    
    static public MoveType fromValue(int value)
    {
        for(MoveType t : MoveType.values())
        {
            if(t.getValue() == value)
                return t;
        }
        return ILLEGAL;
    }
}
