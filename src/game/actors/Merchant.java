package game.actors;
import game.Sellable;
import java.util.List;
/**
 * Represents a merchant who can sell items to players.
 * @author Chi Cheng Chan
 */
public interface Merchant {
    /**
     * Create a list of items that the merchant currently has available for sale.
     *
     * @return a list of items
     */
    List<Sellable> getSellableItems();
    /**
     * Removes an item from the merchant's inventory.
     *
     * @param item the item to be removed
     */
    void removeSoldItem(Sellable item);
}
