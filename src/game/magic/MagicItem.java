package game.magic;

import edu.monash.fit2099.engine.actors.Actor;

import edu.monash.fit2099.engine.positions.GameMap;
import game.items.WeaponItem;
/**
 * Abstract base class for magical weapons that can be used both
 * as standard weapons and as spells.
 * Extends WeaponItem and implements Spell,
 * with a capability of MAGIC_SPELL
 *
 * @see WeaponItem
 * @see Spell
 * @since 1.0
 */
public abstract class MagicItem extends WeaponItem implements Spell{

    private final int manaCost;

    /**
     * Constructor.
     *
     * @param name        name of the item
     * @param displayChar character to use for display when item is on the ground
     * @param damage      amount of damage this weapon does
     * @param verb        verb to use for this weapon, e.g. "hits", "zaps"
     * @param hitRate     the probability/chance to hit the target.
     * @param manaCost    mana required to cast a spell using a specific weapon
     */
    protected MagicItem(String name, char displayChar, int damage, String verb, int hitRate, int manaCost) {
        super(name, displayChar, damage, verb, hitRate);
        this.manaCost = manaCost;
        this.addCapability(game.capabilities.Status.MAGIC_SPELL);
    }

    /**
     * Returns the mana cost required.
     *
     * @return the mana cost
     */
    public int getManaCost() {
        return manaCost;
    }

    /**
     * Returns the ame of this magic weapon.
     *
     * @return the name of the item
     */
    public String getName() {
        return toString();
    }
    /**
     * Using the magic item to cast a spell and apply special effect to the target.
     *
     * @param caster the actor cast the spell
     * @param target the actor affected by the spell
     * @param map the current game map
     * @return a string describe the spell’s outcome
     */
    public abstract String cast(Actor caster, Actor target, GameMap map);

}