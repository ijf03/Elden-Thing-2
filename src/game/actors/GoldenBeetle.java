package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.Reproducible;
import game.actions.AttackAction;
import game.actions.ConsumeAction;
import game.actions.ListenAction;
import game.actors.behaviours.*;
import game.capabilities.Status;
import game.items.Consumable;
import game.items.GoldenEgg;

import java.util.HashMap;
import java.util.Map;

import static game.actions.AttackUtils.addSpellActions;
import static game.actions.AttackUtils.addWeaponActions;

/**
 * GoldenBeetle with wandering behaviour and a follow behaviour to follow the player. It can also lay eggs and can be consumed for health and runes.
 */
public class GoldenBeetle extends Creature implements Consumable, Reproducible, Speaker
{

    /**
     * Behaviour map with priority by hashmap
     */
    private final Map<Integer, Behaviour> behaviours = new HashMap<>();
    /**
     * NPC Controller that determines what action will be done
     */
    private NPCController npcController;
    /**
     * Counts how many turns the beetle been alive. its used to control how often an egg is layed
     */
    private int turnCounter;
    /**
     * Constructor if behaviour selection style is chosen
     * @param status behaviour style
     */

    public GoldenBeetle(Status status) {
        super("Golden Beetle", 'b', 25, status);
        // this.behaviours.put(998, new FollowBehaviour());
        // behaviours.put(999, new WanderBehaviour());
        this.npcController = setBehaviourStyle();
        npcController.newBehaviour(new WanderBehaviour(), 999);
        npcController.newBehaviour(new FollowBehaviour(), 1);
        npcController.newBehaviour(new ReproduceBehaviour(this), 2);
    }

    /**
     * Constructor for the Golden Beetle. Initializes its name, display character "b", its health and behaviours
     */
    public GoldenBeetle() {
        super("Golden Beetle", 'b', 25, Status.NORMAL_BEHAVIOUR);
        // this.behaviours.put(998, new FollowBehaviour());
        // behaviours.put(999, new WanderBehaviour());
        this.npcController = setBehaviourStyle();
        npcController.newBehaviour(new WanderBehaviour(), 999);
        npcController.newBehaviour(new FollowBehaviour(), 1);
        npcController.newBehaviour(new ReproduceBehaviour(this), 2);
    }

    /**
     * At each turn select a valid action to perform
     * lays egg every 5 turns
     * @param actions Collection of possible actions
     * @param lastAction Last action taken
     * @param display Display object for text output
     * @param map Current game map
     * @return An Action to perform
     */

    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        turnCounter++;
//        Location location = map.locationOf(this);
//        if (canReproduce(location)) {
//            reproduce(map, location);
//        }

        return npcController.retrieveNextAction(this, map); // null check already in method
    }

    /**
     * Used when the player consumes this GoldenBeetle.
     * Heals the player by 15 HP and gives 1000 runes to a Player.
     *
     * @param actor The actor who consumed this item
     */
    @Override
    public void consume(Actor actor, GameMap map) {
        map.removeActor(this);
        actor.heal(15);
        map.getActorAt(map.locationOf(actor)).addBalance(1000);
    }

    /**
     * Returns a list of actions that an actor can perform on this beetle.
     * Includes attacking and consuming if the actor is the player.
     *
     * @param otherActor The actor interacting with this beetle
     * @param direction Direction relative to the actor
     * @param map Game map
     * @return List of allowable actions
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = new ActionList();
        if (otherActor.hasCapability(Status.HOSTILE_TO_ENEMY)) {
            actions.add(new AttackAction(this, direction, otherActor.getIntrinsicWeapon()));
            addWeaponActions(otherActor, this, direction, actions);
            addSpellActions( otherActor,this, actions);
        }
        if (otherActor.hasCapability(Status.PLAYABLE)) {
            actions.add(new ConsumeAction(this));
        }
        if (canSpeak()) {
            actions.add(new ListenAction(this));
        }
        return actions;
    }

    /**
     * Checks whether the GoldenBeetle can reproduce.
     * @param map location to check reproduction condition
     * @return true if can reproduce, else false
     */
    @Override
    public boolean canReproduce(GameMap map) {
        return this.turnCounter % 5 == 0;
    }

    /**
     * Performs the reproduction process.
     * @param map the game map where reproduction occurs
     * @param map  location where it reproduces
     */
    @Override
    public void reproduce(GameMap map) {
        Location location = map.locationOf(this);
        location.addItem(new GoldenEgg());
    }

    /**
     * Hurts GoldenBeetle with the given damage points.
     * @param damage the damage points that the actor receives
     */
    @Override
    public void hurt(int damage) {
        boolean isInvincible = false;
        if (this.hasCapability(Status.INVINCIBLE)) {
            isInvincible = true;
        }

        if (isInvincible) {
            System.out.println(this + " is Invincible and can't be hurt!");
        } else {
            super.hurt(damage);
        }
    }

    /**
     * The rules for what the GoldenBeetle would say.
     * @param player that listens to the speaker
     * @param map where they are
     * @return a string that displays their message
     */
    @Override
    public String ListenAction(Actor player, GameMap map) {
        String result;
        if (this.turnCounter % 5 <= 2) {
            result = "A beetle egg will be laid soon!";
        } else {
            result = "Safe travels friend.";
        }
        return this + "~~~ " + result;
    }

    /**
     * Checks if GoldenBeetle is currently able to speak.
     * @return true if condition is met, otherwise false
     */
    @Override
    public boolean canSpeak() {
        return this.hasCapability(Status.SPEAK);
    }
}
