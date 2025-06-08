package game.actors.boss;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.Weapon;
import game.actions.AttackAction;
import game.capabilities.Status;
import game.weapons.BossWeapon;
import java.util.List;

import java.util.ArrayList;

import static game.actions.AttackUtils.addSpellActions;
import static game.actions.AttackUtils.addWeaponActions;

/**
 * A stationary boss that grows and attacks with increasing damage.
 */
public class BedOfChaos extends Actor {

    private static final int basedamage = 25;
    private static final int baseAccuracy = 75;

//    private final BossWeapon bossWeapon;
//    private final Branch chaosTree;
//    private int totalAttackDamage = basedamage;
    private final BossPartInterface chaosTree;
    private int currentTotalDamage;
    private final BossWeapon bossWeapon;


    /**
     * Constructor for the Bed of Chaos.
     */
    public BedOfChaos() {
        super("Bed of Chaos", 'T', 1000);
        this.chaosTree = new Branch();
        this.currentTotalDamage = chaosTree.getAttackBonus();
        this.bossWeapon = new BossWeapon(this::getCurrentTotalDamage);
        this.setIntrinsicWeapon(bossWeapon);
    }

    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {

        display.println(this + " is growing...");

        int bonusBefore = calculateBonus();

        int leavesBefore = countLeaves(chaosTree);
        while (countLeaves(chaosTree) == leavesBefore) {
            if (chaosTree instanceof Growable) {
                ((Growable) chaosTree).grow(display);
            }
        }

        int heal = chaosTree.getHealingAmount();
        if (heal > 0) {
            display.println(this + " is healed for " + heal + " HP.");
            this.heal(heal);
        }
        if (this.getAttribute(BaseActorAttributes.HEALTH) <= 0) {
            display.println(this.unconscious(map));
            return new DoNothingAction();
        }

        int bonusAfter= calculateBonus();
        int bonusGained = bonusAfter - bonusBefore;
        currentTotalDamage += bonusGained;

        display.println(this + " prepares an attack (Damage = " + currentTotalDamage + ")");

        for (Exit exit : map.locationOf(this).getExits()) {
            Actor target = exit.getDestination().getActor();
            if (target != null && target.hasCapability(Status.HOSTILE_TO_ENEMY)) {
                return new AttackAction(target, exit.getName(), bossWeapon);
            }
        }

        return new DoNothingAction();
    }

    private int calculateBonus() {
        int total = 0;
        for (BossPartInterface part : chaosTree.getChildren()) {
            total += part.getAttackBonus();
        }
        return total;
    }

    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = new ActionList();
        if (otherActor.hasCapability(Status.HOSTILE_TO_ENEMY)) {
            actions.add(new AttackAction(this, direction, otherActor.getIntrinsicWeapon()));

            addWeaponActions(otherActor, this, direction, actions);
            addSpellActions( otherActor,this, actions);
        }
        return actions;
    }

    /**
     * Returns the full current attack damage including tree bonuses.
     */
    public int getCurrentTotalDamage() {
        return currentTotalDamage;
    }

//    /**
//     * Returns the bonus damage from current tree structure.
//     */
//    private int getCurrentAttackBonus() {
//        int total = 0;
//        for (Growable child : growthTree.getChildren()) {
//            total += child.getAttackBonus();
//        }
//        return total;
//    }

    /**
     * Counts the number of leaves in the tree.
     */
    private int countLeaves(BossPartInterface node) {
        if (node instanceof Leaf) return 1;
        if (node instanceof Branch) {
            int total = 0;
            for (BossPartInterface child : node.getChildren()) {
                total += countLeaves(child);
            }
            return total;
        }
        return 0;
    }
}
