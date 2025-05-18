package game;

/**
 * Class representing the counter for game turns.
 * @author Sher Tan
 */
public class Turns {
    private final int initialTurnCounter;
    private int turnCounter;

    /**
     * Constructor
     * @param turnCounter the initial value for the turn counter
     */
    public Turns(int turnCounter) {
        this.initialTurnCounter = turnCounter;
        this.turnCounter = turnCounter;
    }

    // Getters and Setters
    public int getInitialTurnCounter() {
        return initialTurnCounter;
    }

    public int getTurnCounter() {
        return turnCounter;
    }

    public void setTurnCounter(int turnCounter) {
        this.turnCounter = turnCounter;
    }

    /**
     * Decreases the turn counter by 1.
     */
    public void decreaseCounter() {
        setTurnCounter(getTurnCounter() - 1);
    }

    /**
     * Increases the turn counter by 1.
     */
    public void increaseCounter() {
        setTurnCounter(getTurnCounter() + 1);
    }

    /**
     * Resets the turn counter to its initial value.
     */
    public void resetTurnCounter() {
        setTurnCounter(getInitialTurnCounter());
    }
}
