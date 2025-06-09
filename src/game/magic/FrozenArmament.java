package game.magic;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.magic.effects.Frozen;
/**
 * FrozenArmament represent a magic item that can freezing strike,
 * applies a Frozen effect on the target.
 *
 */
public class FrozenArmament extends MagicItem {
    /**
     * Constructor.
     */
    public FrozenArmament() {
        super("Frozen Armament", 'A', 0, "freezes", 100, 20);
    }
    /**
     * Casts a spell using Frozen Armament on the target.
     * Deals an 20 damage, then inflicts a Frozen} effect.
     *
     * @param caster the actor cast the spell
     * @param target the actor affected by the spell
     * @param map the current game map
     * @return a string of the action
     */
    @Override
    public String cast(Actor caster, Actor target, GameMap map) {
        int damage = 20;
        target.hurt(damage);
        target.addStatusEffect(new Frozen());
        return caster + " casts Frozen Armament on " + target + ", freezing them.";
    }
}
