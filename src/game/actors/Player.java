package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Wallet;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttribute;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.displays.Menu;
import edu.monash.fit2099.engine.positions.GameMap;
import game.FancyMessage;
import game.actions.AttackAction;
import game.capabilities.Status;
import game.weapons.BareFist;

/**
 * Representing the Player in the game.
 *
 * Created by: Adrian Kristanto
 * Modified by: All members
 */
public class Player extends Actor {

    /**
     * Constructor.
     *
     * @param name        Name to call the player
     * @param displayChar Character to represent the player in the map
     * @param hitPoints   Player's starting health
     */
    public Player(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
        this.addAttribute(BaseActorAttributes.HEALTH, new BaseActorAttribute(100));
        this.addAttribute(BaseActorAttributes.STAMINA, new BaseActorAttribute(200));
        this.addAttribute(BaseActorAttributes.MANA, new BaseActorAttribute(1000));
        //this.modifyAttribute(BaseActorAttributes.MANA, ActorAttributeOperations.UPDATE, 0);
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
                this.getAttributeMaximum(BaseActorAttributes.STAMINA) + "/" +
                " | Mana: " + this.getAttribute(BaseActorAttributes.MANA);
    }

    /**
     * Determines allowable actions another actor can perform on the player.
     *
     * @param otherActor the actor initiating interaction
     * @param direction  the direction from which they approach
     * @param map        the game map
     * @return a list of allowable actions (e.g., attack)
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = new ActionList();
        if (otherActor.hasCapability(Status.HOSTILE_TO_ENEMY)) {
            actions.add(new AttackAction(this, direction));
        }
        return actions;
    }

    /**
     * Determines what action the player takes during their turn.
     *
     * @param actions    list of available actions
     * @param lastAction last action the player performed
     * @param map        current game map
     * @param display    I/O display for output
     * @return the chosen action
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        if (!this.isConscious()) {
            display.println(FancyMessage.YOU_DIED);
            this.unconscious(map);
        }

        ManaManager.onTurn(this);

        // Handle multi-turn Actions
        if (lastAction.getNextAction() != null)
            return lastAction.getNextAction();

        display.println("--- Your Runes: " + this.getBalance() + " ---");

        Menu menu = new Menu(actions);
        return menu.showMenu(this, display);

    }

    /**
     * Restores the player's stamina by a specific amount.
     *
     * @param amount the amount of stamina to restore
     */
    public void restoreStamina(int amount) {
        this.modifyAttribute(BaseActorAttributes.STAMINA, ActorAttributeOperations.INCREASE, amount);
    }

    /**
     * Increases the player's maximum health.
     *
     * @param amount the amount to increase max HP by
     */
    public void increaseMaxHp(int amount) {
        this.modifyAttributeMaximum(BaseActorAttributes.HEALTH, ActorAttributeOperations.INCREASE, amount);
    }

    /**
     * Increases the player's maximum stamina.
     *
     * @param amount the amount to increase max stamina by
     */
    public void increaseMaxStamina(int amount) {
        this.modifyAttributeMaximum(BaseActorAttributes.STAMINA, ActorAttributeOperations.INCREASE, amount);
    }

    /**
     * Heals the player by a specific amount.
     *
     * @param amount the amount of health points to restore
     */
    @Override
    public void heal(int amount) {
        super.heal(amount);
    }

    private final Wallet wallet = new Wallet();

    public Wallet getWallet() {
        return wallet;
    }
    /**
     * Adds or subtracts mana. Negative amount spends mana.
     */
    public void addMana(int amount) {
        modifyAttribute(BaseActorAttributes.MANA, ActorAttributeOperations.INCREASE, amount);
    }
}
