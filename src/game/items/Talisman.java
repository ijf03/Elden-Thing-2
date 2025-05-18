package game.items;

import edu.monash.fit2099.engine.items.Item;
import game.capabilities.Status;


/**
 * A class representing a Talisman that an actor can pick up and drop
 * @author Adrian Kristanto
 */
public class Talisman extends Item {
    /**
     * Constructor
     * Talismans have the ability to cure
     */
    public Talisman() {
        super("Talisman", 'o', true);
        addCapability(Status.CURE);
    }
}
