package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actors.Speaker;

public class ListenAction extends Action {
    private Speaker speaker;

    public ListenAction(Speaker speaker) {
        this.speaker = speaker;
    }

    // will it turn up as an option for npcs??? is this an issue if they can't execute it anyway??
    @Override
    public String execute(Actor player, GameMap map) {
        return speaker.ListenAction(player, map);
    }

    @Override
    public String menuDescription(Actor player) {
        return "Listen to " + speaker;
    }
}
