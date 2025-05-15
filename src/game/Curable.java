package game;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Location;

/**
 * Interface for objects that are curable
 * Eg. OmenSheep is curable by the player using the talisman
 */

public interface Curable {
    /**
     * Depending on the curable object, curing them invokes different reactions
     * @param curer is an actor that is doing the curing
     * @param location where the actor is doing the curing
     */
    void cure(Actor curer, Location location);
}
