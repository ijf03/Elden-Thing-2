package game.magic;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * steals 30% of the targets max HP and hurts by that amount then heals the caster by the same amount
 */
public class LifestealFist extends MagicItem {

    public LifestealFist() {
        super("Lifesteal Fist", 'L', /*damage=*/0, "strikes", /*hitRate=*/100, /*manaCost=*/10);
    }

    @Override
    public String cast(Actor caster, Actor target, GameMap map) {
        Integer maxHp = target.getAttributeMaximum(BaseActorAttributes.HEALTH);
        int steal = (maxHp == null) ? 0 : (int) Math.ceil(maxHp * 0.3);
        target.hurt(steal);
        caster.heal(steal);
        return String.format(
                "%s strikes %s with Lifesteal Fist, stealing and healing %d HP.",
                caster, target, steal
        );
    }
}
