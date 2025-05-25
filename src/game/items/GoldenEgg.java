package game.items;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.Hatchable;
import game.actions.EatAction;
import game.actors.GoldenBeetle;
import game.actors.Player;
import game.capabilities.Status;


import static edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations.INCREASE;
import static edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes.STAMINA;

/**
 * Golden Egg Scaffold
 */
public class GoldenEgg extends Item implements Hatchable, Consumable {

    public GoldenEgg() {
        super("Golden Egg", '0', true);
        this.addCapability(Status.CURSED);

    }

    @Override
    public void tick(Location currentLocation) {
        if (canHatch(currentLocation) && !currentLocation.containsAnActor()) {
            hatch(currentLocation);
        }
    }

    /**
     * Checks whether its surrounding has entities that are cursed, so that
     * the Golden Egg can hatch.
     * @param currentLocation location to check hatching condition
     * @return true if can hatch, else false
     */
    @Override
    public boolean canHatch(Location currentLocation) {
        for (Exit exit : currentLocation.getExits()) {
            Location adjacent = exit.getDestination();
            if (adjacent.getGround().hasCapability(Status.CURSED) &&
                    !adjacent.containsAnActor()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Performs the hatching process.
     * @param currentLocation location where it hatches
     */
    @Override
    public void hatch(Location currentLocation) {
        currentLocation.removeItem(this);
        currentLocation.addActor(new GoldenBeetle());
    }

    /**
     * A Golden Egg that can be consumed to restore 20 stamina.
     *
     */

    @Override
    public void consume(Actor actor, GameMap map) {
        actor.removeItemFromInventory(this);
        actor.modifyAttribute(STAMINA, INCREASE, 20);
    }

    /**
     * Returns a list of actions that the actor can perform on the GoldenEgg.
     *
     * @param owner The actor who owns the item
     * @param map The game map
     * @return A list of allowable actions (only EatAction for now)
     */
    @Override
    public ActionList allowableActions(Actor owner, GameMap map) {
        ActionList actions = new ActionList();
        actions.add(new EatAction(this));
        return actions;
    }


}
