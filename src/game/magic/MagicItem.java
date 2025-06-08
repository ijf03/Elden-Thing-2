package game.magic;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.capabilities.Status;
import game.items.WeaponItem;

/**
 * Base class for magic weapons
 * carries a mana cost and can be used both as a Spell and as a WeaponItem.
 */
public abstract class MagicItem extends WeaponItem implements Spell {

    private final int manaCost;

    /**
     * @param name        Display name of the magic weapon
     * @param displayChar Character used to render on the ground
     * @param damage      Base damage dealt on hit
     * @param verb        Verb (e.g., "strikes", "zaps")
     * @param hitRate     Hit chance (0-100)
     * @param manaCost    Mana cost to cast as a spell
     */
    protected MagicItem(String name, char displayChar, int damage, String verb, int hitRate, int manaCost) {
        super(name, displayChar, damage, verb, hitRate);
        this.manaCost = manaCost;
        addCapability(Status.MAGIC_SPELL);
    }

    @Override
    public int getManaCost() {
        return manaCost;
    }
}
