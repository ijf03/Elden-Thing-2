package game.potions;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.StatusEffect;
import edu.monash.fit2099.engine.positions.Location;
import game.Turns;
import game.capabilities.Status;

/**
 * Class representing the Speak Status Effect.
 * @author Sher Tan
 */
public class SpeakEffect extends StatusEffect {
    private Turns counter;

    /**
     * Constructor.
     */
    public SpeakEffect() {
        super("Speak");
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

        if (!isActive()) {
            actor.removeStatusEffect(this);
            actor.removeCapability(Status.SPEAK);
        } else {
            System.out.println(actor + " can speak for " + this.counter.getTurnCounter());
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
