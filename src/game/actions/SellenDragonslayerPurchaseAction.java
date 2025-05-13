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

    @Override
    protected void applyGlobalEffect(Player player) {
        player.modifyAttributeMaximum(BaseActorAttributes.HEALTH, ActorAttributeOperations.INCREASE, 15);
    }

    @Override
    protected void applyMerchantBonus(Player player, GameMap map) {
        // spawn Golden Beetle
        Location center = map.locationOf(player);
        for (Location loc : TileLocation.getNearbyTiles(map, center)) {
            if (!loc.containsAnActor()) {
                loc.addActor(new GoldenBeetle());
                break;
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
