package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttribute;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.displays.Menu;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.PlantAction;
import game.capabilities.Status;
import game.items.Seed;
import game.weapons.BareFist;

import java.util.HashMap;
import java.util.Map;


/**
 * Class representing the Player.
 * Created by:
 * @author Adrian Kristanto
 * Modified by:Modified by: Chi Cheng Chan
 *
 */
public class Player extends Actor {


    private Map<BaseActorAttributes, BaseActorAttribute> attributes = new HashMap<>();

    /**
     * Constructor.
     *
     * @param name Name to call the player in the UI
     * @param displayChar Character to represent the player in the UI
     * @param hitPoints Player's starting number of hitpoints (health)
     */
    public Player(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
        attributes.put(BaseActorAttributes.HEALTH, new BaseActorAttribute(100));
        attributes.put(BaseActorAttributes.STAMINA, new BaseActorAttribute(200));
        this.addCapability(Status.HOSTILE_TO_ENEMY);
        this.setIntrinsicWeapon(new BareFist());  // Default weapon



    }

    /**
     * Displays the player's current attributes (health and stamina) at each turn.
     *
     * @return a string representation including stamina information
     */
    @Override
    public String toString() {
        return super.toString() +
                " | Stamina: " + getStamina() + "/" + attributes.get(BaseActorAttributes.STAMINA).getMaximum();
    }

    /**
     * Defines the Player's behaviour each turn.
     *
     * @param actions Collection of possible Actions for Player
     * @param lastAction Action this Player took last turn
     * @param map Map with the Player
     * @param display Display messages
     * @return The selected action
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        if (!this.isConscious()) {
            display.println("YOU DIED");
            System.exit(0);
        }

        Location currentLocation = map.locationOf(this);


        // Handle multi-turn Actions
        if (lastAction.getNextAction() != null)
            return lastAction.getNextAction();

        Menu menu = new Menu(actions);
        return menu.showMenu(this, display);
    }

    /**
     * Gets the current stamina value of the player.
     *
     * @return stamina values
     */

    public int getStamina() {
        return attributes.get(BaseActorAttributes.STAMINA).get();
    }
    /**
     * Reduces the player's stamina by a specific amount.
     *
     * @param amount the amount of stamina decrease
     */
    public void reduceStamina(int amount) {
        attributes.get(BaseActorAttributes.STAMINA).decrease(amount);
    }
    /**
     * Restores the player's staminaa by a specific amount.
     *
     * @param amount the amount of stamina increase
     */
    public void restoreStamina(int amount) {
        attributes.get(BaseActorAttributes.STAMINA).increase(amount);
    }

    /**
     * Heals the Player by the specified amount of health points.
     *
     * @param amount the amount of health points add
     */
    @Override
    public void heal(int amount) {
        super.heal(amount);
    }

}
