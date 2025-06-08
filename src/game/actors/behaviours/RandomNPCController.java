package game.actors.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.positions.GameMap;

import java.util.ArrayList;
import java.util.Random;

public class RandomNPCController implements NPCController {

    Random random = new Random();
    /**
     * A Hashmap storing all the behaviours
     */
    ArrayList<Behaviour> behaviours = new ArrayList<>();
    @Override
    public Action retrieveNextAction(Actor actor, GameMap map) {
        Behaviour behaviour =  behaviours.get(random.nextInt(behaviours.size()));
        Action chosenAction = behaviour.getAction(actor, map);
        if (chosenAction != null) {
            return chosenAction;
        }
        return new DoNothingAction();
    }

    @Override
    public void newBehaviour(Behaviour behaviour, Integer priority) {
        behaviours.add(behaviour);
    }
}
