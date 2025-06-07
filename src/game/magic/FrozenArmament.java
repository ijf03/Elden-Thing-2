package game.magic;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.magic.effects.Frozen;

public class FrozenArmament extends MagicItem {

    public FrozenArmament() {
        super("Frozen Armament", 'A', true, 20);
    }

    @Override
    public String cast(Actor caster, Actor target, GameMap map) {
        target.addStatusEffect(new Frozen());
        return caster + " casts Frozen Armament on " + target + ", freezing them for 5 turns.";
    }
}
