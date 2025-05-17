package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.items.Item;
import game.actors.Merchant;
import game.actors.Player;
import game.Sellable;
import game.capabilities.Status;
/**
 * Represents an action that allows a player to purchase a item from a Merchant.
 */
public class PurchaseAction extends Action {
    private final Sellable item;
    private final Actor merchant;
    /**
     * Constructor.
     *
     * @param item the item to be purchased
     * @param merchant the merchant selling the item
     */
    public PurchaseAction(Sellable item, Actor merchant) {
        super();
        this.item = item;
        this.merchant = merchant;
    }
    /**
     * Executes the purchase action.
     *
     * @param actor the player
     * @param map the current game map
     * @return the string of the result of the action
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        if (!actor.hasCapability(Status.HOSTILE_TO_ENEMY)) return "Only Players can buy.";

        Player player = (Player) actor;

        if (player.getBalance() < item.getPrice(merchant)) return "Not enough runes.";

        player.deductBalance(item.getPrice(merchant));
        item.onPurchase(player, merchant, map);

        if (item.isWeapon()) {
            player.addItemToInventory(item.asItem());
        }

        if (merchant.hasCapability(Status.MERCHANT)) {
            if (item.isWeapon()) {
                merchant.removeItemFromInventory(item.asItem());
            }
            ((Merchant) merchant).removeSoldItem(item);
        }

        return "You purchased: " + item.getName() + " from " + merchant + ".";

    }
    /**
     * Provides a description of this action in game menu.
     *
     * @param actor the player
     * @return the string of menu description
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " buys " + item.getName() + " from " + merchant + " for " + item.getPrice(merchant) + " runes";
    }

}