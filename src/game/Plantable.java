package game;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * Crops are plantable by actors on the ground
 * They begin their lifecycle as a seed, then planted, then finish as bloomed
 * @author Trinh Nguyen
 */
public interface Plantable {

    /**
     * All plantable objects only need one function which is the logic involved when they are planted (ie how they affect
     * the farmer, surroundings etc.
     * @param actor doing the planting
     * @param map where the planting is done
     */
    void PlantAction(Actor actor, GameMap map);
}
