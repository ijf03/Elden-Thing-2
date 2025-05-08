package game.terrain;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.CureAction;
import game.actors.Curable;
import game.capabilities.Status;
import game.items.Talisman;

import static edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations.DECREASE;
import static edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes.STAMINA;

/**
 * A class representing a blight covering the ground of the valley.
 * @author Adrian Kristanto
 */
public class Blight extends Ground implements Curable {
    public Blight() {
        super('x', "Blight");
    }

    @Override
    public void cure(Actor curer, Location location) {
        location.setGround(new Soil());
        curer.modifyAttribute(STAMINA, DECREASE, 50);
    }

    @Override
    public ActionList allowableActions(Actor actor, Location location, String direction) {
        ActionList actions = super.allowableActions(actor, location, direction);
        if (actor.hasCapability(Status.CURE)) {
            actions.add(new CureAction(this, location, direction));
        }
        return actions;
    }
}
