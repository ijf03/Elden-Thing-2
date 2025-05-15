package game;

import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;

public interface Reproducible {
    boolean canReproduce(Location location);
    void reproduce(GameMap map, Location location);
}
