package game.magic.effects;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.StatusEffect;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.Location;
/**
 * A Status effect that sets an actor to have frozen effect for 5 turns.
 *
 */
public class Frozen extends StatusEffect {
    private int turnsLeft = 5;
    private final Display display = new Display();
    /**
     * Constructor.
     */
    public Frozen() {
        super("Frozen");
    }
    /**
     * Each game tick within the counter turnsLeft,
     * deals 30 damage and prints a message.
     *
     * @param location the location of the actor
     * @param actor    the target that is freezing
     */
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

