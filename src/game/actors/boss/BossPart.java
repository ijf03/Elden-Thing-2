package game.actors.boss;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Base implementation of BossPart
 * manages child parts and aggregating their values
 */
public abstract class BossPart implements BossPartInterface {
    private final List<BossPartInterface> children = new ArrayList<>();

    @Override
    public List<BossPartInterface> getChildren() {
        return Collections.unmodifiableList(children);
    }

    @Override
    public void addChild(BossPartInterface child) {
        if (child == null) {
            throw new IllegalArgumentException("Child cannot be null");
        }
        children.add(child);
    }

    @Override
    public int getAttackBonus() {
        return 0;
    }

    @Override
    public int getHealingAmount() {
        return 0;
    }

    /**
     * Recursively calculates the total attack bonus of this part and its descendants
     *
     * @return aggregated attack bonus
     */
    public int getTotalAttackBonus() {
        int total = getAttackBonus();
        for (BossPartInterface child : children) {
            if (child instanceof BossPart) {
                total += ((BossPart) child).getTotalAttackBonus();
            } else {
                total += child.getAttackBonus();
            }
        }
        return total;
    }

    /**
     * Recursively calculates the total healing amount of this part and its descendants
     *
     * @return aggregated healing amount
     */
    public int getTotalHealingAmount() {
        int total = getHealingAmount();
        for (BossPartInterface child : children) {
            if (child instanceof BossPart) {
                total += ((BossPart) child).getTotalHealingAmount();
            } else {
                total += child.getHealingAmount();
            }
        }
        return total;
    }
}
