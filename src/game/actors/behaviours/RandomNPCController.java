package game.actors.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.positions.GameMap;

import java.util.ArrayList;
import java.util.Random;

/**
 * NPC Controller that chooses behaviour randomly
 */
public class RandomNPCController implements NPCController {

    /**
     * Initialising random object
     */
    Random random = new Random();
    /**
     * A Hashmap storing all the behaviours
     */
    ArrayList<Behaviour> behaviours = new ArrayList<>();

    /**
     * Next action will be given based off random selection of behaiour
     * @param actor actor that is taking the action
     * @param map where the actor is
     * @return Action available to the NPC
     */
    @Override
    public Action retrieveNextAction(Actor actor, GameMap map) {
        Behaviour behaviour =  behaviours.get(random.nextInt(behaviours.size()));
        Action chosenAction = behaviour.getAction(actor, map);
        if (chosenAction != null) {
            return chosenAction;
        }
        return new DoNothingAction();
    }

    /**
     * Add the behaviour to the pool regardless of priority
     * @param behaviour to add to the behaviours pool the controller can choose from
     * @param priority of the behaviour (if any)
     */
    @Override
    public void newBehaviour(Behaviour behaviour, Integer priority) {
        behaviours.add(behaviour);
    }
}
