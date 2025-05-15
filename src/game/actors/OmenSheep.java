package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.Reproducible;
import game.actions.AttackAction;
import game.actions.CureAction;
import game.actors.behaviours.NPCController;
import game.actors.behaviours.StandardNPCController;
import game.capabilities.Status;
import game.actors.behaviours.WanderBehaviour;
import game.items.Egg;
import game.terrain.Inheritree;

import java.util.HashMap;
import java.util.Map;

/**
 * Class representing the NPC OmenSheep which roams around
 * @author Trinh Nguyen
 */
public class OmenSheep extends Actor implements Curable, Reproducible {
    /**
     * A Hashmap storing all the behaviours
     */
    private Map<Integer, Behaviour> behaviours = new HashMap<>();

    /**
     * Integer to keep count of how many turns have gone
     */
    private int turnCounter;
    private final NPCController NPCController;
    private int reproduceCounter = 7;

    public OmenSheep() {
        super("OmenSheep",'m', 75);
        this.behaviours.put(999, new WanderBehaviour());
        this.turnCounter = 15;
        this.NPCController = new StandardNPCController();
    }

    // Getters and Setters
    public int getReproduceCounter() {
        return reproduceCounter;
    }

    public void setReproduceCounter(int reproduceCounter) {
        this.reproduceCounter = reproduceCounter;
    }

    /**
     * Decreases counter by 1.
     */
    public void decreaseReproduceCounter() {
        setReproduceCounter(getReproduceCounter()-1);
    }

    /**
     * Resets the timer.
     */
    public void resetCounter() {
        setReproduceCounter(7);
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

        // reproduce egg
        decreaseReproduceCounter();
        Location location = map.locationOf(this);
        if (canReproduce(location)) {
            reproduce(map, location);
        }

        System.out.println("counter" + getReproduceCounter());
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
     * OmenSheep are curable and thus can have the cure action applied to them
     * When cured bloomed inheritrees are placed around where their initial position is
     * @param curer The Actor doing the curing
     * @param location   location of omensheep
     */
    public void cure(Actor curer, Location location) {
        for (Exit exit : location.getExits()) {
            Location destination = exit.getDestination();
            Inheritree inheritree = new Inheritree("Sheep Inheritree");
            inheritree.bloom();
            destination.setGround(inheritree);
        }
        this.turnCounter = 15;
    }

    @Override
    public boolean canReproduce(Location location) {
        return getReproduceCounter() <= 0;
    }

    @Override
    public void reproduce(GameMap map, Location location) {
        resetCounter();
        location.addItem(new Egg(10));
    }
}
