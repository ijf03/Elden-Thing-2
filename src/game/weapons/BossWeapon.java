package game.weapons;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import edu.monash.fit2099.engine.weapons.Weapon;
import game.actions.AttackAction;
import game.actors.boss.BedOfChaos;
import java.util.Random;
import java.util.function.Supplier;

/**
 * A boss weapon that calculates damage
 */
public class BossWeapon extends IntrinsicWeapon {
    private final Supplier<Integer> damageSupplier;

    public BossWeapon(Supplier<Integer> damageSupplier) {
        super(damageSupplier.get(), "smashes", 75);
        this.damageSupplier = damageSupplier;
    }

    /**
     * Each time we attack, update damage to the latest value
     */
    @Override
    public String attack(Actor attacker, Actor target, GameMap map) {

        this.damage = damageSupplier.get();
        return super.attack(attacker, target, map);
    }


}
