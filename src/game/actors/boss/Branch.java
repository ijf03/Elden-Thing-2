package game.actors.boss;

import edu.monash.fit2099.engine.displays.Display;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Branch extends BossPart implements Growable {

    private final Random rand = new Random();

    @Override
    public BossPartInterface grow(Display display) {
        BossPartInterface newChild = rand.nextBoolean() ? new Branch() : new Leaf();
        addChild(newChild);
        display.println("The BedOfChaos branch has sprouted a " + (newChild instanceof Leaf ? "Leaf" : "Branch") + ".");
        return newChild;
    }

    @Override
    public int getAttackBonus() {
        return 3;
    }

    @Override
    public int getHealingAmount() {
        return 0;
    }

}
