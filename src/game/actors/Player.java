package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.displays.Menu;
import edu.monash.fit2099.engine.positions.GameMap;
import game.FancyMessage;
import game.actions.AttackAction;
import game.actions.ListenAction;
import game.capabilities.Status;
import game.weapons.BareFist;

/**
 * Class representing the Player.
 * @author Adrian Kristanto
 */
public class Player extends Actor {
    /**
     * Constructor.
     *
     * @param name        Name to call the player in the UI
     * @param displayChar Character to represent the player in the UI
     * @param hitPoints   Player's starting number of hitpoints
     */
    public Player(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
        this.addCapability(Status.HOSTILE_TO_ENEMY);
        this.addCapability(Status.PLAYABLE);
        this.setIntrinsicWeapon(new BareFist());
    }

    /**
     * A method for returning the string representation of an actor.
     * It displays the actor's name and its current hit points, along with its maximum health hit points.
     */
    @Override
    public String toString() {
        StringBuilder string = new StringBuilder(this.name + " ");

        for (BaseActorAttributes attribute : BaseActorAttributes.values()) {
            if (getAttribute(attribute) != null) {
                string.append(attribute.toString() + " is (" +
                        this.getAttribute(attribute) + "/" +
                        this.getAttributeMaximum(attribute) + ") ");
            }
        }

        return string.toString();
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
        // If farmer is unconscious need print statement
        if (!this.isConscious()) {
            display.println(FancyMessage.YOU_DIED);
            this.unconscious(map);
//            actions.clear(); // Farmer can not take anymore actions if unconscious
//            map.removeActor(this);
        }

        // Handle multi-turn Actions
        if (lastAction.getNextAction() != null)
            return lastAction.getNextAction();

        // return/print the console menu
        Menu menu = new Menu(actions);
        return menu.showMenu(this, display);
    }
}
