package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actors.ManaManager;
import game.actors.Merchant;
import game.actors.Player;
import game.Sellable;
import game.capabilities.Status;
/**
 * Represents an action that allows an actor to sell an item.
 */
public class SellAction extends Action {
    private final Sellable item;
    private final Actor merchant;
    /**
     * Constructor.
     *
     * @param item the item to be sold
     * @param merchant the merchant selling the item
     */
    public SellAction(Sellable item, Actor merchant) {
        super();
        this.item = item;
        this.merchant = merchant;
    }
    /**
     * Executes the selling action of merchant when the player have runes.
     *
     * @param actor the player
     * @param map the current game map
     * @return the string of the result of the action
     */
    @Override
    public String execute(Actor actor, GameMap map) {

        Player player = (Player) actor;

        if (player.getBalance() < item.getPrice(merchant)) {
            return "Not enough runes.";}

        else{
            player.deductBalance(item.getPrice(merchant));
            item.onPurchase(player, merchant, map);

            if (item.isWeapon()) {
                player.addItemToInventory(item.asItem());
            }
            if (actor.hasCapability(Status.PLAYABLE)) {
                ManaManager.onBuy((Player) actor);}


            // remove the item from merchant inventory
            merchant.removeItemFromInventory(item.asItem());
            // remove item form the menu
            ((Merchant) merchant).removeSoldItem(item);

            return "You purchased: " + item.getName() + " from " + merchant + ".";}

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