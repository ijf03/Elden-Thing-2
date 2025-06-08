package game.weapons;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.Sellable;
import game.actors.Player;
import game.capabilities.Status;
import game.items.WeaponItem;

/**
 * Represent the weapon Broadsword.
 * Purchasing it from different merchants will have different effects.
 *
 * @author Chi Cheng Chan
 */
public class Broadsword extends WeaponItem implements Sellable {
    /**
     * Constructor.
     */
    public Broadsword() {
        super("Broadsword", 'b', 30, "slashes", 50);
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
            return 100;
        } else if (merchant.hasCapability(Status.SELLER_KALE)) {
            return 150;
        }
        return 0;
    }

    /**
     * The effects applied when a player purchases the Broadsword.
     * Increase 10 HP when the Broadsword is bought.
     * If bought from Sellen: Increases max HP by 20.
     * If bought from Kale: Increases max stamina by 30.
     *
     * @param buyer the player
     * @param merchant the merchant selling it
     * @param map the game map
     */
    @Override
    public void onPurchase(Player buyer, Actor merchant, GameMap map) {
        int originalHp = buyer.getAttribute(BaseActorAttributes.HEALTH);
        int originalStamina = buyer.getAttribute(BaseActorAttributes.STAMINA);

        if (merchant.hasCapability(Status.SELLER_SELLEN)) {
            buyer.increaseMaxHp(20);
        } else if (merchant.hasCapability(Status.SELLER_KALE)) {
            buyer.increaseMaxStamina(30);
        }

        buyer.modifyAttribute(BaseActorAttributes.HEALTH, ActorAttributeOperations.UPDATE, originalHp);
        buyer.modifyAttribute(BaseActorAttributes.STAMINA, ActorAttributeOperations.UPDATE, originalStamina);
        buyer.heal(10);

    }
    /**
     * Gets the name of the weapon.
     *
     * @return string of the weapon name.
     */
    @Override
    public String getName() {
        return "Broadsword";
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
     * @return Broadsword as item
     */
    @Override
    public Item asItem() {
        return this;
    }


}