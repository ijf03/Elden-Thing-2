package game.actions;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actors.Player;
import game.weapons.DragonslayerGreatsword;

public class KaleDragonslayerPurchaseAction extends PurchaseAction {
    public KaleDragonslayerPurchaseAction() {
        super(new DragonslayerGreatsword(), 1700);
    }

    @Override
    protected void applyGlobalEffect(Player player) {
        player.modifyAttributeMaximum(BaseActorAttributes.HEALTH, ActorAttributeOperations.INCREASE, 15);
    }

    @Override
    protected void applyMerchantBonus(Player player, GameMap map) {
        player.restoreStamina(20);
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " buys Dragonslayer Greatsword from Kale [1700 Runes]";
    }

    @Override
    protected String successMessage() {
        return "Purchased Dragonslayer Greatsword from Kale. +15 Max HP, +20 Stamina restored.";
    }
}

