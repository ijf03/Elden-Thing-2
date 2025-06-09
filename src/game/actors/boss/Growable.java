package game.actors.boss;

import edu.monash.fit2099.engine.displays.Display;

public interface Growable extends BossPartInterface {
   BossPartInterface grow(Display display);
}