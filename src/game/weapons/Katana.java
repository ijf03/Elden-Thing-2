package game.weapons;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.Sellable;
import game.actors.OmenSheep;
import game.actors.Player;
import game.capabilities.Status;
import game.items.WeaponItem;

/**
 * Represent the weapon Katana.
 * Purchasing it from different merchants will have different effects.
 *
 * @author Chi Cheng Chan
 */

public class Katana extends WeaponItem implements Sellable {
    /**
     * Constructor.
     */
    public Katana() {
        super("Katana", 'j', 50, "slashes", 60);
    }
    /**
     * Gets the price of the Katana.
     *
     * @param merchant the merchant
     * @return the price
     */
    @Override
    public int getPrice(Actor merchant) {
        return 500;
    }
    /**
     * The effects applied when a player purchases the Katana.
     * Player takes 25 damage when Katana is purchased.
     * Spawns an OmenSheep near the Sellen.
     * Heals the player by 10 HP.
     * Increases player's max stamina by 20.
     *
     * @param buyer the player
     * @param merchant the merchant
     * @param map the game map
     */
    @Override
    public void onPurchase(Player buyer, Actor merchant, GameMap map) {
        int originalStamina = buyer.getAttribute(BaseActorAttributes.STAMINA);
        buyer.hurt(25);

        if (merchant.hasCapability(Status.SELLER_SELLEN)) {
            Location sellerLocation = map.locationOf(merchant);

            for (Exit exit : sellerLocation.getExits()) {
                Location dest = exit.getDestination();

                if (!dest.containsAnActor() && dest.getGround().hasCapability(Status.PLANTABLE_TERRAIN)) {
                    map.addActor(new OmenSheep(), dest);
                    break;
                }
            }
            buyer.heal(10);
            buyer.increaseMaxStamina(20);
            buyer.modifyAttribute(BaseActorAttributes.STAMINA, ActorAttributeOperations.UPDATE, originalStamina);
        }
    }

    /**
     * Gets the name of the weapon.
     *
     * @return string of the weapon name.
     */
    @Override
    public String getName() {
        return "Katana";
    }
    /**
     * Indicates whether it is a weapon.
     *
     * @return boolean (True)
     */
    @Override
    public boolean isWeapon() {
        return true;
    }
    /**
     * Indicates this weapon as an item.
     *
     * @return this Katana
     */
    @Override
    public Item asItem() {
        return this;
    }
}