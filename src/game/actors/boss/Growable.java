package game.actors.boss;

import edu.monash.fit2099.engine.displays.Display;
/**
 * Interface for boss parts that can grow new children
 */
public interface Growable extends BossPartInterface {
   /**
    * Grows a new child part for this boss part
    * Prints message to the display
    *
    * @param display The game display for outputting messages
    * @return The newly created child part (Branch or Leaf), or null if growth does not occur
    */
   BossPartInterface grow(Display display);
}