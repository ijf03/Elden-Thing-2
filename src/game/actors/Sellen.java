package game.actors;

import edu.monash.fit2099.engine.positions.Location;
import game.actions.*;
import java.util.Arrays;

public class Sellen extends Merchant {
    public Sellen(Location merchantLocation) {
        super("Merchant Sellen", 'S', Arrays.asList(
                new SellenBroadswordPurchaseAction(),
                new SellenDragonslayerPurchaseAction(),
                new SellenKatanaPurchaseAction(merchantLocation)
        ));
    }
}
