package game.actors.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.positions.GameMap;
import game.Reproducible;
import game.Turns;
import game.actions.ReproduceAction;

public class ReproduceBehaviour implements Behaviour {
    private Reproducible reproducer;
    /**
     * A timer to keep count of how many turns have gone
     */
    private Turns turnCounter;

    public ReproduceBehaviour(Reproducible reproducer) {
        this.reproducer = reproducer;
    }

    @Override
    public Action getAction(Actor actor, GameMap map) {
        if (reproducer.canReproduce(map)) {
            return new ReproduceAction(reproducer);
        }
        return null;
    }
}
