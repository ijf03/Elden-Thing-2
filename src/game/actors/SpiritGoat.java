package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.AttackAction;
import game.actions.CureAction;
import game.actors.behaviours.NPCController;
import game.actors.behaviours.StandardNPCController;
import game.capabilities.Status;
import game.actors.behaviours.WanderBehaviour;

import java.util.HashMap;
import java.util.Map;

/**
 * Class representing the NPC SpritGoat which roams around
 * @author Trinh Nguyen
 */
public class SpiritGoat extends Actor implements Curable {


    /**
     * Integer to keep count of how many turns have gone
     */
    private int turnCounter;
    private final NPCController NPCController;

    public SpiritGoat() {
        super("SpiritGoat",'y', 50);
        this.turnCounter = 10;
        this.NPCController = new StandardNPCController();
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
        this.turnCounter -= 1;
        if (this.turnCounter <= 0) {
            map.removeActor(this);
            return new DoNothingAction();
        }
        if (NPCController.retrieveNextAction(this, map) == null) {
            return new DoNothingAction();
        } else {
            return NPCController.retrieveNextAction(this, map);
        }
    }

    /**
     * The spirit goat can be attacked by any actor that has the HOSTILE_TO_ENEMY capability
     * Can rot so another actor can choose to cure them
     *
     * @param otherActor the Actor that might be performing attack
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = new ActionList();
        if (otherActor.hasCapability(Status.HOSTILE_TO_ENEMY)){
            actions.add(new AttackAction(this, direction));
        }
        if (otherActor.hasCapability(Status.CURE)){
            actions.add(new CureAction(this, map.locationOf(this), direction));
        }
        return actions;
    }

    /**
     * spritigoat curing involves resetting the turncounter back to 10
     * @param curer  actor doing the curing
     * @param location location of goat
     */
    public void cure(Actor curer, Location location) {
        this.turnCounter = 10;
    }
}
