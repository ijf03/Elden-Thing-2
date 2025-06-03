package game.items;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.MoveActorAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;

import java.util.ArrayList;

public class TeleportationCircle extends Item {

    ArrayList<Location> destinations = new ArrayList<>();
    /**
     * Constructor.
     *
     */
    public TeleportationCircle() {
        super("Teleportation circle", 'A', false);
    }

    public void addDestination(Location destination) {
        destinations.add(destination);
    }

    public ArrayList<Location> teleportDestination() {
        return destinations;
    }


    @Override
    public ActionList allowableActions(Location location) {
        ActionList actions = super.allowableActions(location);
        for (Location destination : destinations) {
            actions.add(new MoveActorAction(destination, "to " + destination.map()));
        }
        return actions;
    }
}
