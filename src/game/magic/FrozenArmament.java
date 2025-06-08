package game.magic;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.magic.effects.Frozen;

/**
 * deals 20 damage and applies a Frozen effect (5 turns, 30 damage each) to the target.
 */
public class FrozenArmament extends MagicItem {

    public FrozenArmament() {
        super("Frozen Armament", 'A', /*damage=*/0, "freezes", /*hitRate=*/100, /*manaCost=*/20);
    }

    @Override
    public String cast(Actor caster, Actor target, GameMap map) {
        int damage = 20;
        target.hurt(damage);
        target.addStatusEffect(new Frozen());
        return String.format(
                "%s casts Frozen Armament on %s, dealing %d damage and freezing them.",
                caster, target, damage
        );
    }
}
