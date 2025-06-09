package game.actors.boss;

import edu.monash.fit2099.engine.displays.Display;

public class Leaf extends BossPart implements Growable {

    @Override
    public BossPartInterface grow(Display display) {
        display.println("Leaf cannot grow further.");
        return null;
    }
    /**
     * Returns the attack bonus
     *
     * @return 1
     */
    @Override
    public int getAttackBonus() {
        return 1;
    }
    /**
     * Returns the healing amount contributed by this leaf
     *
     * @return 5
     */
    @Override
    public int getHealingAmount() {
        return 5;
    }


}
