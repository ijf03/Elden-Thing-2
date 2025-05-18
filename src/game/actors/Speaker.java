package game.actors;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * Interface for NPCs that can be listened to (Speakers)
 */
public interface Speaker {
    /**
     * Each speaker has their own rules for what they say
     * @param player that listens to the speaker
     * @param map where they are
     * @return string that shows up when they are listened to
     */
    String ListenAction(Actor player, GameMap map);
}
