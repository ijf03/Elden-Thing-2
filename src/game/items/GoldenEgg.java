package game.items;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actors.GoldenBeetle;
import game.capabilities.Status;
import game.terrain.CursedGrounds;

import static edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations.INCREASE;
import static edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes.STAMINA;

/**
 * Golden Egg Scaffold
 */
public class GoldenEgg extends Item implements Consumable{

    private boolean canHatch = true;

    public GoldenEgg() {
        super("Golden Egg", '0', false);
    }

    @Override
    public void tick(Location currentLocation) {
        if (!canHatch) return;

        for (Exit exit : currentLocation.getExits()) {
            Location adjacent = exit.getDestination();
            if (adjacent.getGround().getDisplayChar() == CursedGrounds.BLIGHT.groundChar) {
                currentLocation.removeItem(this);
                currentLocation.addActor(new GoldenBeetle());
                break;
            }
        }
    }

    @Override
    public void tick(Location currentLocation, Actor actor) {
        this.canHatch = false;
    }

    @Override
    public void consume(Actor actor) {
        actor.modifyAttribute(STAMINA, INCREASE, 20);
    }

}
