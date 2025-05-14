package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.LayEggAction;
import game.actors.behaviours.WanderBehaviour;
import game.actors.behaviours.NPCController;
import game.actors.behaviours.StandardNPCController;
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
    private final NPCController npcController;
    private int turnCounter;

    public GoldenBeetle() {
        super("Golden Beetle", 'b', 25);
        behaviours.put(999, new WanderBehaviour());
        this.npcController = new StandardNPCController();
    }

    /**
     * At each turn select a valid action to perform
     * lays egg every 5 turns
     */

    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        turnCounter++;
        if (turnCounter % 5 == 0) {
            return new LayEggAction();
        }
        Action action = npcController.retrieveNextAction(this, map);
        return action != null ? action : new DoNothingAction();
    }

}
