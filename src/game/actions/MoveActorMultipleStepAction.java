package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;

import java.util.Random;

public class MoveActorMultipleStepAction extends Action {
    private int steps;
    private Random random = new Random();

    public MoveActorMultipleStepAction(int steps) {
        this.steps = steps;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < steps; i++) {
            boolean moved = false;
            for (Exit exit : map.locationOf(actor).getExits()) {
                Location destination = exit.getDestination();
                if (destination.canActorEnter(actor)) {
                    exit.getDestination().getMoveAction(actor, "around", exit.getHotKey()).execute(actor, map);
                    result.append(actor).append(" moved around ").append(i+1).append(" steps").append("\n");
                    moved = true;
                    break;
                }
            }

            if (!moved) {
                result.append("Can't move");
            }
        }
        return result.toString();
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " moves " + steps + " steps";
    }
}
