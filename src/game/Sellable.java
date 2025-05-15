package game;

import game.actors.Player;

/**
 * Sellable interface represent a sellable creature or item.
 * <p>
 * Implementing classes should include the selling logic and the XP awarded to the farmer.
 * </p>
 */
public interface Sellable {
    /**
     * Sells the creature or item.
     *
     * @param farmer the farmer doing the sell action.
     * @return the XP gained from the sale.
     */
    int Purchase(Player farmer);
    /**
     * Check whether the sellable object is an item.
     *
     * @return isItem as boolean
     */
    boolean isItem();

}
