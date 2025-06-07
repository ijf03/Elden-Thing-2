package game.magic;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.DropAction;

import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.items.PickUpAction;
import edu.monash.fit2099.engine.positions.GameMap;
import game.items.WeaponItem;

public abstract class MagicItem extends WeaponItem {

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
        this.addCapability(game.capabilities.Status.MAGIC_SPELL);
    }

    /**
     * @return mana cost required to cast this spell
     */
    public int getManaCost() {
        return manaCost;
    }

    /**
     * @return display name of this magic weapon
     */
    public String getName() {
        return toString();
    }
    /**
     * Casts this weapon as a spell effect, applying its special logic.
     * @param caster the actor casting the spell
     * @param target the actor receiving the effect
     * @param map    the game map context
     * @return descriptive text of the effect
     */
    public abstract String cast(Actor caster, Actor target, GameMap map);

}