package game.potions;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.StatusEffect;

public interface Throwable {
    void throwed(Actor actor, Actor target);

    StatusEffect getEffect();
}
