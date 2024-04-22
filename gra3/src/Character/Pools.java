package Character;

import Enemies.Enemy;
import Enemies.Functions;
import Items.Armor;
import Items.Consumable;
import Items.Consumables.HealthPotion;
import Items.Consumables.ManaPotion;
import Items.Item;
import Items.Weapon;
import Keys.Keys;
import Spells.Icicle;
import Spells.Spell;

import java.util.HashMap;

public class Pools {
    public static HashMap<Keys, Item> itemPool = new HashMap<>();
    public static HashMap<Keys, Weapon> weaponPool = new HashMap<>();
    public static HashMap<Keys, Armor> armorPool = new HashMap<>();
    public static HashMap<Keys, Consumable> consumablePool = new HashMap<>();
    public static HashMap<Keys, Spell> spellPool = new HashMap<>();
    public static HashMap<Keys, Enemy> enemyPool = new HashMap<>();

    public static void initiateAllPools() {
        weaponPool.put(Keys.SWORD, new Weapon("Sword", 80, 7, 4, 9, 0));
        weaponPool.put(Keys.GREATAXE, new Weapon("Greataxe", 125, 13, 7, 13, 0));
        weaponPool.put(Keys.BOW, new Weapon("Bow", 60, 4, 3, 11, 0));
        weaponPool.put(Keys.STAFF, new Weapon("Staff", 100, 4, 5, 8, 2));

        armorPool.put(Keys.LEATHER_ARMOR, new Armor("Leather Armor", 120, 8, 9));
        armorPool.put(Keys.CHAIN_MAIL_ARMOR, new Armor("Chain Mail Armor", 160, 13, 12));
        armorPool.put(Keys.IRON_PLATE_ARMOR,new Armor("Iron Plate Armor", 200, 18, 15));
        armorPool.put(Keys.DRAGON_SCALE_ARMOR,new Armor("Dragon Scale Armor", 450, 15, 18));

        consumablePool.put(Keys.HEALTH_POTION, new HealthPotion("Health Potion", 50, 0.2, 10));
        consumablePool.put(Keys.MANA_POTION, new ManaPotion("Mana Potion", 50, 0.2, 10));

        itemPool.putAll(weaponPool);
        itemPool.putAll(armorPool);
        itemPool.putAll(consumablePool);

        spellPool.put(Keys.ICICLE, new Icicle("Icicle", "Cast an icicle that damages one enemy and lowers their armor rating", 4, 7, 5, false));
        spellPool.put(Keys.FIREBOLT, new Icicle("Firebolt", "Cast a bolt of fire that damages one enemy", 8, 14, 5, false));

        Functions.refreshEnemyPool();
    }
}
