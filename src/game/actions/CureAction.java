package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actors.Curable;

import java.util.Objects;

/**
 * Class representing the actor curing
 * an actor can cure anything around them as long as they are deemed curable (such as SpiritGoat)
 */
public class CureAction extends Action {
    /**
     * The target the actor is curing
     */
    private Curable target;

    private Location location;

    /**
     * The direction of the target from the curing actor
     */
    private String direction;

    /**
     * Constructor
     * @param target
     * @param direction
     */
    public CureAction(Curable target, Location location, String direction) {
        this.target = target;
        this.location = location;
        this.direction = direction;
    }

    @Override
    public String execute(Actor curer, GameMap map) {
        if (curer.getAttribute(BaseActorAttributes.STAMINA) > 0) {
            target.cure(curer, location);
            return menuDescription(curer);
        }
        return curer + " does not have enough stamina to complete this action";
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " cures " + target + " at " + direction;
    }
}