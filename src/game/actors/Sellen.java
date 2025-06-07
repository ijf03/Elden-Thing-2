package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.Weapon;
import game.Sellable;
import game.actions.*;
import game.actors.behaviours.NPCController;
import game.actors.behaviours.StandardNPCController;
import game.capabilities.Status;
import game.magic.MagicItem;
import game.weapons.Broadsword;
import game.weapons.DragonslayerGreatsword;
import game.weapons.Katana;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Sellen extends Actor implements Speaker, Merchant {
    /**
     * Integer to keep count of how many turns have gone
     */
    private final NPCController NPCController;
    private final List<Sellable> items;
    /**
     * Constructor.
     */
    public Sellen() {
        super("Sellen",'s', 150);
        this.NPCController = new StandardNPCController();
        this.addCapability(Status.SELLER_SELLEN);
        items = new ArrayList<>();
        items.add(new Broadsword());
        items.add(new DragonslayerGreatsword());
        items.add(new Katana());
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
     * Sellen can say one of 2 lines chosen at random when the player chooses to listen
     * @param player who listens
     * @param map where they are
     * @return string that is heard by the player
     */
    public String ListenAction(Actor player, GameMap map) {
        Random rand = new Random();
        ArrayList<String> monologueOptions = new ArrayList<>();
        monologueOptions.add("The academy casts out those it fears. Yet knowledge, like the stars, cannot be bound forever.");
        monologueOptions.add("You sense it too, don’t you? The Glintstone hums, even now.");
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
     * The actions perform when other actors interact with Sellen.
     *
     * @param otherActor the actor interacting with Sellen
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
            List<String> spellNames = new ArrayList<>();
            for (Item item : otherActor.getItemInventory()) {
                if (item.hasCapability(Status.MAGIC_SPELL)) {
                    MagicItem spell = (MagicItem) item;
                    String name = spell.getName();
                    if (!spellNames.contains(name)) {
                        actions.add(new CastSpellAction(spell, this));
                        spellNames.add(name);
                    }
                }
            }
        }
            // Only can be listened to if the other actor is playable
            if (otherActor.hasCapability(Status.PLAYABLE)) {
                actions.add(new ListenAction(this));
            }
            for (Sellable item : this.getSellableItems()) {
                actions.add(new SellAction(item, this));}
            return actions;

    }
}
