package game;

import edu.monash.fit2099.engine.positions.Location;

/**
 * Interface for hatchable objects.
 * Eg. An egg may hatch into another entity.
 */
public interface Hatchable {
    /**
     * Checks whether an objects can hatch.
     * @param currentLocation location to check hatching condition
     * @return true if can hatch, else false
     */
    boolean canHatch(Location currentLocation);

    /**
     * Performs the hatching process.
     * @param location location where it hatches
     */
    void hatch(Location location);
}
