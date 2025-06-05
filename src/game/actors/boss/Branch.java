package game.actors.boss;

import edu.monash.fit2099.engine.displays.Display;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Branch implements Growable {

    private final List<Growable> children = new ArrayList<>();
    private final Random rand = new Random();

    @Override
    public Growable growResult(Display display) {
        Growable newChild = rand.nextBoolean() ? new Branch() : new Leaf();
        children.add(newChild);

        display.println("Branch is growing...");
        display.println("it grows a " + newChild.getClass().getSimpleName() + "...");
        display.println(" ");
        return newChild;
    }

    @Override
    public int getAttackBonus() {
        int bonus = 3;
        for (Growable child : children) {
            bonus += child.getAttackBonus();
        }
        return bonus;
    }

    @Override
    public int getHealingAmount() {
        int totalHeal = 0;
        for (Growable child : children) {
            totalHeal += child.getHealingAmount();
        }
        return totalHeal;
    }

    @Override
    public int countLeaves() {
        int total = 0;
        for (Growable child : children) {
            total += child.countLeaves();
        }
        return total;
    }

    @Override
    public List<Growable> getChildren() {
        return children;
    }
}
