package game.actors;

import edu.monash.fit2099.engine.actors.Actor;
import game.capabilities.Status;

public class ManaManager {
    /**
     * Award mana when the player attacks a target.
     * @param player the Player who attacked
     * @param target the Actor that was attacked
     */
    public static void onAttack(Player player, Actor target) {
        int manaGain = target.hasCapability(Status.HOSTILE_TO_ENEMY) ? 20 : 10;
        player.addMana(manaGain);
    }

    /**
     * Award mana when the player talks to a merchant or listens.
     * @param player the Player who talked
     */
    public static void onTalk(Player player) {
        player.addMana(3);
    }

    /**
     * Award mana when the player harvests or plants crops.
     * @param player the Player who planted or harvested
     */
    public static void onPlantOrHarvest(Player player) {
        player.addMana(3);
    }

    /**
     * Award passive mana when the player does nothing.
     * @param player the Player who idled
     */
    public static void onIdle(Player player) {
        player.addMana(1);
    }
}
