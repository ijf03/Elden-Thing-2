package game.magic;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * Represents a magical ability that can be cast by one Actor on another.
 */
public interface Spell {

    /**
     * @return the amount of mana required to cast this Spell.
     */
    int getManaCost();

    /**
     * Executes this spell’s effect, from the caster onto the target.
     *
     * @param caster the actor who casts the spell
     * @param target the actor who receives the spell’s effect
     * @param map    the current game map context
     * @return a descriptive message of what happened
     */
    String cast(Actor caster, Actor target, GameMap map);
}
