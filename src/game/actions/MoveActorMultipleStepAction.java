package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class representing the Move Multiple Steps Action.
 * Allows actors to move more than 1 step per turn.
 */
public class MoveActorMultipleStepAction extends Action {
    private int steps;
    private Random random = new Random();

    /**
     * Constructor.
     * @param steps the number of steps to move
     */
    public MoveActorMultipleStepAction(int steps) {
        this.steps = steps;
    }

    /**
     * Performs the Move Action.
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return a description of the movements of the actor.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < steps; i++) {
            ArrayList<Exit> exits = new ArrayList<>();
            for (Exit exit : map.locationOf(actor).getExits()) {
                Location destination = exit.getDestination();
                if (destination.canActorEnter(actor)) {
                    exits.add(exit);
                }
            }

            if (!exits.isEmpty()) {
                Exit location = exits.get(random.nextInt(exits.size()));
                location.getDestination().getMoveAction(actor, "around", location.getHotKey()).execute(actor, map);
                result.append(actor).append(" moved around ").append(i+1).append(" steps").append(i+1 == steps ? "" : "\n");
            } else {
                // no valid exits to move
                result.append("Can't move");
            }
        }
        return result.toString();
    }

    /**
     * Describe the movement of the actor when this action is performed.
     * @param actor The actor performing the action
     * @return the movement description to be displayed
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " moves " + steps + " steps";
    }
}
