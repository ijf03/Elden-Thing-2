package game.items;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.Plantable;
import game.actions.PlantAction;

public class Seed extends Item {
    public Plantable plantable;

    /***
     * Constructor.
     *  @param name the name of this Item
     */
    public Seed(String name,  Plantable plantable) {
        super(name, '*', true);
        this.plantable = plantable;
    }

    /**
     * List of allowable actions that the item allows its owner do to other actor.
     * Example #1: a weapon can return an attacking action to the other actor.
     * Example #2: if the weapon has a special ability, it can return an action to use the special ability.
     * Example #3: a food can return an action to feed the other actor.
     *
     * @param owner the owner of the seed
     * @param map the map where the owner is
     * @return an unmodifiable list of Actions
     */
    @Override
    public ActionList allowableActions(Actor owner, GameMap map){
        ActionList actions = super.allowableActions(owner, map);
        actions.add(new PlantAction(this));
        return actions;
    }
}
