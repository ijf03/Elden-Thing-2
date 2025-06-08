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
import game.actions.CastSpellAction;
import game.actions.CureAction;
import game.actors.behaviours.NPCController;
import game.actors.behaviours.StandardNPCController;
import game.capabilities.Status;
import game.magic.MagicItem;
import game.magic.SpellUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class representing the NPC SpritGoat which roams around
 * @author Trinh Nguyen
 */
public class SpiritGoat extends Actor implements Curable, Reproducible {


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
        super("SpiritGoat",'y', 50);
        this.turnCounter = new Turns(10);
        this.NPCController = new StandardNPCController();
        this.reproduceCooldown = new Turns(0);
    }

    /**
     * Constructor for SpiritGoat offsprings.
     * Has a reproduction cooldown to restrict it from reproducing one born.
     * @param cooldown a turns counter
     */
    public SpiritGoat(Turns cooldown) {
        super("SpiritGoat",'y', 50);
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
        turnCounter.decreaseCounter();
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
        Location location = map.locationOf(this);
        if (canReproduce(location) && reproduceCooldown.getTurnCounter() <= 0) {
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

            SpellUtils.addCastSpellActions(actions, otherActor, this);
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
        turnCounter.resetTurnCounter();
    }

    /**
     * Checks whether its surrounding has entities that are blesses, so that
     * the SpiritGoat can reproduce.
     * @param location location to check reproduction condition
     * @return true if can reproduce, else false
     */
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

    /**
     * Performs the reproduction process.
     * Produces an offspring on a valid location around it.
     * @param map the game map where reproduction occurs
     * @param location location where it reproduces
     */
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
            // offspring has a timer before it can reproduce
            SpiritGoat offspring = new SpiritGoat(new Turns(3));
            spawn.addActor(offspring);
        }
    }
}
