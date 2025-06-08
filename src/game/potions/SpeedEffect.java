package game.potions;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.StatusEffect;
import edu.monash.fit2099.engine.positions.Location;
import game.Turns;
import game.capabilities.Status;

public class SpeedEffect extends StatusEffect {
    private final Turns counter;

    public SpeedEffect() {
        super("Speed");
        this.counter = new Turns(5);
    }

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

    public boolean isActive() {
        return this.counter.getTurnCounter() > 0;
    }
}
