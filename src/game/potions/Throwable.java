package game.potions;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.StatusEffect;

/**
 * Interface for throwable objects.
 * Eg. A potion can be thrown by the player onto another actor.
 */
public interface Throwable {
    /**
     * Defines the action that occurs when this object is thrown.
     * @param actor the player throwing the item
     * @param target the target impacted
     */
    void throwed(Actor actor, Actor target);

    /**
     * Retrieves the StatusEffect that this throwable object might apply upon impact.
     * @return the status effect of the throwable
     */
    StatusEffect getEffect();
}
