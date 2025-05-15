package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.items.Item;
import game.actors.Player;
import game.Sellable;

public abstract class PurchaseAction extends Action {
    private Sellable sellable;
    protected final int cost;
    protected final Item item;

    public PurchaseAction(Item item, int cost) {
        this.item = item;
        this.cost = cost;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
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

    protected void applyGlobalEffect(Player player) {


    }
    protected abstract void applyMerchantBonus(Player player, GameMap map);
    protected abstract String successMessage();
}
