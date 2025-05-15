package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actors.GoldenBeetle;
import game.actors.Player;

public class ConsumeGoldenBeetleAction extends Action {

    private final GoldenBeetle beetle;

    public ConsumeGoldenBeetleAction(GoldenBeetle beetle) {
        this.beetle = beetle;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        Player player = (Player) actor;
        map.removeActor(beetle);
        player.heal(15);
        player.addBalance(1000);
        return "You consume the Golden Beetle. +15 HP, +1000 Runes.";
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " consumes the Golden Beetle nearby";
    }
}
