package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.items.Item;
import game.actors.Player;

public abstract class PurchaseAction extends Action {
    protected final int cost;
    protected final Item item;

    public PurchaseAction(Item item, int cost) {
        this.item = item;
        this.cost = cost;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        if (!(actor instanceof Player)) return "Only a Player can purchase.";
        Player player = (Player) actor;

        if (player.getBalance() < cost) {
            return "Not enough Runes!";
        }

        player.deductBalance(cost);
        applyGlobalEffect(player);
        applyMerchantBonus(player, map);
        player.addItemToInventory(item);
        return successMessage();
    }

    protected void applyGlobalEffect(Player player) {}
    protected abstract void applyMerchantBonus(Player player, GameMap map);
    protected abstract String successMessage();
}
