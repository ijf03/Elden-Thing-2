package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.*;

import game.actors.behaviours.NPCController;
import game.actors.behaviours.StandardNPCController;
import game.capabilities.Status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Kale extends Merchant implements Speaker{
    /**
     * Integer to keep count of how many turns have gone
     */
    private int turnCounter;
    private final NPCController NPCController;

    public Kale() {
        super("Merchant Kale", 'K', Arrays.asList(
                new KaleBroadswordPurchaseAction(),
                new KaleDragonslayerPurchaseAction()
        ));
        this.NPCController = new StandardNPCController();
    }

    public String ListenAction(Actor player, GameMap map) {
        Random rand = new Random();
        ArrayList<String> monologueOptions = new ArrayList<>();
        for (Exit exit : map.locationOf(this).getExits()) {
            if (map.getActorAt(exit.getDestination()) instanceof Curable || exit.getDestination() instanceof Curable) {
                monologueOptions.add("Rest by the flame when you can, friend. These lands will wear you thin.");
            }
        }
        if (player.getBalance() < 500) {
            monologueOptions.add("Ah, hard times, I see. Keep your head low and your blade sharp.");
        } else if (player.getItemInventory().isEmpty()) {
            monologueOptions.add("Not a scrap to your name? Even a farmer should carry a trinket or two.");
        }
        monologueOptions.add("A merchant’s life is a lonely one. But the roads… they whisper secrets to those who listen.");
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
        actions.add(new ListenAction(this));
        return actions;
    }
}
