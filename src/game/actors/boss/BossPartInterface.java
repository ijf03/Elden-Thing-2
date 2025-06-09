package game.actors.boss;

import java.util.List;

/**
 * Interface for the parts of the BedOfChaos
 * includes methods for attack bonus, healing, and managing child parts.
 */
public interface BossPartInterface {
    /**
     * Returns the attack bonus contributed by the part
     *
     * @return Attack bonus value
     */
    int getAttackBonus();
    /**
     * Returns the healing amount contributed when part grows.
     *
     * @return Healing amount value
     */
    int getHealingAmount();
    /**
     * Return list of child parts attached
     *
     * @return List of BossPartInterface child components
     */
    List<BossPartInterface> getChildren();
    /**
     * Attaches new child part
     *
     * @param child The BossPartInterface to add
     * @throws IllegalArgumentException if child is null
     */
    void addChild(BossPartInterface child);

}