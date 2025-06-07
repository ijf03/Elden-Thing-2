package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actors.ManaManager;
import game.actors.Player;
import game.actors.Speaker;
import game.capabilities.Status;

/**
 * This class handles the listening executions and displays on the menu
 */
public class ListenAction extends Action {
    private Speaker speaker;

    /**
     * Constructor
     * @param speaker the NPC the player
     */
    public ListenAction(Speaker speaker) {
        this.speaker = speaker;
    }

    /**
     * Execution logic for listening is called from the speaker's methods
     * @param player The actor performing the action.
     * @param map The map the actor is on.
     * @return string The chosen string according to speaker's logic
     */
    @Override
    public String execute(Actor player, GameMap map) {
        if (player.hasCapability(Status.PLAYABLE)) {
            ManaManager.onTalk((Player) player);
        }

        return speaker.ListenAction(player, map);
    }

    /**
     * String that shows up on the menu at the start of each turn
     * @param player The actor performing the action.
     * @return string What the choice of action looks like in console
     */
    @Override
    public String menuDescription(Actor player) {
        return "Listen to " + speaker;
    }
}
