package game.magic;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.DropAction;

import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.items.PickUpAction;
import edu.monash.fit2099.engine.positions.GameMap;


public abstract class MagicItem extends Item {

    private final int manaCost;

    protected MagicItem(String name, char displayChar, boolean portable, int manaCost) {
        super(name, displayChar, portable);
        this.manaCost = manaCost;
    }

    public int getManaCost() {
        return manaCost;
    }

    public abstract String cast(Actor caster, Actor target, GameMap map);

    @Override
    public PickUpAction getPickUpAction(Actor actor) {
        return new PickUpAction(this);
    }

    @Override
    public DropAction getDropAction(Actor actor) {
        return new DropAction(this);
    }

    public String getName() {
        return super.toString();
    }
}