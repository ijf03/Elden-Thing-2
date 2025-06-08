package game.potions;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.StatusEffect;
import edu.monash.fit2099.engine.positions.Location;
import game.Turns;
import game.capabilities.Status;

public class InvincibleEffect extends StatusEffect {
    private Turns counter;
    private boolean isActive;

    public InvincibleEffect() {
        super("Invincible");
        this.counter = new Turns(5);
        this.isActive = true;
    }

    @Override
    public void tick(Location location, Actor actor) {
        counter.decreaseCounter();

        if (!isActive()) {
            actor.removeStatusEffect(this);
            actor.removeCapability(Status.INVINCIBLE);
        } else {
            System.out.println(actor + " is Invincible for " + this.counter.getTurnCounter());
        }
    }

    public boolean isActive() {
        return this.counter.getTurnCounter() > 0;
    }
}
