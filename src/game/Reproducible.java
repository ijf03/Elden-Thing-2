package game;

import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;

/**
 * Interface for reproducible objects.
 * Eg. A SpiritGoat has the ability to reproduce an offspring.
 */
public interface Reproducible {
    /**
     * Checks map to check reproduction condition
     * @return true if can reproduce, else false
     */
    boolean canReproduce(GameMap map);

    /**
     * Performs the reproduction process.
     * @param map the game map where reproduction occurs
     */
    void reproduce(GameMap map);
}
