package game.actions;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actors.Player;
import game.weapons.Broadsword;

public class SellenBroadswordPurchaseAction extends PurchaseAction {

    public SellenBroadswordPurchaseAction() {
        super(new Broadsword(), 100);
    }

    @Override
    protected void applyMerchantBonus(Player player, GameMap map) {
        int originalHp = player.getAttribute(BaseActorAttributes.HEALTH); // e.g. 95

        // Step 1: Increase max HP (this resets current HP to new max)
        player.modifyAttributeMaximum(BaseActorAttributes.HEALTH, ActorAttributeOperations.INCREASE, 20);

        // Step 2: Restore intended HP (original + 10), capped to new max
        int desiredHp = Math.min(originalHp + 10, player.getAttributeMaximum(BaseActorAttributes.HEALTH));
        player.modifyAttribute(BaseActorAttributes.HEALTH, ActorAttributeOperations.UPDATE, desiredHp);
    }


    /*@Override
    protected void applyGlobalEffect(Player player) {
        player.heal(10);
    }*/

    @Override
    public String menuDescription(Actor actor) {
        return actor + " buys Broadsword from Sellen [100 Runes]";
    }

    @Override
    protected String successMessage() {
        return "Purchased Broadsword from Sellen. +10 HP, +20 Max HP.";
    }
}
