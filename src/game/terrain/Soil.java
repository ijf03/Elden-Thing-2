package game.terrain;

import edu.monash.fit2099.engine.positions.Ground;
import game.capabilities.Status;

/**
 * A class representing the soil in the valley
 * @author Adrian Kristanto
 */
public class Soil extends Ground {
    public Soil() {
        super('.', "Soil");
        addCapability(Status.PLANTABLE_TERRAIN); // farmer can only plant if its soil
    }
}
