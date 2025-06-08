package game.potions;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.StatusEffect;
import edu.monash.fit2099.engine.positions.Location;
import game.Turns;
import game.actors.Speaker;
import game.capabilities.Status;


public class SpeakEffect extends StatusEffect {
    private Turns counter;

    public SpeakEffect() {
        super("Speak");
        this.counter = new Turns(5);
    }

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

    public boolean isActive() {
        return this.counter.getTurnCounter() > 0;
    }
}
