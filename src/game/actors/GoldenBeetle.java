package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actors.behaviours.WanderBehaviour;

import java.util.HashMap;
import java.util.Map;

/**
 * GoldenBeetle with wandering behaviour
 */
public class GoldenBeetle extends Actor {

    /**
     * Behaviour map
     */
    private final Map<Integer, Behaviour> behaviours = new HashMap<>();

    public GoldenBeetle() {
        super("Golden Beetle", 'b', 25);
        behaviours.put(999, new WanderBehaviour());
    }

    /**
     * At each turn select a valid action to perform
     *
     */

    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        for (Behaviour behaviour : behaviours.values()) {
            Action action = behaviour.getAction(this, map);
            if (action != null) {
                return action;
            }
        }
        return new DoNothingAction();
    }
}
