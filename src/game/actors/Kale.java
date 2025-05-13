package game.actors;

import game.actions.*;
import java.util.Arrays;

public class Kale extends Merchant {
    public Kale() {
        super("Merchant Kale", 'K', Arrays.asList(
                new KaleBroadswordPurchaseAction(),
                new KaleDragonslayerPurchaseAction()
        ));
    }
}
