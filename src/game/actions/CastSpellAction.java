package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.GameMap;
import game.magic.MagicItem;

public class CastSpellAction extends Action {
    private final MagicItem item;
    private final Actor target;

    public CastSpellAction(MagicItem item, Actor target) {
        this.item = item;
        this.target = target;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        Integer currentMana = actor.getAttribute(BaseActorAttributes.MANA);
        if (currentMana != null) {
            int cost = item.getManaCost();
            if (currentMana < cost) {
                return actor + " does not have enough mana to cast " + item;
            }
            actor.modifyAttribute(BaseActorAttributes.MANA, ActorAttributeOperations.DECREASE, cost);
        }
        return item.cast(actor, target, map);
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " casts " + item.toString() + " on " + target + " cost " + item.getManaCost() + " mana)";
    }
}
