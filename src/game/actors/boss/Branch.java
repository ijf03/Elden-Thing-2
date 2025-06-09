package game.actors.boss;

import edu.monash.fit2099.engine.displays.Display;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents a branch part of the Bed of Chaos boss tree.
 * Branches can grow by randomly adding either another branch or a leaf as a child.
 * Each branch contributes 3 attack bonus and 0 healing.
 */
public class Branch extends BossPart implements Growable {

    private final Random rand = new Random();
    /**
     * randomly Grows a new child part, either a new branch or leaf as a child.
     * Prints a message when growth occurs.
     *
     * @param display The game display for outputting messages
     * @return The new child part (branch or leaf)
     */
    @Override
    public BossPartInterface grow(Display display) {
        BossPartInterface newChild = rand.nextBoolean() ? new Branch() : new Leaf();
        addChild(newChild);
        display.println("The BedOfChaos branch has sprouted a " + (newChild instanceof Leaf ? "Leaf" : "Branch") + ".");
        return newChild;
    }
    /**
     * Returns the attack bonus
     *
     * @return 3 (branches add three attack points each)
     */

    @Override
    public int getAttackBonus() {
        return 3;
    }
    /**
     * Returns the healing amount contributed by this branch (always 0).
     *
     * @return 0 (branches do not heal)
     */
    @Override
    public int getHealingAmount() {
        return 0;
    }

}
