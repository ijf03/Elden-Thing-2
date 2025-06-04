package game.actors.boss;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.actions.AttackAction;
import game.capabilities.Status;

public class BedOfChaos extends Actor {
    private static final int BASE_DAMAGE = 25;
    private static final int BASE_ACCURACY = 75;

    private final Branch growthTree;
    private int currentTotalDamage = BASE_DAMAGE;


    public BedOfChaos() {
        super("Bed of Chaos", 'T', 1000);
        this.growthTree = new Branch();
        this.setIntrinsicWeapon(new IntrinsicWeapon(BASE_DAMAGE, "lashes", BASE_ACCURACY) {});
    }

    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        display.println(this + " is growing...");

        int bonusBefore = getCurrentAttackBonus();

        int leavesBefore = countLeaves(growthTree);
        while (countLeaves(growthTree) == leavesBefore) {
            growthTree.growWithResult(display);
        }

        int heal = growthTree.applyHeal();
        if (heal > 0) {
            display.println(this + " is healed for " + heal + " HP.");
            this.heal(heal);
        }

        int bonusAfter = getCurrentAttackBonus();
        int bonusThisTurn = bonusAfter - bonusBefore;
        currentTotalDamage += bonusThisTurn;


        for (Exit exit : map.locationOf(this).getExits()) {
            Actor target = exit.getDestination().getActor();
            if (target != null && target.hasCapability(Status.HOSTILE_TO_ENEMY)) {
                IntrinsicWeapon weapon = new IntrinsicWeapon(currentTotalDamage, "lashes", BASE_ACCURACY) {};
                return new AttackAction(target, exit.getName(), weapon);
            }
        }

        return new DoNothingAction();
    }

    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = new ActionList();
        if (otherActor.hasCapability(Status.HOSTILE_TO_ENEMY)) {
            actions.add(new AttackAction(this, direction, otherActor.getIntrinsicWeapon()));
        }
        return actions;
    }

    private int getCurrentAttackBonus() {
        int total = 0;
        for (Growable child : growthTree.getChildren()) {
            total += child.getAttackBonus();
        }
        return total;
    }

    private int countLeaves(Growable node) {
        if (node instanceof Leaf) return 1;
        if (node instanceof Branch branch) {
            int total = 0;
            for (Growable child : branch.getChildren()) {
                total += countLeaves(child);
            }
            return total;
        }
        return 0;
    }
}
