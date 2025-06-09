package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.items.Consumable;

/**
 * Class representing the Consume Action.
 * @author Sher Tan
 */
public class ConsumeAction extends Action {
    private Consumable consumable;

    /**
     * Constructor
     * @param consumable the entity to be consumed
     */
    public ConsumeAction(Consumable consumable) {
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
     * Performs the Consume Action.
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return a description of what consumable item was consumed.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        getConsumable().consume(actor,map);

        return actor + " ate " + getConsumable();
    }

    /**
     * Describe the consume action will be performed if this Action is chosen in the menu.
     * @param actor The actor performing the action.
     * @return the consume action description to be displayed on the menu
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " consumes " + getConsumable();
    }
}
