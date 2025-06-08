package game.actions;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.weapons.Weapon;
import game.capabilities.Status;
import game.weapons.magic.MagicItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for generating attack and spell actions.
 */
public class AttackUtils {

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

    public static void addSpellActions(Actor otherActor, Actor actor, ActionList actions) {
        List<String> spellNames = new ArrayList<>();
        for (Item item : otherActor.getItemInventory()) {
            if (item.hasCapability(Status.MAGIC_SPELL)) {
                MagicItem spell = (MagicItem) item;
                String name = spell.getName();
                if (!spellNames.contains(name)) {
                    actions.add(new CastSpellAction(spell, actor));
                    spellNames.add(name);
                }
            }
        }
    }

}
