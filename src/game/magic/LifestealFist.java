package game.magic;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.GameMap;


public class LifestealFist extends MagicItem {

    public LifestealFist() {
        super("Lifesteal Fist", 'L', true, 10);
    }

    @Override
    public String cast(Actor caster, Actor target, GameMap map) {
        Integer maxHpAttr = target.getAttributeMaximum(BaseActorAttributes.HEALTH);
        int maxHp = (maxHpAttr != null) ? maxHpAttr : 0;
        int healAmount = (int) Math.ceil(maxHp * 0.3);
        target.hurt(healAmount);
        caster.heal(healAmount);
        return caster + " strikes " + target + " with Lifesteal Fist, healing for " + healAmount + " HP.";
    }
}

