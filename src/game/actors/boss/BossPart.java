package game.actors.boss;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Abstract class for boss parts of the BedOfChaos
 * implements logic for managing child parts and aggregating attack and healing values
 */
public abstract class BossPart implements BossPartInterface {
    private final List<BossPartInterface> children = new ArrayList<>();

    /**
     * Returns a list of child parts attached to this part
     *
     * @return List of BossPartInterface child components
     */
    @Override
    public List<BossPartInterface> getChildren() {
        return Collections.unmodifiableList(children);
    }

    /**
     * Attaches a new child part to the current part
     *
     * @param child The BossPartInterface to add
     * @throws IllegalArgumentException if child is null
     */
    @Override
    public void addChild(BossPartInterface child) {
        if (child == null) {
            throw new IllegalArgumentException("Child cannot be null");
        }
        children.add(child);
    }
    /**
     * Returns the attack bonus contributed by this part.
     *
     * @return Attack bonus
     */
    @Override
    public int getAttackBonus() {
        return 0;
    }
    /**
     * Returns the healing amount contributed by this part when grown.
     *
     * @return Healing amount value
     */
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
