package game.items.potions;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.StatusEffect;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.ThrowAction;
import game.capabilities.Status;
import game.items.Throwable;

/**
 * Class representing the Speed Potion.
 * @author Sher Tan
 */
public class SpeedPotion extends Item implements Throwable {
    private SpeedEffect speedEffect;

    /**
     * Constructor.
     */
    public SpeedPotion() {
        super("Speed Potion", 'p', true);
    }

    /**
     * Defines the action that occurs when the speed potion is thrown.
     * @param actor the player throwing the item
     * @param target the target impacted
     */
    @Override
    public void throwed(Actor actor, Actor target) {
        this.speedEffect = new SpeedEffect();
        actor.removeItemFromInventory(this);
        target.addStatusEffect(getEffect());
        target.addCapability(Status.SPEED);
    }
    /**
     * Retrieves the SpeedEffect that this potion might apply upon impact.
     * @return the Speed status effect
     */

    @Override
    public StatusEffect getEffect() {
        return this.speedEffect;
    }

    /**
     * Returns the list of actions that can be performed on this speed potion.
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
