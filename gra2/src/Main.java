import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import Enemies.Enemy;
import Enemies.Functions;
import Items.*;
import Items.Consumables.HealthPotion;
import Items.Consumables.ManaPotion;
import Character.Character;
import Character.CharacterClass;
import Character.Levels;
import Keys.Keys;
import Spells.Icicle;
import Spells.Spell;

public class Main {
    public static final HashMap<Keys, Item> itemPool = new HashMap<>();
    public static final HashMap<Keys, Weapon> weaponPool = new HashMap<>();
    public static final HashMap<Keys, Armor> armorPool = new HashMap<>();
    public static final HashMap<Keys, Consumable> consumablePool = new HashMap<>();
    public static final HashMap<Keys, Spell> spellPool = new HashMap<>();
    public static final HashMap<Keys, Enemy> enemyPool = new HashMap<>();

    static void initiateAllPools() {
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

        Functions.refreshEnemyPool(enemyPool, weaponPool);
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        initiateAllPools();
        Character player = new Character();
        String[] characterArr;
        String[] spellsArr;
        int iterator = 0;
        String choice = "";

        try {
            File file = new File("Character.txt");
            file.createNewFile();
            Scanner fileReader = new Scanner(file);
            if (file.length() != 0) {
                characterArr = fileReader.nextLine().split(";");

                System.out.println("Load your character: ");
                System.out.println("1. " + characterArr[0]);
                System.out.println("0. Create new character");
                choice = input.next();
                if (choice.equals("1"))
                    loadCharacter(player, characterArr, fileReader.nextLine().split(";"));
            }
            if (choice.equals("0") || choice.equals("")) {
                player = new Character(weaponPool, spellPool);
                player.saveCharacter();
            }

            if (!player.pastIntro) {
                player.printAllInfo();
                System.out.println();
                System.out.println();
                System.out.println();

                System.out.println("You wake up on a battlefield among what seems like thousands of fallen soldiers. You don't remember much. Except for your name.");
                System.out.println("As you stand up you get a little light headed, looking around your surroundings you realise you are in the middle of a desert. On the horizon you spot an oasis.");
                System.out.println("Stumbling over corpses of fallen warriors you begin to walk in a direction of the oasis. While getting closer to your destination you think about what happened here, and why am i the only one that's survived and... .");
                System.out.println("Your thoughts are interrupted by a strange noise beneath your feet. You decide not to investigate and keep on walking very disturbed. It takes much of your remaining strength but you finally get there.");
                System.out.println("You kneel to drink the water from the oasis lake. You drink until your thirst is quenched and as you feel satisfied you pass out from the heat of the desert day. You wake up at night and a sensation goes through your body. Shivers, you are cold.");
                while (!choice.equals("1")) {
                    System.out.println("You stand up and you don't know what to do next so you decide to:");
                    System.out.println("1. Try to find a way out of this desert");
                    choice = input.next();
                }
                System.out.println("You are surprised by how fast you find your way out of the desert but there is just one problem. Goblins. Before you can react in any way they spot you. There is no sense running you need to fight.");
                player.initiateFight(enemyPool, weaponPool, armorPool);
                player.pastIntro = true;
            }

            mainGameLoop:
            while (true) {
                System.out.println("What do you want to do now: ");
                System.out.println("1. Explore");
                System.out.println("2. Rest");
                System.out.println("3. Check inventory");
                System.out.println("4. See spells");
                System.out.println("5. See stats");
                System.out.println("6. Save");
                System.out.println("7. Exit");
                choice = input.next();
                switch (choice) {
                    case "1" -> player.explore(enemyPool, weaponPool, armorPool, itemPool);
                    case "2" -> player.rest();
                    case "3" -> player.inventoryFeature(weaponPool, armorPool);
                    case "4" -> player.seeSpells();
                    case "5" -> {
                        player.printAllInfo();
                        Functions.pressEnterToContinue();
                    }
                    case "6" -> player.saveCharacter();
                    case "7" -> {
                        break mainGameLoop;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }
}