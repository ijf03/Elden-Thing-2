package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.items.Item;
import game.items.Consumable;

/**
 * Action that allows player to consume a consumable item or actor
 */
public class ConsumeAction extends Action {

    private final Consumable target;
    private final String description;

    /**
     * Constructor
     */
    public ConsumeAction(Consumable target, String description) {
        this.target = target;
        this.description = description;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        target.consume(actor);
        if (target instanceof Item) {
            actor.removeItemFromInventory((Item) target);
        } else {
            map.removeActor((Actor) target);
        }
        return actor + " consumes " + description;
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " consumes " + description;
    }
}
