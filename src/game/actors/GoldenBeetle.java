package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.Weapon;
import game.actions.AttackAction;
import game.actions.ConsumeAction;
import game.actions.LayEggAction;
import game.actors.behaviours.FollowBehaviour;
import game.actors.behaviours.WanderBehaviour;
import game.actors.behaviours.NPCController;
import game.actors.behaviours.StandardNPCController;
import game.capabilities.Status;
import game.items.Consumable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations.INCREASE;
import static edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes.STAMINA;

/**
 * GoldenBeetle with wandering behaviour
 */
public class GoldenBeetle extends Actor implements Consumable
 {

    /**
     * Behaviour map
     */
    private final Map<Integer, Behaviour> behaviours = new HashMap<>();
    private final NPCController npcController;
    private int turnCounter;

    public GoldenBeetle() {
        super("Golden Beetle", 'b', 25);
        this.behaviours.put(998, new FollowBehaviour());
        behaviours.put(999, new WanderBehaviour());
        this.npcController = new StandardNPCController();
    }

    /**
     * At each turn select a valid action to perform
     * lays egg every 5 turns
     */

    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        turnCounter++;
        if (turnCounter % 5 == 0) {
            return new LayEggAction();
        }
        Action action = npcController.retrieveNextAction(this, map);
        return action != null ? action : new DoNothingAction();
    }
     @Override
     public void consume(Actor actor) {
         actor.heal(15);
         if (actor instanceof Player) {
             ((Player) actor).addBalance(1000);
         }
     }

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
         if (otherActor instanceof game.actors.Player) {
             actions.add(new ConsumeAction(this, "Golden Beetle"));
         }
         return actions;
     }


 }
