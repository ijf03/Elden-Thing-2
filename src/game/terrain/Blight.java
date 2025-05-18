package game.terrain;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.CureAction;
import game.Curable;
import game.capabilities.Status;

import static edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations.DECREASE;
import static edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes.STAMINA;

/**
 * A class representing a blight covering the ground of the valley.
 * @author Adrian Kristanto
 */
public class Blight extends Ground implements Curable {
    /**
     * Constructor
     * Blight is a cursed entity
     */
    public Blight() {
        super('x', "Blight");
        this.addCapability(Status.CURSED);
    }

    /**
     * Logic to cure blight
     * @param curer is an actor that is doing the curing
     * @param location where the actor is doing the curing
     */
    @Override
    public void cure(Actor curer, Location location) {
        location.setGround(new Soil());
        curer.modifyAttribute(STAMINA, DECREASE, 50);
    }

    /**
     * Enables actors to cure when they are around blight
     * @param actor     the Actor acting
     * @param location  the current Location
     * @param direction the direction of the Ground from the Actor
     * @return action   the actor can take with respect to the ground
     */
    @Override
    public ActionList allowableActions(Actor actor, Location location, String direction) {
        ActionList actions = super.allowableActions(actor, location, direction);
        if (actor.hasCapability(Status.CURE)) {
            actions.add(new CureAction(this, location, direction));
        }
        return actions;
    }
}
