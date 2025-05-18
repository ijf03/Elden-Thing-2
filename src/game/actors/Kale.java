package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.Weapon;
import game.Sellable;
import game.actions.*;
import game.actors.behaviours.NPCController;
import game.actors.behaviours.StandardNPCController;
import game.capabilities.Status;
import game.weapons.Broadsword;
import game.weapons.DragonslayerGreatsword;

import java.util.*;

/**
 * Represents the NPC merchant Kale.
 */
public class Kale extends Actor implements Speaker, Merchant {
    /**
     * Integer to keep count of how many turns have gone
     */
    private final NPCController NPCController;
    private final List<Sellable> items;

    /**
     * Constructor.
     */
    public Kale() {
        super("Merchant Kale", 'K', 200);
        this.addCapability(Status.SELLER_KALE);
        this.NPCController = new StandardNPCController();
        items = new ArrayList<>();
        items.add(new Broadsword());
        items.add(new DragonslayerGreatsword());
    }
    /**
     * Returns the list of items that Kale offers for sale.
     *
     * @return a list of items
     */
    @Override
    public List<Sellable> getSellableItems() {
        return items;
    }
    /**
     * Removes a sold item from Kale's inventory.
     *
     * @param item the item to remove
     */
    @Override
    public void removeSoldItem(Sellable item) {
        items.remove(item);
    }

    /**
     * Logic for what Kale says depending on player's inventory, money and Kale's surroundings
     * @param player who listens
     * @param map where they are
     * @return string Appropriate line said by Kale
     */
    public String ListenAction(Actor player, GameMap map) {
        Random rand = new Random();
        ArrayList<String> monologueOptions = new ArrayList<>();
        for (Exit exit : map.locationOf(this).getExits()) {
            if (map.getActorAt(exit.getDestination()) != null) {
                if (map.getActorAt(exit.getDestination()).hasCapability(Status.CURSED)) {
                    if (!monologueOptions.contains("Rest by the flame when you can, friend. These lands will wear you thin.")) {
                        monologueOptions.add("Rest by the flame when you can, friend. These lands will wear you thin.");
                    }
                }
            } else if (exit.getDestination().getGround().hasCapability(Status.CURSED)) {
                if (!monologueOptions.contains("Rest by the flame when you can, friend. These lands will wear you thin.")) {
                    monologueOptions.add("Rest by the flame when you can, friend. These lands will wear you thin.");
                }
            }
        }
        if (player.getBalance() < 500) {
            monologueOptions.add("Ah, hard times, I see. Keep your head low and your blade sharp.");
        }
        if (player.getItemInventory().isEmpty()) {
            monologueOptions.add("Not a scrap to your name? Even a farmer should carry a trinket or two.");
        }
        monologueOptions.add("A merchant’s life is a lonely one. But the roads… they whisper secrets to those who listen.");
        return monologueOptions.get(rand.nextInt(monologueOptions.size()));
    }

    /**
     * At each turn, select a valid action to perform.
     *
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return the valid action that can be performed in that iteration or null if no valid action is found
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        if (NPCController.retrieveNextAction(this, map) == null) {
            return new DoNothingAction();
        } else {
            return NPCController.retrieveNextAction(this, map);
        }
    }
    /**
     * The actions perform when other actors interact with Kale.
     *
     * @param otherActor the actor interacting with Kale
     * @param direction the direction from which the interaction is happening
     * @param map the current game map
     * @return a list of allowable Action
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = new ActionList();
        if (otherActor.hasCapability(Status.HOSTILE_TO_ENEMY)) {
            actions.add(new AttackAction(this, direction, otherActor.getIntrinsicWeapon()));

            List<String> weaponNames = new ArrayList<>();

            for (Item item : otherActor.getItemInventory()) {
                if (item.hasCapability(Status.USABLE_WEAPON)) {
                    Weapon weapon = (Weapon) item;
                    String name = weapon.toString();

                    if (!weaponNames.contains(name)) {
                        actions.add(new AttackAction(this, direction, weapon));
                        weaponNames.add(name);
                    }
                }
            }
        }
        for (Sellable item : this.getSellableItems()) {
            actions.add(new SellAction(item, this));}
        if (otherActor.hasCapability(Status.PLAYABLE)) {
            actions.add(new ListenAction(this));
        }
        return actions;
    }
}
