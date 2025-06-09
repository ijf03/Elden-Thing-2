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
 * Intrinsic weapon for the boss.
 * Calculates its attack damage based on the boss's tree structure.
 */
public class BossWeapon extends IntrinsicWeapon {
    private final Supplier<Integer> damageSupplier;
    /**
     * Constructs a BossWeapon with a supplier for dynamic damage calculation.
     *
     * @param damageSupplier Function that supplies the latest damage value
     */
    public BossWeapon(Supplier<Integer> damageSupplier) {
        super(damageSupplier.get(), "smashes", 75);
        this.damageSupplier = damageSupplier;
    }

    /**
     * Each time we attack, update damage to the latest value
     * @param attacker The actor using the weapon
     * @param target   The actor being attacked
     * @param map      The current game map
     * @return Result of the attack as a string
     */
    @Override
    public String attack(Actor attacker, Actor target, GameMap map) {

        this.damage = damageSupplier.get();
        return super.attack(attacker, target, map);
    }


}
