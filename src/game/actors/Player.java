package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttribute;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.displays.Menu;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.Weapon;
import game.FancyMessage;
import game.actions.AttackAction;
import game.actions.ListenAction;
import game.capabilities.Status;
import game.items.Seed;
import game.items.WeaponItem;
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
    private Weapon equippedWeapon;
    /**
     * Constructor.
     *
     * @param name Name to call the player in the UI
     * @param displayChar Character to represent the player in the UI
     * @param hitPoints Player's starting number of hitpoints (health)
     */
    public Player(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
        this.addAttribute(BaseActorAttributes.HEALTH, new BaseActorAttribute(100));
        this.addAttribute(BaseActorAttributes.STAMINA, new BaseActorAttribute(200));
        this.addCapability(Status.HOSTILE_TO_ENEMY);
        this.addCapability(Status.PLAYABLE);
        this.setIntrinsicWeapon(new BareFist());
        this.addCapability(Status.FOLLOWABLE);
    }

    /**
     * Displays the player's current attributes (health and stamina) at each turn.
     *
     * @return a string representation including stamina information
     */
    @Override
    public String toString() {
        return super.toString() +
                " | Stamina: " + this.getAttribute(BaseActorAttributes.STAMINA) + "/" +
                this.getAttributeMaximum(BaseActorAttributes.STAMINA);
    }


    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = new ActionList();
        if (otherActor.hasCapability(Status.HOSTILE_TO_ENEMY)){
            actions.add(new AttackAction(this, direction));
        }
        return actions;
    }

    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        if (!this.isConscious()) {
            display.println(FancyMessage.YOU_DIED);
            this.unconscious(map);
        }

        // Handle multi-turn Actions
        if (lastAction.getNextAction() != null)
            return lastAction.getNextAction();

        display.println("--- Your Runes: " + this.getBalance() + " ---");

        Menu menu = new Menu(actions);
        return menu.showMenu(this, display);

    }
    public void setWeapon(Weapon weapon) {
        this.equippedWeapon = weapon;
    }
    public Weapon getWeapon() {
        return (equippedWeapon != null) ? equippedWeapon : getIntrinsicWeapon();
    }

    /**
     * Gets the current stamina value of the player.
     *
     * @return stamina values
     */

    public int getStamina() {
        return this.getAttribute(BaseActorAttributes.STAMINA);
    }


    /**
     * Restores the player's staminaa by a specific amount.
     *
     * @param amount the amount of stamina increase
     */
    public void restoreStamina(int amount) {
        this.modifyAttribute(BaseActorAttributes.STAMINA, ActorAttributeOperations.INCREASE, amount);
    }

    public void increaseMaxHp(int amount) {
        this.modifyAttributeMaximum(BaseActorAttributes.HEALTH, ActorAttributeOperations.INCREASE, amount);
    }

    public void increaseMaxStamina(int amount) {
        this.modifyAttributeMaximum(BaseActorAttributes.STAMINA, ActorAttributeOperations.INCREASE, amount);
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
