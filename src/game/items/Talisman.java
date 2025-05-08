package game.items;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.CureAction;
import game.actors.Curable;
import game.capabilities.Status;


/**
 * A class representing a Talisman that an actor can pick up and drop
 * @author Adrian Kristanto
 */
public class Talisman extends Item {
    public Talisman() {
        super("Talisman", 'o', true);
        addCapability(Status.CURE);
    }
}
