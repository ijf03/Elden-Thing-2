package game.terrain;

/**
 * Enum used to determine which ground objects are cursed
 * As of now only blight is cursed
 */
public enum CursedGrounds {
    BLIGHT('x');

    /**
     * character of the cursed ground
     */
    public final char groundChar;

    /**
     * Constructor
     * @param groundChar
     */
    CursedGrounds(char groundChar) {
        this.groundChar = groundChar;
    }
}
