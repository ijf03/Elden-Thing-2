package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.GameMap;
import game.capabilities.Status;
import game.items.Seed;

/**
 * Class representing the planting action
 * This will be invoked when a seed is in the actor's inventory
 */
public class PlantAction extends Action {
        /**
         * Current item
         */
        private final Seed seed;

        /**
         * Constructor.
         *
         * @param seed to plant
         */
        public PlantAction(Seed seed) {
            this.seed = seed;
        }

        /**
         * When an item is planted, remove the item from the actor's inventory and add it to the current location of the actor.
         * It is only plantable if the crop permits it (the crop in inventory is a seed)
         * @param actor The actor planting
         * @param map The map the actor is on.
         * @return String to print on the menu
         */
        @Override
        public String execute(Actor actor, GameMap map) {
            if (actor.getAttribute(BaseActorAttributes.STAMINA) > 0 && map.locationOf(actor).getGround().hasCapability(Status.PLANTABLE_TERRAIN)) {
                actor.removeItemFromInventory(seed);
                seed.plantable.PlantAction(actor, map);
                return menuDescription(actor);
            }
            return actor + " cannot plant the " + seed + " here";
        }

        /**
         * A string describing the action suitable for displaying in the UI menu.
         *
         * @param actor The actor planting the crop
         * @return planting string
         */
        @Override
        public String menuDescription(Actor actor) {
            return actor + " plants the " + seed;
        }

}
