import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import Items.*;
import Items.Armors.ChainmailArmor;
import Items.Armors.DragonScaleArmor;
import Items.Armors.IronPlateArmor;
import Items.Armors.LeatherArmor;
import Items.Consumables.HealthPotion;
import Items.Consumables.ManaPotion;
import Items.Weapons.Bow;
import Items.Weapons.Greataxe;
import Items.Weapons.Staff;
import Items.Weapons.Sword;

public class Main {
    private static List<Item> itemPool = new ArrayList<>();
    private static List<Weapon> weaponPool = new ArrayList<>();
    private static List<Armor> armorPool = new ArrayList<>();
    private static List<Consumable> consumablePool = new ArrayList<>();

    static void initiateItemPool() {
        weaponPool.add(new Sword());
        weaponPool.add(new Greataxe());
        weaponPool.add(new Bow());
        weaponPool.add(new Staff());

        armorPool.add(new LeatherArmor());
        armorPool.add(new ChainmailArmor());
        armorPool.add(new IronPlateArmor());
        armorPool.add(new DragonScaleArmor());

        consumablePool.add(new HealthPotion());
        consumablePool.add(new ManaPotion());

        itemPool.addAll(weaponPool);
    }
    static boolean isNegative(int number) {
        return number < 0;
    }
    static void pressEnterToContinue() {
        System.out.println("Press Enter key to continue...");
        try
        {
            System.in.read();
        }
        catch(Exception ignored)
        {}
    }
    static void initiateFight(Character player) {
        int numberOfEnemies = Functions.randomInt(1, 3);
        ArrayList<Enemy> enemies = new ArrayList<>();

        pressEnterToContinue();
        player.printAllInfo();
        System.out.println();
        for (int i = 0; i < numberOfEnemies; i++) {
            if (player.level.ordinal() + 1 <= 4) {
                enemies.add(new Goblin("goblin " + (i+1)));
            }
            enemies.get(i).printAllInfo();
        }
        fight(player, enemies);
    }
    static void fight(Character player, ArrayList<Enemy> enemies) {
        Scanner input = new Scanner(System.in);
        int action;
        int goldFromFight = 0;
        boolean endYourTurn = false;
        boolean ranAway = false;

        for (Enemy enemy : enemies) {
            goldFromFight += enemy.health;
        }
        goldFromFight /= 2;

        fightLoop:
        while (!enemies.isEmpty()) {
            System.out.println("It's your turn what do you wish to do: ");
            System.out.println("1. Attack | 2. Cast a Spell | 3. Run away | 4. Items");
            while (!endYourTurn) {
                action = input.nextInt();
                switch (action) {
                    case 1:
                        attack(enemies, (Functions.randomInt(player.equippedWeapon.minDamage, player.equippedWeapon.maxDamage) + ((player.strength - 10) / 2)));
                        endYourTurn = true;
                        break;
                    case 2:
                        System.out.println("You don't know any spells");
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
                        for (int i = 0; i < player.characterItems.size(); i++) {
                            System.out.println((i + 1) + ". " + player.characterItems.get(i));
                        }
                        break fightLoop;
                    default:
                        System.out.println("No such option. Try again.");
                }
            }
            for (int i = 0; i < enemies.size(); i++) {
                if (enemies.get(i).health <= 0) {
                    System.out.println(enemies.get(i).name + " dies.");
                    System.out.println("You gain " + enemies.get(i).expForKill + " experience.");
                    player.addExp(enemies.get(i).expForKill);
                    enemies.remove(i);
                }
            }

            for (Enemy enemy : enemies) {
                int damage = Functions.randomInt(enemy.equippedWeapon.minDamage, enemy.equippedWeapon.maxDamage);
                System.out.println(enemy.name + " Hit you with his " + enemy.equippedWeapon.name + " for " + damage + " damage.");
                player.health -= damage;
            }
            if(enemies.isEmpty()) {
                break fightLoop;
            }

            pressEnterToContinue();

            if (!enemies.isEmpty())
                player.printAllInfo();
            for (Enemy enemy : enemies) {
                enemy.printAllInfo();
            }

            endYourTurn = false;
        }
        if (!ranAway) {
            System.out.print("You won the fight.");
        } else {
            System.out.print("You ran away.");
        }
        System.out.println(" From the bodies you looted " + goldFromFight + " gold coins.");
        player.goldCoins += goldFromFight;
        pressEnterToContinue();
    }
    static void attack(ArrayList<Enemy> enemies, int damage) {
        Scanner input = new Scanner(System.in);
        int action;

        System.out.println("Who do you wish to attack: ");
        for (int i = 0; i < enemies.size(); i++) {
            System.out.println((i + 1) + ". " + enemies.get(i).name);
        }
        while (true) {
            action = input.nextInt();
            try {
                enemies.get(action - 1).health -= damage;
                break;
            } catch (IndexOutOfBoundsException e) {
                System.out.println("No such option");
            }
        }
        System.out.println("You struck at your opponent dealing " + damage + " damage");
    }
    static void foundShop(Character player) {
        Scanner input = new Scanner(System.in);
        int amountOfItems = 5;
        System.out.println("While exploring you found a shop");
        int iterator = 1;
        int choice = 10;
        ArrayList<Item> availableItems = new ArrayList<>();
        for (int i = 0; i < amountOfItems; i++) {
            availableItems.add(itemPool.get(Functions.randomInt(0, itemPool.size() - 1)));
        }

        System.out.println("As you enter the shop from across the counter shopkeeper says: \"Welcome. Would you like to buy anything ?\".");

        while (true) {
            iterator = 1;
            System.out.println("You have " + player.goldCoins + " gold coins.");
            for (Item item : availableItems) {
                System.out.println(iterator + ". " + item.name + " for " + item.cost);
                iterator++;
            }
            System.out.println(iterator + ". Exit");
            choice = input.nextInt();
            if (choice == iterator)
                break;
            choice--;

            if (player.goldCoins - availableItems.get(choice).cost >= 0) {
                player.characterItems.add(availableItems.get(choice));
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
        if (Functions.randomInt(1, 100) <= 50) {
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
    static void printInventory(Character player) {
        Scanner input = new Scanner(System.in);

        String choice = "";
        int iterator = 1;
        if (player.characterItems.isEmpty()) {
            System.out.println("You have no items in your inventory.");
            pressEnterToContinue();
        } else {
            System.out.println("Items in your inventory are: ");
        }
        for (Object item : player.characterItems) {
            System.out.println(iterator + ". " + item);
            iterator++;
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
                    player.equippedWeapon = new Sword();
                    chosenCorrectly = true;
                }
                case "ranger" -> {
                    player.charactersClass = CharacterClass.RANGER;
                    player.equippedWeapon = new Bow();
                    chosenCorrectly = true;
                }
                case "wizard" -> {
                    player.charactersClass = CharacterClass.WIZARD;
                    player.equippedWeapon = new Staff();
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

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        ArrayList<Object[]> characters = new ArrayList<>();
        initiateItemPool();
        Object[] characterArr;
        int iterator = 0;
        int loadedCharacter = 0;

        try {
            File file = new File("Character.txt");

            if (file.createNewFile()) {

            } else {
                Scanner fileReader = new Scanner(file);
                if (file.length() != 0) {
                    System.out.println("Load one of your characters: ");
                }
                while (fileReader.hasNextLine()) {
                    characterArr = fileReader.nextLine().split(";");
                    characters.add(characterArr);
                    System.out.println((iterator + 1) + ". " + characterArr[12]);
                    iterator++;
                }
                System.out.println(iterator + 1 + ". Create new character");
                loadedCharacter = input.nextInt();
            }
            Character player = new Character();
            String choice = "0";

            if (loadedCharacter == iterator + 1) {
               player = createCharacter();
            } else {
                characterArr = characters.get(loadedCharacter-1);
                player.level = Levels.valueOf(characterArr[0].toString());
                switch ((String) characterArr[2]) {
                    case "FIGHTER" -> player.charactersClass = CharacterClass.FIGHTER;
                    case "RANGER" -> player.charactersClass = CharacterClass.RANGER;
                    case "WIZARD" -> player.charactersClass = CharacterClass.WIZARD;
                }
                player.experience = Integer.parseInt(characterArr[1].toString());
                player.health = Integer.parseInt(characterArr[3].toString());
                player.mana = Integer.parseInt(characterArr[4].toString());
                player.strength = Short.parseShort(characterArr[5].toString());
                player.inteligence = Short.parseShort(characterArr[6].toString());
                player.constitution = Short.parseShort(characterArr[7].toString());
                player.charisma = Short.parseShort(characterArr[8].toString());
                player.dexterity = Short.parseShort(characterArr[9].toString());
                player.goldCoins = Integer.parseInt(characterArr[10].toString());
                player.armorRating = Integer.parseInt(characterArr[11].toString());
                player.name = characterArr[12].toString();
                switch (characterArr[13].toString()) {
                    case "true" -> player.pastIntro = true;
                    case "false" -> player.pastIntro = false;
                }
                for (Weapon weapon : weaponPool) {
                    if (weapon.name.equals(characterArr[14].toString())) {
                        player.equippedWeapon = weapon;
                        break;
                    }
                }
                for (int i = 15; i < characterArr.length; i++) {
                    for (Item item : itemPool) {
                        if (item.equals(characterArr[i])) {
                            player.characterItems.add(item);
                        }
                    }
                }
            }

            if (!player.pastIntro) {
                player.printAllInfo();
                System.out.println();
                System.out.println();
                System.out.println();

                System.out.println("You wake up on a battlefield among what seems like thousands of fallen soldiers. You don't remember much. Except for your name.");
                System.out.println("As you stand up you get a little light headed, looking around your surroundings you realise you are in the middle of a desert. On the horizon you spot an oasis.");
                System.out.println("Stumbling over corpses of warriors you begin to walk in a direction of the oasis. While getting closer to your destination you think about what happened here, and why am i the only one that's survived and... .");
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
                System.out.println("4. Save");
                System.out.println("5. Save and exit");
                choice = "";
                choice = input.next();
                switch (choice) {
                    case "1" -> explore(player);
                    case "2" -> rest(player);
                    case "3" -> printInventory(player);
                    case "4" -> {
                        FileWriter fileWriter = new FileWriter("Character.txt");
                        fileWriter.write(player.level + ";" + player.experience + ";" + player.charactersClass + ";" + player.health + ";" + player.mana + ";" + player.strength + ";" + player.inteligence + ";" + player.constitution + ";" + player.charisma + ";" + player.dexterity + ";" + player.goldCoins + ";" + player.armorRating + ";" + player.name + ";" + player.pastIntro + ";" + player.equippedWeapon.name);
                        for (Object item : player.characterItems) {
                            fileWriter.write(";" + item);
                        }
                        fileWriter.close();
                    }
                    case "5" -> {
                        FileWriter fileWriter = new FileWriter("Character.txt");
                        fileWriter.write(player.level + ";" + player.experience + ";" + player.charactersClass + ";" + player.health + ";" + player.mana + ";" + player.strength + ";" + player.inteligence + ";" + player.constitution + ";" + player.charisma + ";" + player.dexterity + ";" + player.goldCoins + ";" + player.armorRating + ";" + player.name + ";" + player.pastIntro + ";" + player.equippedWeapon.name);
                        for (Object item : player.characterItems) {
                            fileWriter.write(";" + item);
                        }
                        fileWriter.close();
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
