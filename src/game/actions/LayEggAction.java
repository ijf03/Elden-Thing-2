package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.items.GoldenEgg;

/**
 * Action for Golden Beetle to lay an egg in its location
 */
public class LayEggAction extends Action {

    @Override
    public String execute(Actor actor, GameMap map) {
        Location here = map.locationOf(actor);
        here.addItem(new GoldenEgg());
        return actor + " lays a Golden Egg.";
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " lays a Golden Egg";
    }
}
