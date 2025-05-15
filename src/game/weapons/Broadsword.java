package game.weapons;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.Weapon;
import game.Sellable;

import game.actors.Player;
import game.capabilities.Status;
import game.items.WeaponItem;

public class Broadsword extends WeaponItem implements Sellable {

    public Broadsword() {
        super("Broadsword", 'b', 30, "slashes", 50);
    }

    @Override
    public int getPrice(Actor merchant) {
        if (merchant.hasCapability(Status.SELLER_SELLEN)) {
            return 100;
        } else if (merchant.hasCapability(Status.SELLER_KALE)) {
            return 150;
        }
        return 0;
    }


    @Override
    public void onPurchase(Player buyer, Actor merchant, GameMap map) {
        int originalHp = buyer.getAttribute(BaseActorAttributes.HEALTH);
        int originalStamina = buyer.getAttribute(BaseActorAttributes.STAMINA);

        if (merchant.hasCapability(Status.SELLER_SELLEN)) {
            buyer.increaseMaxHp(20);
        } else if (merchant.hasCapability(Status.SELLER_KALE)) {
            buyer.increaseMaxStamina(30);
        }

        buyer.modifyAttribute(BaseActorAttributes.HEALTH, ActorAttributeOperations.UPDATE, originalHp);
        buyer.modifyAttribute(BaseActorAttributes.STAMINA, ActorAttributeOperations.UPDATE, originalStamina);
        buyer.heal(10);

    }

    @Override
    public String getName() {
        return "Broadsword";
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