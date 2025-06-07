package game.magic.effects;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.StatusEffect;
import edu.monash.fit2099.engine.positions.Location;

public class Frozen extends StatusEffect {
    private int turnsLeft = 5;

    public Frozen() {
        super("Frozen");
    }

    @Override
    public void tick(Location location, Actor actor) {
        if (turnsLeft > 0) {
            actor.hurt(30);
            turnsLeft--;
        } else {
            actor.removeStatusEffect(this);
        }
    }
}

