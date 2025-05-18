package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.items.Consumable;

/**
 * Class representing the Eat Action.
 * @author Sher Tan
 */
public class EatAction extends Action {
    private Consumable consumable;

    /**
     * Constructor
     * @param consumable the entity to be consumed
     */
    public EatAction(Consumable consumable) {
        this.consumable = consumable;
    }

    // Getters & Setters
    public Consumable getConsumable() {
        return consumable;
    }

    public void setConsumable(Consumable consumable) {
        this.consumable = consumable;
    }

    /**
     * Performs the Eat Action.
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        getConsumable().consume(actor);

        if (getConsumable() instanceof Item) {
            actor.removeItemFromInventory((Item) getConsumable());
        } else {
            map.removeActor((Actor) getConsumable());
        }

        return actor + " ate " + getConsumable();
    }

    /**
     * Describe the eat action will be performed if this Action is chosen in the menu.
     * @param actor The actor performing the action.
     * @return
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " eats " + getConsumable();
    }
}
