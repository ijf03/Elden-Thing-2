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
import game.Curable;
import game.Reproducible;
import game.Turns;
import game.actions.AttackAction;
import game.actions.CureAction;
import game.actors.behaviours.NPCController;
import game.actors.behaviours.ReproduceBehaviour;
import game.actors.behaviours.StandardNPCController;
import game.capabilities.Status;
import game.actors.behaviours.WanderBehaviour;
import game.items.Egg;
import game.terrain.Inheritree;

import java.util.HashMap;
import java.util.Map;

import static game.actions.AttackUtils.addSpellActions;
import static game.actions.AttackUtils.addWeaponActions;

/**
 * Class representing the NPC OmenSheep which roams around
 * @author Trinh Nguyen
 */
public class OmenSheep extends Creature implements Curable, Reproducible {

    /**
     * A Hashmap storing all the behaviours
     */
    private Map<Integer, Behaviour> behaviours = new HashMap<>();

    /**
     * A timer to keep count of how many turns have gone
     */
    private Turns turnCounter;
    private Turns reproduceCounter;
    private final NPCController NPCController;

    /**
     * Constructor with all the initial behaviours
     */
    public OmenSheep() {
        super("OmenSheep",'m', 75, Status.NORMAL_BEHAVIOUR);
        this.turnCounter = new Turns(15);
        this.NPCController = new StandardNPCController();
        NPCController.newBehaviour(new WanderBehaviour(), 999);
        NPCController.newBehaviour(new ReproduceBehaviour(this), 1);
        this.reproduceCounter = new Turns(7);
    }

    /**
     * Constructor if behaviour selection style is chosen
     * @param status behaviour style
     */
    public OmenSheep(Status status) {
        super("OmenSheep",'m', 75, status);
        //this.behaviours.put(999, new WanderBehaviour());
        this.turnCounter = new Turns(15);
        this.NPCController = setBehaviourStyle();
        NPCController.newBehaviour(new WanderBehaviour(), 999);
        NPCController.newBehaviour(new ReproduceBehaviour(this), 1);
        this.reproduceCounter = new Turns(7);
    }

    /**
     * At each turn, select a valid action to perform.
     * Reproduces an egg once the condition to reproduce is met.
     *
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return the valid action that can be performed in that iteration or null if no valid action is found
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        turnCounter.decreaseCounter();

        // reproduce egg
        reproduceCounter.decreaseCounter();
//        Location location = map.locationOf(this);
//        if (canReproduce(location)) {
//            reproduce(map, location);
//        }
        return NPCController.retrieveNextAction(this, map);
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
        if (otherActor.hasCapability(Status.HOSTILE_TO_ENEMY)) {
            actions.add(new AttackAction(this, direction, otherActor.getIntrinsicWeapon()));
            addWeaponActions(otherActor, this, direction, actions);
            addSpellActions( otherActor,this, actions);
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
        turnCounter.resetTurnCounter();
    }

    /**
     * Checks whether the OmenSheep can reproduce.
     * @param map to check reproduction condition
     * @return true if can reproduce, else false
     */
    @Override
    public boolean canReproduce(GameMap map) {
        return reproduceCounter.getTurnCounter() % 7 == 0;
    }

    /**
     * Performs the reproduction process.
     * @param map the game map where reproduction occurs
     */
    @Override
    public void reproduce(GameMap map) {
        reproduceCounter.resetTurnCounter();
        Location location = map.locationOf(this);
        location.addItem(new Egg(10));
    }
}
