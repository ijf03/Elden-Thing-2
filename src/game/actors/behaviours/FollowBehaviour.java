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
 */
public class FollowBehaviour implements Behaviour {

    private Actor target = null;

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

        return null;
    }

    private Action moveToTarget(Actor actor, GameMap map) {
        Location beetleLoc = map.locationOf(actor);
        Location targetLoc = map.locationOf(target);

        Map<Exit, Integer> distances = new HashMap<>();
        for (Exit exit : beetleLoc.getExits()) {
            Location destination = exit.getDestination();
            int dx = Math.abs(destination.x() - targetLoc.x());
            int dy = Math.abs(destination.y() - targetLoc.y());
            distances.put(exit, dx + dy);
        }

        return distances.entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .map(entry -> new MoveActorAction(entry.getKey().getDestination(), entry.getKey().getName()))
                .orElse(null);
    }
}
