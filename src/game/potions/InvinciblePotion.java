package game.potions;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.StatusEffect;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Location;
import game.capabilities.Status;

public class InvinciblePotion extends Item implements Throwable {
    private InvincibleEffect invincibleEffect;
    /***
     * Constructor.
     */
    public InvinciblePotion() {
        super("Invincible Potion", 'p', true);
    }

    @Override
    public void throwed(Actor actor, Actor target) {
        this.invincibleEffect = new InvincibleEffect();
//        actor.removeItemFromInventory(this);
        target.addStatusEffect(getEffect());
        target.addCapability(Status.INVINCIBLE);
    }

    @Override
    public StatusEffect getEffect() {
        return this.invincibleEffect;
    }

    @Override
    public ActionList allowableActions(Actor otherActor, Location location) {
        ActionList actions = super.allowableActions(otherActor, location);
        Actor target = location.getActor();
        if (target == otherActor) {
            actions.add(new ThrowAction(this, target));
        }
        return actions;
    }
}
