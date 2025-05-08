package game.terrain;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.Plantable;
import game.actors.Player;

/**
 * Class representing the Inheritree crop
 */
public class Inheritree extends Ground implements Plantable {

    private int tickCounter = 0;
    private PLANT_STATUS CROP_PLANT_STATUS;
    /**
     * Constructor
     * @param name
     */
    public Inheritree(String name) {
        super('t', name);
        this.CROP_PLANT_STATUS = PLANT_STATUS.PLANTED;
    }

    /**
     * When inheritree blooms it changes to 't' on the map
     */
    public void bloom() {
        // For when blooming is not instantaneous with being planted
        // this.CROP_STATUS = STATUS.PLANTED;
        this.CROP_PLANT_STATUS = PLANT_STATUS.BLOOMED;
    }

    /**
     * Create and return an action to plant this Item.
     * If this Item is not plantable (ie not a seed, and they picked up a crop), returns null.
     * When planted all cursed grounds are replaced with soil around it
     */
    public void PlantAction(Actor actor, GameMap map) {
        Location location = map.locationOf(actor);
        this.CROP_PLANT_STATUS = PLANT_STATUS.PLANTED;
        for (Exit exit : location.getExits()) {
            Location surroundLocation = exit.getDestination();
            for (CursedGrounds cursedGround : CursedGrounds.values()) {
                if (surroundLocation.getGround().getDisplayChar() == cursedGround.groundChar) {
                    surroundLocation.setGround(new Soil());
                }
            }
        }
        location.setGround(this);
    }

    /**
     * Upon planting, it sets cursed grounds nearby to soil and heals nearby actors for 5 on every turn after
     * @param location The location of the ground on which we lie.
     */
    public void tick(Location location) {
        if (this.canBloom()) {
            this.bloom();
        }
        else if (this.isBloomed()) {
            for (Exit exit : location.getExits()) {
                Location surroundLocation = exit.getDestination();
                if (surroundLocation.getActor() instanceof Player) { // TEMP FIX
                    surroundLocation.getActor().heal(5);
                    surroundLocation.getActor().modifyAttribute(BaseActorAttributes.STAMINA, ActorAttributeOperations.INCREASE, 5);
                }
            }
        }

        this.tickCounter++;
    }

    /**
     * Check if the inheritree can bloom, for now it is bloomed the moment its planted so at the 1st turn
     * @return true or false
     */
    public boolean canBloom() {
        return this.tickCounter >= 0;
    }

    /**
     * Check if the tree has already bloomed
     * @return
     */
    public boolean isBloomed() {
        return this.CROP_PLANT_STATUS == PLANT_STATUS.BLOOMED;
    }
}
