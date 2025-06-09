package game.actions;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.weapons.Weapon;
import game.capabilities.Status;


import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for generating attack with weapons and spell actions.
 */
public class AttackUtils {
    /**
     * Select the correct weapon with a capability USABLE_WEAPON
     * and use that weapon to perform the AttackAction.
     *
     * @param otherActor the actor who attacks
     * @param actor the actor who is being attack
     * @param direction the direction which the attack is made
     * @param actions the ActionList where the AttackAction will be added
     */
    public static void addWeaponActions(Actor otherActor, Actor actor, String direction, ActionList actions) {
        List<String> weaponNames = new ArrayList<>();

        for (Item item : otherActor.getItemInventory()) {
            if (item.hasCapability(Status.USABLE_WEAPON)) {
                Weapon weapon = (Weapon) item;
                String name = weapon.toString();
                if (!weaponNames.contains(name)) {
                    actions.add(new AttackAction(actor, direction, weapon));
                    weaponNames.add(name);
                }
            }
        }
    }
    /**
     * Select the correct item with a capability MAGIC_SPELL
     * and use that item to perform the CastSpellAction.
     *
     * @param otherActor the actor who casts the spell
     * @param actor the actor who is being cast
     * @param actions the ActionList where CastSpellAction will be added
     */
    public static void addSpellActions(Actor otherActor, Actor actor, ActionList actions) {
        List<String> spellNames = new ArrayList<>();
        for (Item item : otherActor.getItemInventory()) {
            if (item.hasCapability(Status.MAGIC_SPELL)) {
                game.magic.MagicItem spell = (game.magic.MagicItem) item;
                String name = spell.getName();
                if (!spellNames.contains(name)) {
                    actions.add(new CastSpellAction(spell, actor));
                    spellNames.add(name);
                }
            }
        }
    }

}
