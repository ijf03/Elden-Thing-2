package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.Curable;
import game.Reproducible;
import game.actions.AttackAction;
import game.actions.CureAction;
import game.actors.behaviours.NPCController;
import game.actors.behaviours.StandardNPCController;
import game.capabilities.Status;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class representing the NPC SpritGoat which roams around
 * @author Trinh Nguyen
 */
public class SpiritGoat extends Actor implements Curable, Reproducible {


    /**
     * Integer to keep count of how many turns have gone
     */
    private int turnCounter;
    private final NPCController NPCController;
//    private int reproductionCooldown = 0;

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
        // Check for nearby blessed entities (like Inheritree) and try to reproduce
//        if (reproductionCooldown == 0 && canReproduce(map.locationOf(this))) {
//            reproduce(map.locationOf(this));
//            reproductionCooldown = 5; // Set cooldown period to prevent constant reproduction
//        }
        Location location = map.locationOf(this);
        if (canReproduce(location)) {
            reproduce(map, location);
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

    @Override
    public boolean canReproduce(Location location) {
        for (Exit exit : location.getExits()) {
            Location destination = exit.getDestination();
            if (destination.getGround().hasCapability(Status.BLESSED)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void reproduce(GameMap map, Location location) {
        // store list of valid locations
        ArrayList<Location> exits = new ArrayList<Location>();
        for (Exit exit : location.getExits()) { // location - map.locationOf(this)
            Location destination = exit.getDestination();
            if (destination.canActorEnter(this)) {
                exits.add(destination);
            }
        }
        // choose random location to spawn offspring
        Random random = new Random();
        if (exits.size() != 0) {
            Location spawn = exits.get(random.nextInt(exits.size()));
            SpiritGoat offspring = new SpiritGoat();
            spawn.addActor(offspring);
        }
    }
}
