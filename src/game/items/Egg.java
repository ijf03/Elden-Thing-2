package game.items;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.Hatchable;
import game.Turns;
import game.actions.EatAction;
import game.actors.OmenSheep;

/**
 * Class representing the Egg of OmenSheep.
 * @author Sher Tan
 */
public class Egg extends Item implements Hatchable, Consumable {
    private Turns hatchCounter;
    private int maxHealthBoost;

    /***
     * Constructor.
     */
    public Egg(int maxHealthBoost) {
        super("Egg", '0', true);
        this.maxHealthBoost = maxHealthBoost;
        this.hatchCounter = new Turns(3);
//        this.addCapability(Status.INCREASE_MAX_HEALTH);
    }

    // Getters and Setters
    public int getMaxHealthBoost() {
        return maxHealthBoost;
    }

    public void setMaxHealthBoost(int maxHealthBoost) {
        this.maxHealthBoost = maxHealthBoost;
    }

    /**
     * Inform the egg on the ground of the passage of time.
     * This method decreases the hatch counter and hatches the egg when the condition is met.
     * @param currentLocation The location of the ground on which it lies.
     */
    @Override
    public void tick(Location currentLocation) {
        hatchCounter.decreaseCounter();
//        System.out.println("egg" + hatchCounter.getTurnCounter());
        if (canHatch(currentLocation) && !currentLocation.containsAnActor()) {
            hatch(currentLocation);
        }
    }

    /**
     * Checks if the egg can hatch.
     * @param currentLocation location to check hatching condition
     * @return true if can hatch, else false
     */
    @Override
    public boolean canHatch(Location currentLocation) {
        return hatchCounter.getTurnCounter() <= 0;
    }

    /**
     * Performs the hatching process.
     * @param location location where it hatches
     */
    @Override
    public void hatch(Location location) {
        location.removeItem(this);
        location.addActor(new OmenSheep());
    }

    /**
     * Defines what happens when an actor consumes this egg.
     * @param actor the actor consuming this item
     */
    @Override
    public void consume(Actor actor, GameMap map) {
        actor.removeItemFromInventory(this);
        actor.modifyAttributeMaximum(BaseActorAttributes.HEALTH, ActorAttributeOperations.INCREASE, getMaxHealthBoost());
//        if (this.hasCapability(Status.INCREASE_MAX_HEALTH)) {
//            actor.modifyAttributeMaximum(BaseActorAttributes.HEALTH, ActorAttributeOperations.INCREASE, getMaxHealthBoost());
//        }
    }

    /**
     * Returns the list of actions that can be performed on this egg.
     * @param owner the actor that owns the item
     * @param map the map where the actor is performing the action on
     * @return
     */
    @Override
    public ActionList allowableActions(Actor owner, GameMap map) {
        ActionList actions = super.allowableActions(owner, map);
        actions.add(new EatAction(this));
        return actions;
    }
}
