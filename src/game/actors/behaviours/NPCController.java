package game.actors.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * Each NPC should have an association with an NPCController
 * For instance SpiritGoat and OmenSheep use the Standard NPCController object
 */
public interface NPCController {
    /**
     * This retrieves the next action for the actor according to the behaviours it should exhibit
     * @param actor actor that is taking the action
     * @param map where the actor is
     * @return the next action
     */
    Action retrieveNextAction(Actor actor, GameMap map);

    void newBehaviour(Behaviour behaviour, Integer priority);
}
