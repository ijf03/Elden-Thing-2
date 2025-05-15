package game.weapons;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.Sellable;

import game.actors.GoldenBeetle;
import game.actors.Player;
import game.capabilities.Status;
import game.items.WeaponItem;

public class DragonslayerGreatsword extends WeaponItem implements Sellable {

    public DragonslayerGreatsword() {
        super("Dragonslayer Greatsword", 'D', 70, "cleaves", 75);
    }

    @Override
    public int getPrice(Actor merchant) {
        if (merchant.hasCapability(Status.SELLER_SELLEN)) {
            return 1500;
        } else if (merchant.hasCapability(Status.SELLER_KALE)) {
            return 1700;
        }
        return 0;
    }

    @Override
    public void onPurchase(Player buyer, Actor merchant, GameMap map) {
        int originalHp = buyer.getAttribute(BaseActorAttributes.HEALTH);

        buyer.increaseMaxHp(15);

        if (merchant.hasCapability(Status.SELLER_SELLEN)) {
            // Spawn Golden Beetle near the buyer
            Location here = map.locationOf(buyer);
            for (Exit exit : here.getExits()) {
                Location destination = exit.getDestination();
                if (!destination.containsAnActor()) {
                    map.addActor(new GoldenBeetle(), destination);
                    break;
                }
            }
        } else if (merchant.hasCapability(Status.SELLER_KALE)) {
            buyer.restoreStamina(20);
        }
        buyer.modifyAttribute(BaseActorAttributes.HEALTH, ActorAttributeOperations.UPDATE, originalHp);
    }

    @Override
    public String getName() {
        return "Dragonslayer Greatsword";
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