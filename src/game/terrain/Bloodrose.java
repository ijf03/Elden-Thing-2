package game.terrain;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.Plantable;

/**
 * Class representing the Bloodrose crop
 */
public class Bloodrose extends Ground implements Plantable {

    private int tickCounter = 0;
    private PLANT_STATUS CROP_PLANT_STATUS;
    /**
     * Constructor
     * @param name
     */
    // begins as a seed in farmer's inventory
    public Bloodrose(String name) {
        super('w', name);
        this.CROP_PLANT_STATUS = PLANT_STATUS.PLANTED;
    }

    /**
     * When a crop blooms it will change character and update status to bloomed
     * Called in tick after a certain amount of turns (immediate for now)
     */
    public void bloom() {
        // For when blooming is not instantaneous with being planted
        // this.CROP_STATUS = STATUS.PLANTED;
        this.CROP_PLANT_STATUS = PLANT_STATUS.BLOOMED;
    }

    /**
     * Create and return an action to plant this Item.
     * If this Item is not plantable (ie not a seed, and they picked up a crop), returns null.
     * @return a new DropItemAction if this Item is portable, null otherwise.
     */
    public void PlantAction(Actor actor, GameMap map) {
        this.CROP_PLANT_STATUS = PLANT_STATUS.PLANTED;
        actor.modifyAttribute(BaseActorAttributes.STAMINA, ActorAttributeOperations.DECREASE, 75);
        actor.hurt(5);
        map.locationOf(actor).setGround(this);
    }

    /**
     * BloodRose hurts the actor that planted it for 5 immediately and for 10 for following turns if they are in nearby
     * @param location The location of the ground on which we lie.
     */
    // public since abstract method in item is public
    public void tick(Location location) {
        if (this.canBloom()) { // Can change the if condition to reflect blooming
            this.bloom();
        }
        if (isBloomed()) {
            for (Exit exit : location.getExits()) {
                Location surroundLocation = exit.getDestination();
                if (surroundLocation.getActor() != null) {
                    surroundLocation.getActor().hurt(10);
                }
            }
        }
        this.tickCounter++;
    }

    /**
     * Check if the bloodrose can bloom, for now it is bloomed the moment its planted so at the 1st turn
     * @return true or false
     */
    public boolean canBloom() {
        return this.tickCounter >= 0;
    }

    /**
     * Check if the rose has already bloomed
     * @return
     */
    public boolean isBloomed() {
        return this.CROP_PLANT_STATUS == PLANT_STATUS.BLOOMED;
    }
}
