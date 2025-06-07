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
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import edu.monash.fit2099.engine.weapons.Weapon;
import game.actions.AttackAction;
import game.actions.CastSpellAction;
import game.capabilities.Status;
import game.magic.MagicItem;

import java.util.ArrayList;
import java.util.List;

public class BedOfChaos extends Actor {

    private static final int basedamage = 25;
    private static final int baseAccuracy = 75;

    private final Branch chaosTree;
    private int totalAttackDamage = basedamage;

    public BedOfChaos() {
        super("Bed of Chaos", 'T', 1000);
        this.chaosTree = new Branch();
        this.setIntrinsicWeapon(new IntrinsicWeapon(basedamage, "lashes", baseAccuracy) {});
    }

    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {

        display.println(this + " is growing...");

        int leavesBefore = chaosTree.countLeaves();
        int bonusBefore = calculateBonus();

        while (chaosTree.countLeaves() == leavesBefore) {
            chaosTree.growResult(display);
        }

        int heal = chaosTree.getHealingAmount();
        if (heal > 0 && this.getAttribute(BaseActorAttributes.HEALTH) > 0) {
            display.println(this + " is healed for " + heal + " HP.");
            this.heal(heal);
        }
        if (this.getAttribute(BaseActorAttributes.HEALTH) <= 0) {
            display.println(this.unconscious(map));
            return new DoNothingAction();
        }

        int bonusAfter = calculateBonus();
        int bonusGained = bonusAfter - bonusBefore;
        totalAttackDamage += bonusGained;

        display.println(this + " prepares an attack (Damage = " + totalAttackDamage + ")");

        for (Exit exit : map.locationOf(this).getExits()) {
            Actor target = exit.getDestination().getActor();
            if (target != null && target.hasCapability(Status.HOSTILE_TO_ENEMY)) {
                IntrinsicWeapon weapon = new IntrinsicWeapon(totalAttackDamage, "lashes", baseAccuracy) {};
                return new AttackAction(target, exit.getName(), weapon);
            }
        }
        return new DoNothingAction();
    }

    private int calculateBonus() {
        int total = 0;
        for (Growable child : chaosTree.getChildren()) {
            total += child.getAttackBonus();
        }
        return total;
    }

    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = new ActionList();
        if (otherActor.hasCapability(Status.HOSTILE_TO_ENEMY)) {
            actions.add(new AttackAction(this, direction, otherActor.getIntrinsicWeapon()));

            List<String> weaponNames = new ArrayList<>();

            for (Item item : otherActor.getItemInventory()) {
                if (item.hasCapability(Status.USABLE_WEAPON)) {
                    Weapon weapon = (Weapon) item;
                    String name = weapon.toString();

                    if (!weaponNames.contains(name)) {
                        actions.add(new AttackAction(this, direction, weapon));
                        weaponNames.add(name);
                    }
                }
            }
            List<String> spellNames = new ArrayList<>();
            for (Item item : otherActor.getItemInventory()) {
                if (item.hasCapability(Status.MAGIC_SPELL)) {
                    MagicItem spell = (MagicItem) item;
                    String name = spell.getName();
                    if (!spellNames.contains(name)) {
                        actions.add(new CastSpellAction(spell, this));
                        spellNames.add(name);
                    }
                }
            }
        }
        return actions;
    }
}
