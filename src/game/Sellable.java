package game;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actors.Player;

/**
 * Items that can be sold by merchants in the game.
 * @author Chi Cheng Chan
 */

public interface Sellable {
    /**
     * Gets the name of the item.
     *
     * @return the name of the item
     */
    String getName();

    /**
     * Returns the price of the item when sold by a specific merchant.
     *
     * @param merchant the merchant selling the item
     * @return the price
     */
    int getPrice(Actor merchant);

    /**
     * Effects when the item is purchased.
     *
     * @param buyer the player
     * @param merchant the specific merchant
     * @param map the current location
     */
    void onPurchase(Player buyer, Actor merchant, GameMap map);
    /**
     * Checks whether the item is a weapon.
     *
     * @return boolean
     */
    boolean isWeapon();
    /**
     * Provides that the purchase weapon is an item.
     *
     * @return item
     */
    Item asItem();

}

