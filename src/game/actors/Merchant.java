package game.actors;
import game.Sellable;

import java.util.List;

public interface Merchant {
    List<Sellable> getSellableItems();
    void removeSoldItem(Sellable item);
}
