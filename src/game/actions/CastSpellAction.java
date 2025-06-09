package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.GameMap;
import game.magic.MagicItem;
/**
 * Represents an action in which an actor casts a magical item.
 *
 */
public class CastSpellAction extends Action {
    private final MagicItem item;
    private final Actor target;
    /**
     * Constructor.
     *
     * @param item the magic item that can be used to cast
     * @param target the actor affected by the spell
     */
    public CastSpellAction(MagicItem item, Actor target) {
        this.item = item;
        this.target = target;
    }
    /**
     * Executes the spell-casting action.
     * When the caster has sufficient mana, the mana cost is deducted.
     * And applied the item’s effect.
     *
     * @param actor the actor cast the spell
     * @param map the game map where the action takes place
     * @return a string of the action’s outcome
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        Integer currentMana = actor.getAttribute(BaseActorAttributes.MANA);
        int cost = item.getManaCost();
        if (currentMana < cost) {
            return actor + " does not have enough mana to cast " + item;
            }
        actor.modifyAttribute(BaseActorAttributes.MANA, ActorAttributeOperations.DECREASE, cost);
        return item.cast(actor, target, map);
    }
    /**
     * Show in the menu describing the cast action.
     *
     * @param actor the actor who can cast the spell
     * @return a string describing the menu option for this action
     */

    @Override
    public String menuDescription(Actor actor) {
        return actor + " casts " + item.toString() + " on " + target + " cost " + item.getManaCost() + " mana";
    }
}
