package game.potions;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.StatusEffect;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Location;
import game.capabilities.Status;

public class SpeakPotion extends Item implements Throwable {
    private SpeakEffect speakEffect;

    public SpeakPotion() {
        super("Speak Potion", 'p', true);
    }

    @Override
    public void throwed(Actor actor, Actor target) {
        this.speakEffect = new SpeakEffect();
//        actor.removeItemFromInventory(this);
        target.addStatusEffect(getEffect());
        target.addCapability(Status.SPEAK);
    }

    @Override
    public StatusEffect getEffect() {
        return this.speakEffect;
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
