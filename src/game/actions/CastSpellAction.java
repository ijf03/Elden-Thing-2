package game.actions;


import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actors.ManaManager;
import game.actors.Player;
import game.capabilities.Status;
import game.magic.MagicItem;
import game.magic.Spell;

public class CastSpellAction extends Action {
    private final Spell spell;
    private final Actor target;

    public CastSpellAction(Spell spell, Actor target) {
        this.spell = spell;
        this.target = target;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        Integer currentMana = actor.getAttribute(BaseActorAttributes.MANA);
        if (currentMana == null || currentMana < spell.getManaCost()) {
            return actor + " does not have enough mana to cast " + spell + ".";
        }

        actor.modifyAttribute(BaseActorAttributes.MANA,
                ActorAttributeOperations.DECREASE,
                spell.getManaCost());


        if (actor instanceof Player && actor.hasCapability(Status.PLAYABLE)) {
            ManaManager.onAttack((Player) actor, target);
        }


        return spell.cast(actor, target, map);
    }

    @Override
    public String menuDescription(Actor actor) {
        return String.format("%s casts %s on %s (cost %d mana)", actor, spell, target, spell.getManaCost());
    }
}
