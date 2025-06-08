package game.magic;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * Interface for all spell-castable items.
 */
public interface Spell {
    /**
     * Defines the behavior of casting a magical effect.
     *
     * @param caster the actor who casts the spell
     * @param target the actor who is affected
     * @param map    the game map where casting happens
     * @return a description of the result
     */
    String cast(Actor caster, Actor target, GameMap map);

    /**
     * @return the mana cost required to cast the spell
     */
    int getManaCost();
}
