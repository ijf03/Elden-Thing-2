package game.magic;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actors.Player;
import game.magic.effects.Burning;

public class FlamingStrike extends MagicItem {
    public FlamingStrike() {
        super("Flaming Strike", 'F', true, 30);
    }

    @Override
    public String cast(Actor caster, Actor target, GameMap map) {
        int mana;
        if (caster instanceof Player) {
            mana = ((Player) caster).getMana();
        } else {
            mana = 0;
        }
        int damage;
        String desc;
        if (mana >= 200) {
            damage = 150;
            target.addStatusEffect(new Burning());
            desc = caster + " unleashes Flaming Strike (200+ mana), dealing " + damage + " damage and burning " + target + " for 3 turns.";
        } else if (mana > 100) {
            damage = 150;
            desc = caster + " uses Flaming Strike (100-199 mana), dealing " + damage + " damage.";
        } else {
            damage = 100;
            desc = caster + " strikes " + target + " with Flaming Strike (<100 mana), dealing " + damage + " damage.";
        }
        target.hurt(damage);
        return desc;
    }
}
