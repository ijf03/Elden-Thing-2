package game.items;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.MoveActorAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;

import java.util.ArrayList;

/**
 * Class for Teleportation Circle that players can use to move instantaneously to another circle
 */
public class TeleportationCircle extends Item {

    /**
     * A list of all the possible destinations this can lead to
     */
    ArrayList<Location> destinations = new ArrayList<>();

    /**
     * Constructor.
     */
    public TeleportationCircle() {
        super("Teleportation circle", 'A', false);
    }

    /**
     * Add destination to the list
     * @param destination that you can end up in
     */
    public void addDestination(Location destination) {
        destinations.add(destination);
    }

    /**
     * Getter for destinations
     * @return List of locations
     */
    public ArrayList<Location> teleportDestination() {
        return destinations;
    }

    /**
     * When this item on the ground is encountered, it enables the actor to use it to move to another destination
     * @param location the location of the ground on which the item lies
     * @return ActionList that is newly available to the player
     */
    @Override
    public ActionList allowableActions(Location location) {
        ActionList actions = super.allowableActions(location);
        for (Location destination : destinations) {
            actions.add(new MoveActorAction(destination, "to " + destination.map()));
        }
        return actions;
    }
}
