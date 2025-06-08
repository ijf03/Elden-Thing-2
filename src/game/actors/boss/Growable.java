package game.actors.boss;

import edu.monash.fit2099.engine.displays.Display;

import java.util.List;

//public interface Growable {
//    Growable growResult(Display display);
//    int getAttackBonus();
//    int getHealingAmount();
//    int countLeaves();
//    List<Growable> getChildren();
//    void grow(Display display);
//}

public interface Growable extends BossPartInterface {
   BossPartInterface grow(Display display);
}