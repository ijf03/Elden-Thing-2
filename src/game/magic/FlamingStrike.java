package game.magic;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.GameMap;
import game.magic.effects.Burning;

/**
 * FlamingStrike represent a magic item that have flaming attack.
 * Damage is caused is scales with the caster’s current mana,
 * if the mana high enough, it will trigger a Burning effect.
 *
 */
public class FlamingStrike extends MagicItem {
    /**
     * Constructor.
     *
     */
    public FlamingStrike() {
        super("Flaming Strike", 'F', 0, "ignites", 100, 30);
    }
    /**
     * Casts a spell using Flaming Strike on the target.
     * - If the caster has >= 200 mana, deals 300 damage and applies a Burning effect.
     * - If the caster has > 100 mana, deals 250 damage.
     * - If the caster has <= 100 mana, deals 200 damage.
     *
     * @param caster the actor cast the spell
     * @param target the actor affected by the spell
     * @param map the current game map
     * @return a string of the action and damage made
     */
    @Override
    public String cast(Actor caster, Actor target, GameMap map) {
        Integer CurrentMana = caster.getAttribute(BaseActorAttributes.MANA);
        int mana = (CurrentMana != null) ? CurrentMana : 0;
        int damage;
        if (mana >= 200) {
            damage = 300;
            target.addStatusEffect(new Burning());
        } else if (mana > 100) {
            damage = 250;
        } else {
            damage = 200;
        }
        target.hurt(damage);
        return caster + " uses Flaming Strike on " + target + " for " + damage + " damage.";
    }
}

