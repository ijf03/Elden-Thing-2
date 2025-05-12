package game.items;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.Consumable;
import game.Hatchable;
import game.actions.EatAction;
import game.actions.PlantAction;
import game.actors.OmenSheep;
import game.capabilities.Status;

/**
 *
 */
public class Egg extends Item implements Hatchable, Consumable {
    private int hatchCounter = 3;
    private int maxHealthBoost;

    /***
     * Constructor.
     */
    public Egg(int maxHealthBoost) {
        super("Egg", '0', true);
        this.maxHealthBoost = maxHealthBoost;
//        this.addCapability(Status.INCREASE_MAX_HEALTH);
    }

    // Getters and Setters
    public int getHatchCounter() {
        return hatchCounter;
    }

    public int getMaxHealthBoost() {
        return maxHealthBoost;
    }

    public void setHatchCounter(int reproduceCounter) {
        this.hatchCounter = reproduceCounter;
    }

    public void setMaxHealthBoost(int maxHealthBoost) {
        this.maxHealthBoost = maxHealthBoost;
    }

    /**
     * Decreases counter by 1.
     */
    public void decreaseCounter() {
        setHatchCounter(getHatchCounter()-1);
    }

    @Override
    public void tick(Location currentLocation) {
        decreaseCounter();
        if (!currentLocation.containsAnActor()) {
            hatch(currentLocation);
        }
    }

    @Override
    public boolean canHatch() {
        return getHatchCounter() <= 0;
    }

    @Override
    public void hatch(Location location) {
        if (canHatch()) {
            location.removeItem(this);
            OmenSheep offspring = new OmenSheep();
            location.addActor(offspring);
        }
    }

    @Override
    public void consumedEffect(Actor actor) {
        actor.removeItemFromInventory(this);
        actor.modifyAttributeMaximum(BaseActorAttributes.HEALTH, ActorAttributeOperations.INCREASE, getMaxHealthBoost());
//        if (this.hasCapability(Status.INCREASE_MAX_HEALTH)) {
//            actor.modifyAttributeMaximum(BaseActorAttributes.HEALTH, ActorAttributeOperations.INCREASE, getMaxHealthBoost());
//        }
    }

    @Override
    public ActionList allowableActions(Actor owner, GameMap map) {
        ActionList actions = super.allowableActions(owner, map);
        actions.add(new EatAction(this));
        return actions;
    }
}
