package game.magic;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * Interface for all magic items.
 *
 */
public interface Spell {
    /**
     * Performs the spell’s action, applying its effects to the target.
     *
     * @param caster the actor casts the spell
     * @param target the actor who is affected
     * @param map the game map where casting happens
     * @return a string describe the result
     */
    String cast(Actor caster, Actor target, GameMap map);

    /**
     * Returns the amount of mana required to cast the spell with specific item.
     *
     * @return mana cost
     */
    int getManaCost();
}
