package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actors.behaviours.NPCController;
import game.actors.behaviours.RandomNPCController;
import game.actors.behaviours.StandardNPCController;
import game.capabilities.Status;

abstract class Creature extends Actor {
    private Status behaviourStyle = Status.NORMAL_BEHAVIOUR;
    protected NPCController npcController;

    /**
     * The constructor of the Actor class.
     *
     * @param name        the name of the Actor
     * @param displayChar the character that will represent the Actor in the display
     * @param hitPoints   the Actor's starting hit points
     */
    public Creature(String name, char displayChar, int hitPoints, Status status) {
        super(name, displayChar, hitPoints);
        behaviourStyle = status;
    }

    public NPCController setBehaviourStyle() {
        if (behaviourStyle == Status.NORMAL_BEHAVIOUR) {
            this.npcController = new StandardNPCController();
        } else {
            this.npcController = new RandomNPCController();
        }
        return npcController;
    }

    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        return null;
    }
}
