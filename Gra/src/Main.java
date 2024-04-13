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

        refreshEnemyPool();
    }
    static void refreshEnemyPool() {
        enemyPool.clear();
        enemyPool.put(Keys.GOBLIN,  new Enemy("Goblin", 13, 25, 5, 15, 8, 9, weaponPool.get(Keys.SWORD)));
        enemyPool.put(Keys.ORC,  new Enemy("Orc", 34, 62, 21, 29, 15, 17, weaponPool.get(Keys.GREATAXE)));
    }
    static boolean isNegative(int number) {
        return number < 0;
    }
    static Enemy pickRandomEnemy(HashMap<Keys, Enemy> hashMap) {
        int randomIndex = Functions.randomInt(0, hashMap.size() - 1);
        int currentIndex = 0;
        for (Enemy value : hashMap.values()) {
            if (currentIndex == randomIndex) {
                return value;
            }
            currentIndex++;
        }
        return null;
    }
    static void pressEnterToContinue() {
        System.out.println("Press Enter key to continue...");
        try {
            System.in.read();
        }
        catch(Exception ignored) {
        }
    }
    static void initiateFight(Character player) {
        player.combatRating = Functions.countCombatRating(player.health, player.mana, player.equippedWeapon.minDamage, player.equippedWeapon.maxDamage, player.armorRating);
        int sumOfCombatRatings = 0;
        ArrayList<Enemy> enemies = new ArrayList<>();
        Enemy randomEnemy;

        pressEnterToContinue();
        player.printAllInfo();
        System.out.println();
        while (player.combatRating > sumOfCombatRatings / 2) {
            randomEnemy = pickRandomEnemy(enemyPool);
            if (randomEnemy.combatRating + sumOfCombatRatings <= player.combatRating * 3) {
                enemies.add(randomEnemy);
                sumOfCombatRatings += randomEnemy.combatRating;
                randomEnemy.printAllInfo();
                refreshEnemyPool();
            }
        }

        fight(player, enemies);
    }
    static void fight(Character player, ArrayList<Enemy> enemies) {
        Scanner input = new Scanner(System.in);
        int action;
        int goldFromFight = 0;
        int killedEnemies = 0;
        int amountOfEnemies = enemies.size();
        boolean endYourTurn = false;
        boolean ranAway = false;
        boolean playerDead = false;

        for (Enemy enemy : enemies) {
            goldFromFight += enemy.health;
        }
        goldFromFight /= 2;

        fightLoop:
        while (!enemies.isEmpty()) {
            while (!endYourTurn) {
                System.out.println("It's your turn what do you wish to do: ");
                System.out.println("1. Attack | 2. Cast a Spell | 3. Run away | 4. Items");
                action = input.nextInt();
                switch (action) {
                    case 1:
                        attack(enemies, player);
                        endYourTurn = true;
                        break;
                    case 2:
                        if (!player.characterSpells.isEmpty()) {
                            while (true) {
                                for (int i = 0; i < player.characterSpells.size(); i++) {
                                    System.out.println((i + 1) + ". " + player.characterSpells.get(i).name);
                                }
                                System.out.println("0. Back");
                                action = input.nextInt() - 1;
                                if (action == -1)
                                    break;
                                spellPool.get(player.characterSpells.get(action).name).cast(enemies, player);
                                endYourTurn = true;
                                break;
                            }
                        } else {
                            System.out.println("You don't know any spells");
                        }
                        break;
                    case 3:
                        if (Functions.randomInt(1, 100) > enemies.size() * 20) {
                            ranAway = true;
                            break fightLoop;
                        } else {
                            System.out.println("You tried to ran but your enemies stopped you.");
                            endYourTurn = true;
                        }
                        break;
                    case 4:
                        inventoryFeature(player);
                        break;
                    default:
                        System.out.println("No such option. Try again.");
                }
            }
            for (int i = 0; i < enemies.size(); i++) {
                if (enemies.get(i).health <= 0) {
                    System.out.println(enemies.get(i).name + " dies.");
                    System.out.println("You gain " + enemies.get(i).expForKill + " experience.");
                    player.addExp(enemies.get(i).expForKill);
                    killedEnemies++;
                    enemies.remove(i);
                }
            }

            for (Enemy enemy : enemies) {
                playerDead = enemy.attackPlayer(player);
                if (playerDead)
                    break fightLoop;
            }
            if(enemies.isEmpty()) {
                break;
            }

            pressEnterToContinue();

            if (!enemies.isEmpty())
                player.printAllInfo();
            for (Enemy enemy : enemies) {
                enemy.printAllInfo();
            }

            endYourTurn = false;
        }
        if (killedEnemies == amountOfEnemies) {
            System.out.print("You won the fight.");
            System.out.println(" From the bodies you looted " + goldFromFight + " gold coins.");
            player.goldCoins += goldFromFight;
        } else if (ranAway) {
            System.out.println("You ran away.");
        }
        pressEnterToContinue();
    }
    static void attack(ArrayList<Enemy> enemies, Character player) {
        Scanner input = new Scanner(System.in);
        int action;

        System.out.println("Who do you wish to attack: ");
        for (int i = 0; i < enemies.size(); i++) {
            System.out.println((i + 1) + ". " + enemies.get(i).name);
        }
        while (true) {
            action = input.nextInt();
            try {
                player.equippedWeapon.attack(enemies.get(action-1), player);
                break;
            } catch (IndexOutOfBoundsException e) {
                System.out.println("No such option");
            }
        }
    }
    static void foundShop(Character player) {
        Scanner input = new Scanner(System.in);
        int amountOfItems = 5;
        System.out.println("While exploring you found a shop");
        int iterator;
        int choice;
        ArrayList<Item> availableItems = new ArrayList<>();
        for (int i = 0; i < amountOfItems; i++) {
            iterator = 0;
            choice = Functions.randomInt(0, itemPool.size() - 1);
            for (Item item : itemPool.values()) {
                if (iterator == choice && !availableItems.contains(item)) {
                    availableItems.add(item);
                } else if (iterator == choice && availableItems.contains(item)) {
                    i--;
                }
                iterator++;
            }
        }

        System.out.println("As you enter the shop from across the counter shopkeeper says: \"Welcome. Would you like to buy anything ?\".");

        while (true) {
            iterator = 1;
            System.out.println("You have " + player.goldCoins + " gold coins.");
            for (Item item : availableItems) {
                System.out.println(iterator + ". " + item.name + " for " + item.cost);
                iterator++;
            }
            System.out.println("0. Exit");
            choice = input.nextInt() - 1;
            if (choice == -1)
                break;

            if (player.goldCoins - availableItems.get(choice).cost >= 0) {
                player.characterItems.add(availableItems.get(choice));
                for (Item item : player.characterItems) {
                    System.out.println(item.name);
                }
                System.out.println("You bought a " + availableItems.get(choice).name + " for " + availableItems.get(choice).cost + " gold coins.");
                player.goldCoins -= availableItems.get(choice).cost;
                availableItems.remove(choice);
            } else {
                System.out.println("You don't have enough money.");
            }

        }
        System.out.println("You leave the shop.");
        pressEnterToContinue();
    }
    static void explore(Character player) {
        if (Functions.randomInt(1, 100) <= 75) {
            System.out.println("While exploring you stumbled upon enemies");
            initiateFight(player);
        } else {
            foundShop(player);
        }
    }
    static void rest(Character player) {
        player.health = player.level.health + (player.level.ordinal() + 1) * (player.constitution / 2);
        player.mana = player.level.mana + (player.level.ordinal() + 1) * (player.inteligence / 2);
        System.out.println("You rest regenerating your health and mana");
    }
    static void inventoryFeature(Character player) {
        Scanner input = new Scanner(System.in);
        int choice;
        int iterator;

        while (true) {
            iterator = 1;
            if (player.characterItems.get(0) == null) {
                System.out.println("You have no items in your inventory.");
                pressEnterToContinue();
                return;
            }
            System.out.println("Items in your inventory are: ");

            for (Item item : player.characterItems) {
                System.out.println(iterator + ". " + item.name);
                iterator++;
            }
            System.out.println("0. Exit Inventory");
            choice = input.nextInt() - 1;
            if (choice == -1)
                return;
            if (weaponPool.containsValue(player.characterItems.get(choice))) {
                player.characterItems.add(player.equippedWeapon);
                player.equippedWeapon = (Weapon) player.characterItems.get(choice);
                player.characterItems.remove(choice);
                System.out.println("You equiped a " + player.equippedWeapon.name);
            } else if (armorPool.containsValue(player.characterItems.get(choice))) {
                player.characterItems.add(player.wornArmor);
                player.wornArmor = (Armor) player.characterItems.get(choice);
                player.armorRating = player.wornArmor.armorRating;
                player.characterItems.remove(choice);
                System.out.println("You equiped a " + player.wornArmor.name);
            } else {
                System.out.println("You drank your " + player.characterItems.get(choice).name);
                ((Consumable)player.characterItems.get(choice)).consume(player, choice);
            }
        }
    }
    static void seeSpells(Character player) {
        Scanner input = new Scanner(System.in);
        int iterator = 1;
        int choice;
        while (true) {
        for (Spell spell : player.characterSpells) {
            System.out.println(iterator + ". " + spell.name);
        }
        System.out.println("0. Exit Spells");
            choice = input.nextInt() - 1;
            if (choice == -1)
                return;
            player.characterSpells.get(choice).printDescription();
            pressEnterToContinue();
        }
    }
    static Character createCharacter() {
        Character player = new Character();
        Scanner input = new Scanner(System.in);
        boolean chosenCorrectly = false;
        String choice;

        System.out.println("---Create your character---");
        System.out.println("Choose your class:");
        System.out.println("Fighter");
        System.out.println("Ranger");
        System.out.println("Wizard");

        while (!chosenCorrectly) {
            choice = input.next();
            switch (choice.toLowerCase()) {
                case "fighter" -> {
                    player.charactersClass = CharacterClass.FIGHTER;
                    player.equippedWeapon = weaponPool.get(Keys.SWORD);
                    chosenCorrectly = true;
                }
                case "ranger" -> {
                    player.charactersClass = CharacterClass.RANGER;
                    player.equippedWeapon = weaponPool.get(Keys.BOW);
                    chosenCorrectly = true;
                }
                case "wizard" -> {
                    player.charactersClass = CharacterClass.WIZARD;
                    player.equippedWeapon = weaponPool.get(Keys.STAFF);
                    chosenCorrectly = true;
                }
                default -> System.out.println("There is no such option. Choose again");
            }
        }

        int sum = 0;
        System.out.println("Choose your ability scores. The sum of them must add up to 62");
        while (true) {
            try {
                System.out.print("Strength: ");
                player.strength = input.nextShort();
                sum += player.strength;
                System.out.println(62 - sum + " Points left");

                System.out.print("Inteligence: ");
                player.inteligence = input.nextShort();
                sum += player.inteligence;
                System.out.println(62 - sum + " Points left");

                System.out.print("Constitution: ");
                player.constitution = input.nextShort();
                sum += player.constitution;
                System.out.println(62 - sum + " Points left");

                System.out.print("Charisma: ");
                player.charisma = input.nextShort();
                sum += player.charisma;
                System.out.println(62 - sum + " Points left");

                System.out.print("Dexterity: ");
                player.dexterity = input.nextShort();
                sum += player.dexterity;
            } catch (InputMismatchException e) {
                System.out.println("Wrong input. Try again");
                sum = 0;
                input.nextLine();
                continue;
            }

            if (player.strength + player.inteligence + player.constitution + player.charisma + player.dexterity != 62) {
                System.out.println("Ability scores don't add up to 62 points. Choose again");
                continue;
            }
            if (isNegative(player.strength) || isNegative(player.inteligence) || isNegative(player.constitution) || isNegative(player.charisma) || isNegative(player.dexterity)) {
                System.out.println("One or more ability scores were negative. Choose again");
                continue;
            }
            break;
        }

        player.health = player.level.health + (player.level.ordinal() + 1) * (player.constitution / 2);
        player.mana = player.level.mana + (player.level.ordinal() + 1) * (player.inteligence / 2);

        System.out.println("What is your name adventurer ?");
        input.nextLine();
        player.name = input.nextLine();
        //capitalize the first letter
        player.name = player.name.substring(0, 1).toUpperCase() + player.name.substring(1);
        return player;
    }
    static void loadCharacter(Character player, String[] characterArr, String[] spellsArr) {
        player.name = characterArr[0];
        player.experience = Integer.parseInt(characterArr[1]);
        switch (characterArr[2]) {
            case "FIGHTER" -> player.charactersClass = CharacterClass.FIGHTER;
            case "RANGER" -> player.charactersClass = CharacterClass.RANGER;
            case "WIZARD" -> player.charactersClass = CharacterClass.WIZARD;
        }
        player.health = Integer.parseInt(characterArr[3]);
        player.mana = Integer.parseInt(characterArr[4]);
        player.strength = Short.parseShort(characterArr[5]);
        player.inteligence = Short.parseShort(characterArr[6]);
        player.constitution = Short.parseShort(characterArr[7]);
        player.charisma = Short.parseShort(characterArr[8]);
        player.dexterity = Short.parseShort(characterArr[9]);
        player.goldCoins = Integer.parseInt(characterArr[10]);
        player.armorRating = Integer.parseInt(characterArr[11]);
        player.level = Levels.valueOf(characterArr[12]);
        switch (characterArr[13]) {
            case "true" -> player.pastIntro = true;
            case "false" -> player.pastIntro = false;
        }
        player.equippedWeapon = weaponPool.get(Keys.valueOf(stringToKey(characterArr[14])));

        if (!characterArr[15].equals("None")) {
            player.wornArmor = armorPool.get(Keys.valueOf(stringToKey(characterArr[15])));
        }
        for (int i = 16; i < characterArr.length; i++) {
            player.characterItems.add(itemPool.get(Keys.valueOf(stringToKey(characterArr[i]))));
        }
        for (String spell : spellsArr) {
            player.characterSpells.add(spellPool.get(Keys.valueOf(stringToKey(spell))));
        }
    }
    static void saveCharacter(Character player) {
        try {
            FileWriter fileWriter = new FileWriter("Character.txt");
            fileWriter.write(player.name + ";" + player.experience + ";" + player.charactersClass + ";" + player.health + ";" + player.mana + ";" + player.strength + ";" + player.inteligence + ";" + player.constitution + ";" + player.charisma + ";" + player.dexterity + ";" + player.goldCoins + ";" + player.armorRating + ";" + player.level + ";" + player.pastIntro + ";" + player.equippedWeapon.name + ";");
            if (player.wornArmor != null) {
                fileWriter.write(player.wornArmor.name);
            } else {
                fileWriter.write("None");
            }
            for (Item item : player.characterItems) {
                fileWriter.write(";" + item.name);
            }
            fileWriter.write("\n");
            for (Spell spell : player.characterSpells) {
                fileWriter.write(spell.name + ";");
            }
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Something went wrong");
        }
    }
    static String stringToKey (String string) {
        StringBuilder result = new StringBuilder();
        string = string.toUpperCase();
        for (int i = 0; i < string.length(); i++) {
            char letter = string.charAt(i);
            if (letter == ' ') {
                result.append('_');
            } else {
                result.append(letter);
            }
        }

        return  result.toString();
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
            if (choice.equals("0") || choice.equals(""))
                player = createCharacter();

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
                initiateFight(player);
                player.pastIntro = true;
            }

            mainGameLoop:
            while (true) {
                System.out.println("What do you want to do now: ");
                System.out.println("1. Explore");
                System.out.println("2. Rest");
                System.out.println("3. Check inventory");
                System.out.println("4. See spells");
                System.out.println("5. Save");
                System.out.println("6. Exit");
                choice = input.next();
                switch (choice) {
                    case "1" -> explore(player);
                    case "2" -> rest(player);
                    case "3" -> inventoryFeature(player);
                    case "4" -> seeSpells(player);
                    case "5" -> saveCharacter(player);
                    case "6" -> {
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