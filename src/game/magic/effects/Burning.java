package game.magic.effects;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.StatusEffect;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.Location;

public class Burning extends StatusEffect {
    private int turnsLeft = 3;
    private final Display display = new Display();

    public Burning() {
        super("Burning");
    }

    @Override
    public void tick(Location location, Actor actor) {
        if (turnsLeft > 0) {
            int damage = 150;
            actor.hurt(damage);
            display.println(actor + " is burning and takes " + damage + " fire damage! " + turnsLeft + " turns left.");
            turnsLeft--;
        } else {
            actor.removeStatusEffect(this);
        }
    }
}
