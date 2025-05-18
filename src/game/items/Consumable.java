package game.items;

import edu.monash.fit2099.engine.actors.Actor;

/**
 * Interface for consumable objects.
 * Eg. An egg can be consumed by an actor.
 */
public interface Consumable {
    /**
     * Defines the effect of consuming this item by the specified actor.
     * @param actor the actor consuming this item
     */
    void consume(Actor actor);
}
