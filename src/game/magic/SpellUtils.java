package game.magic;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.actors.Actor;
import game.capabilities.Status;
import game.actions.CastSpellAction;

import java.util.HashSet;
import java.util.Set;

/**
 * adds CastSpellActions to an ActionList when an actor holds Magic items
 */
public final class SpellUtils {

    private SpellUtils() {}

    public static void addCastSpellActions(ActionList actions, Actor otherActor, Actor thisActor) {
        Set<String> seen = new HashSet<>();
        for (Item item : otherActor.getItemInventory()) {
            if (item.hasCapability(Status.MAGIC_SPELL) && item instanceof Spell) {
                Spell spell = (Spell) item;
                String name = item.toString();
                if (seen.add(name)) {
                    actions.add(new CastSpellAction(spell, thisActor));
                }
            }
        }
    }
}
