package game.weapons.magic.effects;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.StatusEffect;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.Location;

public class Frozen extends StatusEffect {
    private int turnsLeft = 5;
    private final Display display = new Display();


    public Frozen() {
        super("Frozen");
    }

    @Override
    public void tick(Location location, Actor actor) {
        if (turnsLeft > 0) {
            int damage = 30;
            actor.hurt(damage);
            display.println(actor + " is frozen and takes " + damage + " frost damage! " + turnsLeft + " turns left.");
            turnsLeft--;
        } else {
            actor.removeStatusEffect(this);
        }
    }
}

