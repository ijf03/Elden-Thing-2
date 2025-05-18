package game;

import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;

/**
 * Interface for reproducible objects.
 * Eg. A SpiritGoat has the ability to reproduce an offspring.
 */
public interface Reproducible {
    /**
     * Checks whether an objects can reproduce.
     * @param location location to check reproduction condition
     * @return true if can reproduce, else false
     */
    boolean canReproduce(Location location);

    /**
     * Performs the reproduction process.
     * @param map the game map where reproduction occurs
     * @param location location where it reproduces
     */
    void reproduce(GameMap map, Location location);
}
