package game.actors.boss;

import edu.monash.fit2099.engine.displays.Display;

import java.util.Collections;
import java.util.List;

public class Leaf extends BossPart implements Growable {

//    @Override
//    public Growable growResult(Display display) {
//        return null;
//    }

    @Override
    public BossPartInterface grow(Display display) {
        display.println("Leaf cannot grow further.");
        return null;
    }

    @Override
    public int getAttackBonus() {
        return 1;
    }

    @Override
    public int getHealingAmount() {
        return 5;
    }


//    @Override
//    public List<Growable> getChildren() {
//        return Collections.emptyList();
//    }


}
