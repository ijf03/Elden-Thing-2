package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
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
import game.actors.behaviours.WanderBehaviour;
import game.items.Egg;
import game.magic.MagicItem;
import game.terrain.Inheritree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
     * A timer to keep count of how many turns have gone
     */
    private Turns turnCounter;
    private final NPCController NPCController;
    private Turns reproduceCounter;

    public OmenSheep() {
        super("OmenSheep",'m', 75);
        this.behaviours.put(999, new WanderBehaviour());
        this.turnCounter = new Turns(15);
        this.NPCController = new StandardNPCController();
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
        Location location = map.locationOf(this);
        if (canReproduce(location)) {
            reproduce(map, location);
        }

        if (turnCounter.getTurnCounter() <= 0) {
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
            List<String> spellNames = new ArrayList<>();
            for (Item item : otherActor.getItemInventory()) {
                if (item.hasCapability(Status.MAGIC_SPELL)) {
                    MagicItem spell = (MagicItem) item;
                    String name = spell.getName();
                    if (!spellNames.contains(name)) {
                        actions.add(new CastSpellAction(spell, this));
                        spellNames.add(name);
                    }
                }
            }
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
     * @param location location to check reproduction condition
     * @return true if can reproduce, else false
     */
    @Override
    public boolean canReproduce(Location location) {
        return reproduceCounter.getTurnCounter() <= 0;
    }

    /**
     * Performs the reproduction process.
     * @param map the game map where reproduction occurs
     * @param location location where it reproduces
     */
    @Override
    public void reproduce(GameMap map, Location location) {
        reproduceCounter.resetTurnCounter();
        location.addItem(new Egg(10));
    }
}
