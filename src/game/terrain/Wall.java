package game.terrain;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;

/**
 * A class representing a wall that cannot be entered by any actor
 * @author Riordan D. Alfredo
 */
public class Wall extends Ground {

    /**
     * Constructor
     */
    public Wall() {
        super('#', "Wall");
    }

    /**
     * Boolean check if an actor can pass through a wall
     * @param actor the Actor to check
     * @return false you cannot
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        return false;
    }
}
