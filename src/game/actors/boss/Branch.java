package game.actors.boss;

import edu.monash.fit2099.engine.displays.Display;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Branch implements Growable {

    private final List<Growable> children = new ArrayList<>();
    private final Random rand = new Random();


    @Override
    public int getAttackBonus() {
        int bonus = 3;
        for (Growable child : children) {
            bonus += child.getAttackBonus();
        }
        return bonus;
    }

    @Override
    public int applyHeal() {
        int totalHeal = 0;
        for (Growable child : children) {
            totalHeal += child.applyHeal();
        }
        return totalHeal;
    }

    public List<Growable> getChildren() {
        return children;
    }
    public Growable growWithResult(Display display) {
        Growable newChild;

        display.println("Branch is growing...");

        if (rand.nextBoolean()) {
            newChild = new Branch();
            display.println("it grows a Branch...");
        } else {
            newChild = new Leaf();
            display.println("it grows a Leaf...");
        }

        children.add(newChild);
        return newChild;
    }


}
