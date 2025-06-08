package game.potions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

public class ThrowAction extends Action {
    private Throwable throwable;
    private Actor target;

    public ThrowAction(Throwable throwable, Actor target) {
        this.throwable = throwable;
        this.target = target;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public Actor getTarget() {
        return target;
    }

    public void setTarget(Actor target) {
        this.target = target;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        this.getThrowable().throwed(actor, getTarget());
        return getThrowable() + " thrown!";
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " throws " + getThrowable() + " at " + target;
    }
}
