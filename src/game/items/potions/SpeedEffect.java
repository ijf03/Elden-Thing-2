package game.items.potions;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.StatusEffect;
import edu.monash.fit2099.engine.positions.Location;
import game.Turns;
import game.capabilities.Status;

/**
 * Class representing the Speed Status Effect.
 * @author Sher Tan
 */
public class SpeedEffect extends StatusEffect {
    private final Turns counter;

    /**
     * Constructor.
     */
    public SpeedEffect() {
        super("Speed");
        this.counter = new Turns(5);
    }

    /**
     * Inform a status effect of the passage of time.
     * @param location the location where the actor with the status effect is currently standing
     * @param actor the actor holding the status effect
     */
    @Override
    public void tick(Location location, Actor actor) {
        counter.decreaseCounter();
        System.out.println("speed counter" + this.counter.getTurnCounter());
        if (!isActive()) {
            actor.removeStatusEffect(this);
            actor.removeCapability(Status.SPEED);
        } else {
            System.out.println(actor + " has speed boost for " + this.counter.getTurnCounter());
        }
    }

    /**
     * Condition checker to see if the potion is still effective.
     * @return true if condition is met, otherwise false
     */
    public boolean isActive() {
        return this.counter.getTurnCounter() > 0;
    }
}
