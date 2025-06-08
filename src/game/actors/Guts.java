package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.AttackAction;
import game.actions.ListenAction;
import game.actors.behaviours.NPCController;
import game.actors.behaviours.StandardNPCController;
import game.capabilities.Status;
import game.weapons.BareFist;

import java.util.ArrayList;
import java.util.Random;

import static game.actions.AttackUtils.addSpellActions;
import static game.actions.AttackUtils.addWeaponActions;

/**
 * This class represents the Guts NPC
 */
public class Guts extends Actor implements Speaker {
    private NPCController NPCController;

    /**
     * Constructor
     * Initiates the NPC controller as StandardNPCController (wanders around)
     * Sets his capability to hostile to enemy so that he is attackable
     * Sets his Intrinsic weapon to BareFist
     */
    public Guts() {
        super("Guts",'g', 500);
        this.NPCController = new StandardNPCController();
        this.addCapability(Status.HOSTILE_TO_ENEMY);
        this.setIntrinsicWeapon(new BareFist());
    }

    /**
     * When the player wants to listen to him
     * @param otherActor the player who is listening
     * @param map where they are
     * @return string of what he responds (depends on the player's health)
     */
    public String ListenAction(Actor otherActor, GameMap map) {
        Random rand = new Random();
        ArrayList<String> monologueOptions = new ArrayList<>();
        monologueOptions.add("RAAAAGH!");
        monologueOptions.add("I’LL CRUSH YOU ALL!");
        if (otherActor.getAttribute(BaseActorAttributes.HEALTH) < 50) {
            return "WEAK! TOO WEAK TO FIGHT ME!";
        }
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
        for (Exit exit : map.locationOf(this).getExits()) {
            if (exit.getDestination().containsAnActor()) {
                if (exit.getDestination().getActor().getAttribute(BaseActorAttributes.HEALTH) > 50) {
                    return new AttackAction(exit.getDestination().getActor(), exit.getName(), getIntrinsicWeapon());
                }
            }
        }
        // NPCController gives next move action
        if (NPCController.retrieveNextAction(this, map) == null) {
            return new DoNothingAction();
        } else {
            return NPCController.retrieveNextAction(this, map);
        }
    }

    /**
     * Guts can be attacked by any actor that has the HOSTILE_TO_ENEMY capability
     * When he attacks the weapon he attacks with must be chosen
     * He can be listened to by just the player
     *
     *
     * @param otherActor the Actor that might be performing attack
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return actionList
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = new ActionList();
        if (otherActor.hasCapability(Status.HOSTILE_TO_ENEMY)) {
            actions.add(new AttackAction(this, direction, otherActor.getIntrinsicWeapon()));
            addWeaponActions(otherActor, this, direction, actions);
            addSpellActions( otherActor,this, actions);
        }
        // Can only listen if the other actor is the player/is playable
        if (otherActor.hasCapability(Status.PLAYABLE)) {
            actions.add(new ListenAction(this));
        }
        return actions;
    }
}
