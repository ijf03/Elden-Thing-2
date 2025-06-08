package game.magic;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.GameMap;
import game.magic.effects.Burning;

/**
 * Deals 200–300 damage based on the caster’s current mana.
 * if caster has  200 mana or more, it also applies a Burning effect (3 turns at 150 damage each).
 */
public class FlamingStrike extends MagicItem {

    public FlamingStrike() {
        super("Flaming Strike", 'F', /*damage=*/0, "ignites", /*hitRate=*/100, /*manaCost=*/30);
    }

    @Override
    public String cast(Actor caster, Actor target, GameMap map) {
        Integer manaAttr = caster.getAttribute(BaseActorAttributes.MANA);
        int mana = (manaAttr == null) ? 0 : manaAttr;

        boolean applyBurn = mana >= 200;
        int damage = applyBurn ? 300 : (mana > 100 ? 250 : 200);

        target.hurt(damage);
        if (applyBurn) {
            target.addStatusEffect(new Burning());
        }

        return String.format(
                "%s uses Flaming Strike on %s for %d damage%s.",
                caster,
                target,
                damage,
                applyBurn ? " and sets them ablaze" : ""
        );
    }
}
