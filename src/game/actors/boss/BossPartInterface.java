package game.actors.boss;

import java.util.List;


public interface BossPartInterface {

    int getAttackBonus();

    int getHealingAmount();

    List<BossPartInterface> getChildren();

    void addChild(BossPartInterface child);

}