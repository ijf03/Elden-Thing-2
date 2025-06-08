package game.magic;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.GameMap;
import game.magic.MagicItem;
import game.magic.effects.Burning;

public class FlamingStrike extends MagicItem {
    public FlamingStrike() {
        super("Flaming Strike", 'F', 0, "ignites", 100, 30);
    }

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

