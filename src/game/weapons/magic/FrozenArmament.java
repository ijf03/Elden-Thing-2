package game.weapons.magic;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.weapons.magic.effects.Frozen;

public class FrozenArmament extends MagicItem {
    public FrozenArmament() {
        super("Frozen Armament", 'A', 0, "freezes", 100, 20);
    }

    @Override
    public String cast(Actor caster, Actor target, GameMap map) {
        int damage = 20;
        target.hurt(damage);
        target.addStatusEffect(new Frozen());
        return caster + " casts Frozen Armament on " + target + ", freezing them.";
    }
}
