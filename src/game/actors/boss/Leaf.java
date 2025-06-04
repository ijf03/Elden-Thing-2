package game.actors.boss;

import edu.monash.fit2099.engine.displays.Display;

class Leaf implements Growable {

    @Override
    public int getAttackBonus() {
        return 1;
    }

    @Override
    public int applyHeal() {
        return 5;
    }
}
