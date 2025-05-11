package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.Consumable;
import game.capabilities.Status;

public class EatAction extends Action {
//    private Item item;
    private Consumable consumable;
    private int maxHealthBoost;

    public EatAction(Consumable consumable, int maxHealthBoost) {
        this.consumable = consumable;
        this.maxHealthBoost = maxHealthBoost;
    }

    // Getters & Setters
    public Consumable getConsumable() {
        return consumable;
    }

    public void setConsumable(Consumable consumable) {
        this.consumable = consumable;
    }

    public int getMaxHealthBoost() {
        return maxHealthBoost;
    }

    public void setMaxHealthBoost(int maxHealthBoost) {
        this.maxHealthBoost = maxHealthBoost;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        getConsumable().consumedEffect(actor);
        return actor + "ate " + getConsumable();
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " eats " + getConsumable();
    }
}
