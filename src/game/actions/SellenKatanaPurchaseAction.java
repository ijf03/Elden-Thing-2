package game.actions;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.TileLocation;
import game.actors.OmenSheep;
import game.actors.Player;
import game.weapons.Katana;

import java.util.List;

public class SellenKatanaPurchaseAction extends PurchaseAction {

    private final Location merchantLocation;

    public SellenKatanaPurchaseAction(Location merchantLocation) {
        super(new Katana(), 500);
        this.merchantLocation = merchantLocation;
    }

    @Override
    protected void applyGlobalEffect(Player player) {
        player.hurt(25);  // universal damage
    }

    @Override
    protected void applyMerchantBonus(Player player, GameMap map) {
        Location playerLoc = map.locationOf(player);
        int currentStamina = player.getAttribute(BaseActorAttributes.STAMINA);

        for (Location loc : TileLocation.getNearbyTiles(map, playerLoc)) {
            boolean isSoil = loc.getGround().toString().equalsIgnoreCase("Soil");
            boolean isFree = !loc.containsAnActor();

            if (isSoil && isFree) {
                loc.addActor(new OmenSheep());
                break;
            }
        }

        player.heal(10);
        // Step 1: Increase max (this resets current to 220)
        player.modifyAttributeMaximum(BaseActorAttributes.STAMINA, ActorAttributeOperations.INCREASE, 20);

// Step 2: Restore the original value (e.g. 125)
        player.modifyAttribute(BaseActorAttributes.STAMINA, ActorAttributeOperations.UPDATE, currentStamina);
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " buys Katana from Sellen [500 Runes]";
    }

    @Override
    protected String successMessage() {
        return "Purchased Katana from Sellen. -25 HP, +10 HP, +20 Max Stamina, Omen Sheep spawned near Sellen.";
    }
}
