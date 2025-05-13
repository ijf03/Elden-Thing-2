package game;

import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.positions.NumberRange;
import java.util.ArrayList;
import java.util.List;

/**
 * Retrieving nearby tiles around a central location on the map.
 */
public class TileLocation{

    /**
     * Returns a list of all tiles adjacent to a center location, including diagonals.
     *
     * @param map the GameMap where the search occurs
     * @param center the central Location from which nearby tiles are found
     * @return a list of adjacent Locations (including diagonals)
     * as seen in:
     * https://www.geeksforgeeks.org/depth-first-traversal-dfs-on-a-2d-array/
     */
    public static List<Location> getNearbyTiles(GameMap map, Location center) {
        List<Location> nearbyTiles = new ArrayList<>();
        NumberRange xRange = map.getXRange();
        NumberRange yRange = map.getYRange();

        for (int x = center.x() - 1; x <= center.x() + 1; x++) {
            for (int y = center.y() - 1; y <= center.y() + 1; y++) {
                if (xRange.contains(x) && yRange.contains(y)) {
                    nearbyTiles.add(map.at(x, y));
                }
            }
        }
        return nearbyTiles;
    }
}
