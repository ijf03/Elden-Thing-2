package game.items;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.ConsumeAction;
import game.actors.GoldenBeetle;
import game.actors.Player;
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
        super("Golden Egg", '0', true);
        this.addCapability(Status.CURSED);

    }

    @Override
    public void tick(Location currentLocation) {
        if (!canHatch) return;

        for (Exit exit : currentLocation.getExits()) {
            Location adjacent = exit.getDestination();
            if (adjacent.getGround().hasCapability(Status.CURSED) &&
                    !adjacent.containsAnActor()) {

                currentLocation.removeItem(this);
                adjacent.addActor(new GoldenBeetle());
                return;
            }
        }
    }

    @Override
    public void consume(Actor actor) {
        actor.modifyAttribute(STAMINA, INCREASE, 20);
    }

    @Override
    public ActionList allowableActions(Actor owner, GameMap map) {
        ActionList actions = new ActionList();
        actions.add(new ConsumeAction(this, "Golden Egg"));
        return actions;
    }


}
