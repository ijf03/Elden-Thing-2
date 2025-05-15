package game.weapons;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.Sellable;
import game.actors.OmenSheep;
import game.actors.Player;
import game.capabilities.Status;
import game.items.WeaponItem;

public class Katana extends WeaponItem implements Sellable {

    public Katana() {
        super("Katana", 'j', 50, "slashes", 60);
    }

    @Override
    public int getPrice(Actor merchant) {
        return 500;
    }

    @Override
    public void onPurchase(Player buyer, Actor merchant, GameMap map) {
        buyer.hurt(25);

        if (merchant.hasCapability(Status.SELLER_SELLEN)) {
            Location sellerLocation = map.locationOf(merchant);

            for (Exit exit : sellerLocation.getExits()) {
                Location dest = exit.getDestination();

                Display display = new Display();

                if (!dest.containsAnActor() && dest.getGround().hasCapability(Status.PLANTABLE_TERRAIN)) {
                    display.println("Spawning Omen Sheep at: " + dest);
                    map.addActor(new OmenSheep(), dest);
                    break;
                }
            }

            buyer.heal(10);
            int originalStamina = buyer.getAttribute(BaseActorAttributes.STAMINA);
            buyer.increaseMaxStamina(20);
            buyer.modifyAttribute(BaseActorAttributes.STAMINA, ActorAttributeOperations.UPDATE, originalStamina);
        }
    }


    @Override
    public String getName() {
        return "Katana";
    }

    @Override
    public boolean isWeapon() {
        return true;
    }

    @Override
    public Item asItem() {
        return this;
    }
}