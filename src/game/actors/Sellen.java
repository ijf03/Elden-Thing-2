package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import game.Sellable;
import game.actions.*;
import game.actors.behaviours.NPCController;
import game.actors.behaviours.StandardNPCController;
import game.capabilities.Status;
import game.weapons.Broadsword;
import game.weapons.DragonslayerGreatsword;
import game.weapons.Katana;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Sellen extends Actor implements Speaker, Merchant {
    /**
     * Integer to keep count of how many turns have gone
     */
    private final NPCController NPCController;
    private final List<Sellable> items;

    public Sellen() {
        super("Sellen",'s', 150);
        this.NPCController = new StandardNPCController();
        this.addCapability(Status.SELLER_SELLEN);
        this.addCapability(Status.MERCHANT);

        items = new ArrayList<>();
        items.add(new Broadsword());
        items.add(new DragonslayerGreatsword());
        items.add(new Katana());


    }
    @Override
    public List<Sellable> getSellableItems() {
        return items;
    }
    @Override
    public void removeSoldItem(Sellable item) {
        items.remove(item);
    }


    public String ListenAction(Actor player, GameMap map) {
        Random rand = new Random();
        ArrayList<String> monologueOptions = new ArrayList<>();
        monologueOptions.add("The academy casts out those it fears. Yet knowledge, like the stars, cannot be bound forever.");
        monologueOptions.add("You sense it too, don’t you? The Glintstone hums, even now.");
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
        if (otherActor.hasCapability(Status.HOSTILE_TO_ENEMY)) {
            actions.add(new AttackAction(this, direction));

            }
            if (otherActor.hasCapability(Status.PLAYABLE)) {
                actions.add(new ListenAction(this));

            }
            for (Sellable item : this.getSellableItems()) {
                actions.add(new PurchaseAction(item, this));}
            return actions;

    }
}
