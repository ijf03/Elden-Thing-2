package game.magic;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.GameMap;
/**
 * LifestealFist epresent a magic item that can have a life-stealing strike,
 * it deals damage equal to 30% of the target’s maximum health,
 * and heals the caster by the same amount.
 *
 */
public class LifestealFist extends MagicItem {
    /**
     * Constructor.
     */
    public LifestealFist() {
        super("Lifesteal Fist", 'L', 0, "strikes", 100, 10);
    }
    /**
     * Casts a spell using Lifesteal Fist on the target.
     * Calculates 30% of the target’s maximum health
     *
     * @param caster the actor caster a spell
     * @param target the actor affected by the spell
     * @param map the current game map
     * @return a string of the action and amount healed
     */
    @Override
    public String cast(Actor caster, Actor target, GameMap map) {
        Integer maxHp = target.getAttributeMaximum(BaseActorAttributes.HEALTH);
        int heal = (maxHp != null) ? (int) Math.ceil(maxHp * 0.3) : 0;
        target.hurt(heal);
        caster.heal(heal);
        return caster + " strikes " + target + " with Lifesteal Fist, healing " + heal + " HP.";
    }
}
