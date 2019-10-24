package pkg3stone.engine;

/**
 * MoveType Enum
 *
 * @author Svitlana Myronova
 */
public enum MoveType {
    PROPOSED(0),
    ILLEGAL(1),
    CONFIRMED(2),
    LAST_MOVE_AND_CONTINUE(3),  //////////////////////////////
    LAST_MOVE_AND_GAME_OVER(4);

    private final int value;

    /**
     * Constructor
     * 
     * @param value
     */
    private MoveType(int value) {
        this.value = value;
    }

    /** 
     * Value getter
     *
     * @return int 
     */
    public int getValue() {
        return this.value;
    }

    /**
     * Get MoveType from the value
     *
     * @param value
     * @return MoveType
     */
    static public MoveType fromValue(int value) {
        for (MoveType t : MoveType.values()) {
            if (t.getValue() == value) {
                return t;
            }
        }
        return ILLEGAL;
    }
}
