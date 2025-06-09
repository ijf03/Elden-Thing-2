package game.actors.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.positions.GameMap;
import game.Reproducible;
import game.Turns;
import game.actions.ReproduceAction;

/**
 * Behaviour for reproducing
 * @author Trinh N Nguyen
 */
public class ReproduceBehaviour implements Behaviour {
    /**
     * The actor who is doing the producing
     */
    private Reproducible reproducer;
    /**
     * A timer to keep count of how many turns have gone
     */
    private Turns turnCounter;

    /**
     * Constructor
     * @param reproducer actor who is reproducing
     */
    public ReproduceBehaviour(Reproducible reproducer) {
        this.reproducer = reproducer;
    }

    /**
     * If the actor can reproduce, return their reproduce action
     * @param actor the Actor producing
     * @param map the GameMap containing the Actor
     * @return ReproduceAction
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        if (reproducer.canReproduce(map)) {
            return new ReproduceAction(reproducer);
        }
        return null;
    }
}
