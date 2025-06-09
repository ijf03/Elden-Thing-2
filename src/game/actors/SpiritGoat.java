package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.Weapon;
import game.Curable;
import game.Reproducible;
import game.Turns;
import game.actions.AttackAction;
import game.actions.CureAction;
import game.actions.ListenAction;
import game.actors.behaviours.NPCController;
import game.actors.behaviours.ReproduceBehaviour;
import game.actors.behaviours.StandardNPCController;
import game.actors.behaviours.WanderBehaviour;
import game.capabilities.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class representing the NPC SpritGoat which roams around
 * @author Trinh Nguyen
 */
public class SpiritGoat extends Creature implements Curable, Reproducible, Speaker {


    /**
     * A timer to keep count of how many turns have gone
     */
    private Turns turnCounter;
    private final NPCController NPCController;
    private Turns reproduceCooldown;

    /**
     * Constructor.
     */
    public SpiritGoat() {
        super("SpiritGoat",'y', 50, Status.NORMAL_BEHAVIOUR);
        this.turnCounter = new Turns(10);
        this.NPCController = new StandardNPCController();
        NPCController.newBehaviour(new WanderBehaviour(), 999);
        NPCController.newBehaviour(new ReproduceBehaviour(this), 1);
        this.reproduceCooldown = new Turns(0);
    }

    public SpiritGoat(Status status) {
        super("SpiritGoat",'y', 50, status);
        this.turnCounter = new Turns(10);
        this.NPCController = setBehaviourStyle();
        NPCController.newBehaviour(new WanderBehaviour(), 999);
        NPCController.newBehaviour(new ReproduceBehaviour(this), 1);
        this.reproduceCooldown = new Turns(0);
    }

    /**
     * Constructor for SpiritGoat offsprings.
     * Has a reproduction cooldown to restrict it from reproducing one born.
     * @param cooldown a turns counter
     */
    public SpiritGoat(Turns cooldown) {
        super("SpiritGoat",'y', 50, Status.NORMAL_BEHAVIOUR);
        this.turnCounter = new Turns(10);
        this.NPCController = new StandardNPCController();
        this.reproduceCooldown = cooldown;
    }

    /**
     * At each turn, select a valid action to perform.
     * Reproduces an offspring once the condition to reproduce is met.
     *
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return the valid action that can be performed in that iteration or null if no valid action is found
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        if (!this.hasCapability(Status.INVINCIBLE)) {
            turnCounter.decreaseCounter();
        }

        if (reproduceCooldown.getTurnCounter() > 0) {
            reproduceCooldown.decreaseCounter();
//            System.out.println(reproduceCooldown.getTurnCounter());
        }
//        System.out.println("turn" + turnCounter.getTurnCounter());

        if (turnCounter.getTurnCounter() <= 0) {
            map.removeActor(this);
            return new DoNothingAction();
        }
        // Check for nearby blessed entities (like Inheritree) and try to reproduce
//        Location location = map.locationOf(this);
//        if (canReproduce(location) && reproduceCooldown.getTurnCounter() <= 0) {
//            reproduce(map, location);
//        }
        return NPCController.retrieveNextAction(this, map);
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
        if (otherActor.hasCapability(Status.HOSTILE_TO_ENEMY)) {
            actions.add(new AttackAction(this, direction, otherActor.getIntrinsicWeapon()));

            List<String> weaponNames = new ArrayList<>();

            for (Item item : otherActor.getItemInventory()) {
                if (item.hasCapability(Status.USABLE_WEAPON)) {
                    Weapon weapon = (Weapon) item;
                    String name = weapon.toString();

                    if (!weaponNames.contains(name)) {
                        actions.add(new AttackAction(this, direction, weapon));
                        weaponNames.add(name);
                    }
                }
            }
        }
        if (otherActor.hasCapability(Status.CURE)){
            actions.add(new CureAction(this, map.locationOf(this), direction));
        }
        if (canSpeak()) {
            actions.add(new ListenAction(this));
        }
        return actions;
    }

    /**
     * spritigoat curing involves resetting the turncounter back to 10
     * @param curer  actor doing the curing
     * @param location location of goat
     */
    public void cure(Actor curer, Location location) {
        turnCounter.resetTurnCounter();
    }

    /**
     * Checks whether its surrounding has entities that are blesses, so that
     * the SpiritGoat can reproduce.
     * @param map location to check reproduction condition
     * @return true if can reproduce, else false
     */
    @Override
    public boolean canReproduce(GameMap map) {
        Location location = map.locationOf(this);
        for (Exit exit : location.getExits()) {
            Location destination = exit.getDestination();
            if (destination.getGround().hasCapability(Status.BLESSED)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Performs the reproduction process.
     * Produces an offspring on a valid location around it.
     * @param map the game map where reproduction occurs
     */
    @Override
    public void reproduce(GameMap map) {
        // store list of valid locations
        ArrayList<Location> exits = new ArrayList<Location>();
        Location location = map.locationOf(this);
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
            // offspring has a timer before it can reproduce
            SpiritGoat offspring = new SpiritGoat(new Turns(3));
            spawn.addActor(offspring);
        }
    }

    /**
     * Hurts SpiritGoat with the given damage points.
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
     * The rules for what the SpiritGoat would say.
     * @param player that listens to the speaker
     * @param map where they are
     * @return a string that displays their message
     */
    @Override
    public String ListenAction(Actor player, GameMap map) {
        String result;
        if (this.turnCounter.getTurnCounter() < 5) {
            result = "The rot is unforgiving but I would hope you are not, please friend save me if not for a few days more.";
        } else {
            result = "Safe travels friend.";
        }
        return this + "~~~ " + result;
    }

    /**
     * Checks if SpiritGoat is currently able to speak.
     * @return true if condition is met, otherwise false
     */
    @Override
    public boolean canSpeak() {
        return this.hasCapability(Status.SPEAK);
    }
}
