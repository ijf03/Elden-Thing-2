package game.actors.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.positions.GameMap;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * This contains the standard behaviours of NPCs
 * This is used by OmenSheep and SpiritGoat who, as of now, just wander around but more behaviours can be added later
 */
public class StandardNPCController implements NPCController {
    /**
     * A Hashmap storing all the behaviours
     */
    private Map<Integer, Behaviour> behaviours = new TreeMap<>();

    @Override
    public void newBehaviour(Behaviour behaviour, Integer priority) {
        this.behaviours.put(priority, behaviour);
    }

    /**
     * Based on the behaviours present, get the suitable next action
     * @param actor actor that is taking the action
     * @param map where the actor is
     * @return
     */
    @Override
    public Action retrieveNextAction(Actor actor, GameMap map) {
        for (Behaviour behaviour : behaviours.values()) {
            Action action = behaviour.getAction(actor, map);
            if (action != null)
                return action;
        }
        return new DoNothingAction();
    }
}
