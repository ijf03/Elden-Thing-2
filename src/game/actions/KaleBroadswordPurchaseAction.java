package game.actions;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttribute;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actors.Player;
import game.weapons.Broadsword;

public class KaleBroadswordPurchaseAction extends PurchaseAction {
    public KaleBroadswordPurchaseAction() {
        super(new Broadsword(), 150);
    }

    @Override
    protected void applyGlobalEffect(Player player) {
        player.heal(10);
    }

    @Override
    protected void applyMerchantBonus(Player player, GameMap map) {
        int currentStamina = player.getAttribute(BaseActorAttributes.STAMINA);  // e.g. 200

        // Step 1: increase max (this resets current to 230)
        player.modifyAttributeMaximum(BaseActorAttributes.STAMINA, ActorAttributeOperations.INCREASE, 30);

        // Step 2: restore previous value
        player.modifyAttribute(BaseActorAttributes.STAMINA, ActorAttributeOperations.UPDATE, currentStamina);
    }




    @Override
    public String menuDescription(Actor actor) {
        return actor + " buys Broadsword from Kale [150 Runes]";
    }

    @Override
    protected String successMessage() {
        return "Purchased Broadsword from Kale. +10 HP, +30 Max Stamina.";
    }
}