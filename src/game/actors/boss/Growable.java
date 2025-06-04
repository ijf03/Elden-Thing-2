package game.actors.boss;

import edu.monash.fit2099.engine.displays.Display;

public interface Growable {
    void grow(Display display);
    int getAttackBonus();
    int applyHeal();
}
