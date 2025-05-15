package game.actions;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.TileLocation;
import game.actors.GoldenBeetle;
import game.actors.Player;
import game.weapons.DragonslayerGreatsword;

import java.util.List;

public class SellenDragonslayerPurchaseAction extends PurchaseAction {
    public SellenDragonslayerPurchaseAction() {
        super(new DragonslayerGreatsword(), 1500);
    }

    /*@Override
    protected void applyGlobalEffect(Player player) {
        player.modifyAttributeMaximum(BaseActorAttributes.HEALTH, ActorAttributeOperations.INCREASE, 15);
    }*/

    @Override
    protected void applyMerchantBonus(Player player, GameMap map) {
        // Step 1: Preserve current HP
        int currentHp = player.getAttribute(BaseActorAttributes.HEALTH);

        // Step 2: Increase max HP
        player.modifyAttributeMaximum(BaseActorAttributes.HEALTH, ActorAttributeOperations.INCREASE, 15);

        // Step 3: Restore current HP
        player.modifyAttribute(BaseActorAttributes.HEALTH, ActorAttributeOperations.UPDATE, currentHp);

        // Step 4: Spawn Golden Beetles on adjacent Soil tiles without actors
        Location playerLoc = map.locationOf(player);

        for (Location tile : TileLocation.getNearbyTiles(map, playerLoc)) {
            boolean isSoil = tile.getGround().toString().equalsIgnoreCase("Soil");
            boolean isEmpty = !tile.containsAnActor();

            if (isSoil && isEmpty) {
                tile.addActor(new GoldenBeetle());
            }
        }
    }



    @Override
    public String menuDescription(Actor actor) {
        return actor + " buys Dragonslayer Greatsword from Sellen [1500 Runes]";
    }

    @Override
    protected String successMessage() {
        return "Purchased Dragonslayer Greatsword from Sellen. +15 Max HP, Golden Beetle spawned nearby.";
    }
}
