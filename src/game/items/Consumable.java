package game.items;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;

/**
 * Interface for consumable objects.
 * Eg. An egg can be consumed by an actor.
 */
public interface Consumable {
    /**
     * Defines the effect of consuming this item by the specified actor.
     * @param actor the actor consuming this item
     */
    void consume(Actor actor, GameMap map);
}
