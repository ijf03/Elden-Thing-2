package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.Reproducible;

public class ReproduceAction extends Action {
    private final Reproducible reproducer;

    public ReproduceAction(Reproducible reproducer) {
        this.reproducer = reproducer;
    }


    @Override
    public String execute(Actor actor, GameMap map) {
        if (reproducer.canReproduce(map)) {
            reproducer.reproduce(map);
            return this.menuDescription(actor);
        }
        return "";
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " reproduced!";
    }
}
