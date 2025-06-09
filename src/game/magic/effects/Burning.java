package game.magic.effects;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.StatusEffect;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.Location;
/**
 * A Status effect that sets an actor to have burning effect for 3 turns.
 *
 */

public class Burning extends StatusEffect {
    private int turnsLeft = 3;
    private final Display display = new Display();
    /**
     * Constructor.
     *
     */
    public Burning() {
        super("Burning");
    }

    /**
     * Each game tick within the counter turnsLeft,
     * deals 150 damage and prints a message.
     *
     * @param location the location of the actor
     * @param actor    the target that is burning
     */
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
