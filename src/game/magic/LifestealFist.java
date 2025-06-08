package game.magic;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.GameMap;


public class LifestealFist extends MagicItem {
    public LifestealFist() {
        super("Lifesteal Fist", 'L', 0, "strikes", 100, 10);
    }

    @Override
    public String cast(Actor caster, Actor target, GameMap map) {
        Integer maxHp = target.getAttributeMaximum(BaseActorAttributes.HEALTH);
        int heal = (maxHp != null) ? (int) Math.ceil(maxHp * 0.3) : 0;
        target.hurt(heal);
        caster.heal(heal);
        return caster + " strikes " + target + " with Lifesteal Fist, healing " + heal + " HP.";
    }
}
