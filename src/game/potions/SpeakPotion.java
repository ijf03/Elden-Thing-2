package game.potions;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.StatusEffect;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Location;
import game.capabilities.Status;

/**
 * Class representing the Speak Potion.
 * @author Sher Tan
 */
public class SpeakPotion extends Item implements Throwable {
    private SpeakEffect speakEffect;

    /**
     * Constructor.
     */
    public SpeakPotion() {
        super("Speak Potion", 'p', true);
    }

    /**
     * Defines the action that occurs when the speak potion is thrown.
     * @param actor the player throwing the item
     * @param target the target impacted
     */
    @Override
    public void throwed(Actor actor, Actor target) {
        this.speakEffect = new SpeakEffect();
//        actor.removeItemFromInventory(this);
        target.addStatusEffect(getEffect());
        target.addCapability(Status.SPEAK);
    }

    /**
     * Retrieves the SpeakEffect that this potion might apply upon impact.
     * @return the Speak status effect
     */
    @Override
    public StatusEffect getEffect() {
        return this.speakEffect;
    }

    /**
     * Returns the list of actions that can be performed on this speak potion.
     * @param otherActor the other actor
     * @param location the location of the other actor
     * @return an unmodifiable list of Actions
     */
    @Override
    public ActionList allowableActions(Actor otherActor, Location location) {
        ActionList actions = super.allowableActions(otherActor, location);
        Actor target = location.getActor();
        if (target == otherActor) {
            actions.add(new ThrowAction(this, target));
        }
        return actions;
    }
}
