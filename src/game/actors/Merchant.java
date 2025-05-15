package game.actors;

import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;


import java.util.List;

public abstract class Merchant extends Actor {

    protected final List<Action> inventory;

    public Merchant(String name, char displayChar, int hitPoints, List<Action> inventory) {
        super(name, displayChar, hitPoints); // Can choose hitpoints of merchant
        this.inventory = inventory;
        this.addCapability(Actors.MERCHANT);
    }

    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = new ActionList();
        for (Action action : inventory) {
            actions.add(action);
        }
        return actions;
    }
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        return new DoNothingAction();
    }

}
