package game.actions;


import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actions.Action;
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
        return item.cast(actor, target, map);
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " casts " + item.getName() + " on " + target;
    }
}