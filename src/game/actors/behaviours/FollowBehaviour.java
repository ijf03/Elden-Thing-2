package game.actors.behaviours;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.MoveActorAction;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.capabilities.Status;

import java.util.HashMap;
import java.util.Map;

/**
 * allows an actor to follow the first followable actor it finds.
 *
 * Reference: edu.monash.fit2099.demo.mars.behaviours
 */
public class FollowBehaviour implements Behaviour {
    /** The current actor being followed. */
    private Actor target = null;


    /**
     * Returns the action that moves the actor toward a followable target.
     *
     * @param actor The actor executing the behaviour
     * @param map The game map
     * @return A MoveActorAction if a followable target is nearby or already being followed; null otherwise
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        Location here = map.locationOf(actor);

        // If already has a target and it's still on the map, keep following it
        if (target != null && map.contains(target)) {
            return moveToTarget(actor, map);
        }

        // Otherwise, scan surroundings for a new target
        for (Exit exit : here.getExits()) {
            Location destination = exit.getDestination();
            if (destination.containsAnActor()) {
                Actor other = destination.getActor();
                if (other.hasCapability(Status.FOLLOWABLE)) {
                    target = other;
                    return moveToTarget(actor, map);
                }
            }
        }
        // No followable actors found
        return null;
    }

    /**
     * Generates a MoveActorAction to move toward the current target actor.
     * Chooses the neighboring tile with the shortest distance to the target.
     *
     * @param actor The actor trying to follow the target
     * @param map The game map
     * @return A MoveActorAction toward the best adjacent location, or null if not possible
     */
    private Action moveToTarget(Actor actor, GameMap map) {
        Location beetleLoc = map.locationOf(actor);
        Location targetLoc = map.locationOf(target);

        // Calculates the distance to the target
        Map<Exit, Integer> distances = new HashMap<>();
        for (Exit exit : beetleLoc.getExits()) {
            Location destination = exit.getDestination();
            int dx = Math.abs(destination.x() - targetLoc.x());
            int dy = Math.abs(destination.y() - targetLoc.y());
            distances.put(exit, dx + dy);
        }

        // Choose the exit with the smallest distance to the target
        return distances.entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .map(entry -> new MoveActorAction(entry.getKey().getDestination(), entry.getKey().getName()))
                .orElse(null);
    }
}
