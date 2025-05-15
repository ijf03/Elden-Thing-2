package game;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;

import game.actors.Player;

public interface Sellable {
    String getName();
    int getPrice(Actor merchant);
    void onPurchase(Player buyer, Actor merchant, GameMap map);
    boolean isWeapon();
    Item asItem();

}

