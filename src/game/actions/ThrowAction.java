package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.items.Throwable;

/**
 * Class representing the Throw Action.
 * @author Sher Tan
 */
public class ThrowAction extends Action {
    private Throwable throwable;
    private Actor target;

    /**
     * Constructor.
     * @param throwable the item to be thrown
     * @param target the target to throw onto
     */
    public ThrowAction(Throwable throwable, Actor target) {
        this.throwable = throwable;
        this.target = target;
    }

    // Getters & Setters
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

    /**
     * Performs the Throw Action.
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return a description of what throwable item was thrown.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        this.getThrowable().throwed(actor, getTarget());
        return getThrowable() + " thrown!";
    }

    /**
     * Describe the throw action will be performed if this Action is chosen in the menu.
     * @param actor The actor performing the action.
     * @return the throw action description to be displayed on the menu
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " throws " + getThrowable() + " at " + target;
    }
}
