package game.weapons;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.Sellable;
import game.actors.GoldenBeetle;
import game.actors.Player;
import game.capabilities.Status;
import game.items.WeaponItem;

/**
 * Represent the weapon Dragonslayer Greatsword.
 * Purchasing it from different merchants will have different effects.
 *
 * @author Chi Cheng Chan
 */
public class DragonslayerGreatsword extends WeaponItem implements Sellable {
    /**
     * Constructor.
     */
    public DragonslayerGreatsword() {
        super("Dragonslayer Greatsword", 'D', 70, "cleaves", 75);
        this.addCapability(Status.USABLE_WEAPON);
    }
    /**
     * Returns the price of the Broadsword depending on the specific merchant.
     *
     * @param merchant the merchant
     * @return the price of the weapon
     */
    @Override
    public int getPrice(Actor merchant) {
        if (merchant.hasCapability(Status.SELLER_SELLEN)) {
            return 1500;
        } else if (merchant.hasCapability(Status.SELLER_KALE)) {
            return 1700;
        }
        return 0;
    }

    /**
     * The effects applied when a player purchases the Dragonslayer Greatsword.
     * Increases the player's max HP by 15 when Dragonslayer Greatsword is purchased.
     * If sold by Sellen, spawns a GoldenBeetle near farmer.
     * If sold by Kale, restores 20 stamina.
     *
     * @param buyer the player
     * @param merchant the merchant selling it
     * @param map the game map
     */
    @Override
    public void onPurchase(Player buyer, Actor merchant, GameMap map) {
        int originalHp = buyer.getAttribute(BaseActorAttributes.HEALTH);

        buyer.increaseMaxHp(15);

        if (merchant.hasCapability(Status.SELLER_SELLEN)) {
            // Spawn Golden Beetle near the buyer
            Location here = map.locationOf(buyer);
            for (Exit exit : here.getExits()) {
                Location destination = exit.getDestination();
                if (!destination.containsAnActor()) {
                    map.addActor(new GoldenBeetle(), destination);
                    break;
                }
            }
        } else if (merchant.hasCapability(Status.SELLER_KALE)) {
            buyer.restoreStamina(20);
        }
        buyer.modifyAttribute(BaseActorAttributes.HEALTH, ActorAttributeOperations.UPDATE, originalHp);
    }
    /**
     * Gets the name of the weapon.
     *
     * @return string of the weapon name.
     */
    @Override
    public String getName() {
        return "Dragonslayer Greatsword";
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
     * @return this Dragonslayer Greatsword
     */
    @Override
    public Item asItem() {
        return this;
    }
}