package game.capabilities;

/**
 * Use this enum class to represent a status.
 * Example #1: if the player is sleeping, you can attach a Status.SLEEP to the player class
 * @author Riordan D. Alfredo
 */
public enum Status {
    PLAYABLE, // Distinguish between players and NPCs
    HOSTILE_TO_ENEMY,
    PLANTABLE_TERRAIN,
    CURE, // When an actor has an item that has this capability, this is copied up to the actors' list of capabilities
    CURSED,
    FOLLOWABLE, // allows an actor to be followed by the FollowBehaviour
    BLESSED,
    SELLER_SELLEN,
    SELLER_KALE,
    USABLE_WEAPON,
    MAGIC_SPELL, PLAYER
}

