package Character;

import Enemies.Enemy;
import Enemies.Functions;
import Items.Armor;
import Items.Consumable;
import Items.Item;
import Items.Weapon;
import Keys.Keys;
import Spells.Spell;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import static Character.Pools.*;

public class Character {
    public Levels level = Levels.LEVEL_1;
    public int experience = 0;
    public CharacterClass charactersClass;
    public int health = this.level.health;
    public int mana = this.level.mana;
    public int strength;
    public int inteligence;
    public int constitution;
    public int charisma;
    public int dexterity;
    public int goldCoins = 0;
    public int armorRating = 0;
    public String name;
    public boolean pastIntro = false;
    public Weapon equippedWeapon;
    public Armor wornArmor;
    public ArrayList<Item> characterItems = new ArrayList<>();
    public ArrayList<Spell> characterSpells = new ArrayList<>();
    public int combatRating;

    public Character() {

    }
    public void createCharacter() {
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
                    this.charactersClass = CharacterClass.FIGHTER;
                    this.equippedWeapon = weaponPool.get(Keys.SWORD);
                    chosenCorrectly = true;
                }
                case "ranger" -> {
                    this.charactersClass = CharacterClass.RANGER;
                    this.equippedWeapon = weaponPool.get("BOW");
                    chosenCorrectly = true;
                }
                case "wizard" -> {
                    this.charactersClass = CharacterClass.WIZARD;
                    this.equippedWeapon = weaponPool.get(Keys.STAFF);
                    this.characterSpells.add(Pools.spellPool.get(Keys.FIREBOLT));
                    this.characterSpells.add(Pools.spellPool.get(Keys.ICICLE));
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
                this.strength = input.nextShort();
                sum += this.strength;
                System.out.println(62 - sum + " Points left");

                System.out.print("Inteligence: ");
                this.inteligence = input.nextShort();
                sum += this.inteligence;
                System.out.println(62 - sum + " Points left");

                System.out.print("Constitution: ");
                this.constitution = input.nextShort();
                sum += this.constitution;
                System.out.println(62 - sum + " Points left");

                System.out.print("Charisma: ");
                this.charisma = input.nextShort();
                sum += this.charisma;
                System.out.println(62 - sum + " Points left");

                System.out.print("Dexterity: ");
                this.dexterity = input.nextShort();
                sum += this.dexterity;
            } catch (InputMismatchException e) {
                System.out.println("Wrong input. Try again");
                sum = 0;
                input.nextLine();
                continue;
            }

            if (this.strength + this.inteligence + this.constitution + this.charisma + this.dexterity != 62) {
                System.out.println("Ability scores don't add up to 62 points. Choose again");
                continue;
            }
            if (this.strength > 0 || this.inteligence > 0 || this.constitution > 0 || this.charisma > 0 || this.dexterity > 0) {
                System.out.println("One or more ability scores were negative. Choose again");
                continue;
            }
            break;
        }

        this.strength = this.strength + this.level.strength;
        this.inteligence = this.inteligence + this.level.inteligence;
        this.constitution = this.constitution + this.level.constitution;
        this.charisma = this.charisma + this.level.charisma;
        this.dexterity = this.dexterity + this.level.dexterity;

        this.health = this.level.health + (this.level.ordinal() + 1) * (this.constitution / 2);
        this.mana = this.level.mana + (this.level.ordinal() + 1) * (this.inteligence / 2);

        System.out.println("What is your name adventurer ?");
        input.nextLine();
        this.name = input.nextLine();
        //capitalize the first letter
        this.name = this.name.substring(0, 1).toUpperCase() + this.name.substring(1);
    }
    public void loadCharacter() {
        try {
            File file = new File("Character.txt");
            Scanner fileReader = new Scanner(file);
            String[] characterArr = fileReader.nextLine().split(";");
            String[] spellsArr = fileReader.nextLine().split(";");

            this.name = characterArr[0];
            this.experience = Integer.parseInt(characterArr[1]);
            switch (characterArr[2]) {
                case "FIGHTER" -> this.charactersClass = CharacterClass.FIGHTER;
                case "RANGER" -> this.charactersClass = CharacterClass.RANGER;
                case "WIZARD" -> this.charactersClass = CharacterClass.WIZARD;
            }
            this.health = Integer.parseInt(characterArr[3]);
            this.mana = Integer.parseInt(characterArr[4]);
            this.strength = Short.parseShort(characterArr[5]);
            this.inteligence = Short.parseShort(characterArr[6]);
            this.constitution = Short.parseShort(characterArr[7]);
            this.charisma = Short.parseShort(characterArr[8]);
            this.dexterity = Short.parseShort(characterArr[9]);
            this.goldCoins = Integer.parseInt(characterArr[10]);
            this.armorRating = Integer.parseInt(characterArr[11]);
            this.level = Levels.valueOf(characterArr[12]);
            switch (characterArr[13]) {
                case "true" -> this.pastIntro = true;
                case "false" -> this.pastIntro = false;
            }
            this.equippedWeapon = weaponPool.get(Keys.valueOf(Functions.stringToKey(characterArr[14])));

            if (!characterArr[15].equals("None")) {
                this.wornArmor = armorPool.get(Keys.valueOf(Functions.stringToKey(characterArr[15])));
            }
            for (int i = 16; i < characterArr.length; i++) {
                this.characterItems.add(itemPool.get(Keys.valueOf(Functions.stringToKey(characterArr[i]))));
            }
            for (String spell : spellsArr) {
                this.characterSpells.add(spellPool.get(Keys.valueOf(Functions.stringToKey(spell))));
            }
        } catch (Exception e) {
            System.out.println("blad");
        }
    }
    public void printAllInfo() {
        int amountOfSpaces = 0;
        System.out.println("---" + this.name + "---");
        amountOfSpaces = 20 - ("Your level: " + (this.level.ordinal() + 1)).length();
        System.out.print("Your level: " + (this.level.ordinal() + 1));
        Functions.printSpaces(amountOfSpaces);
        System.out.println("Experience: " + this.experience);

        amountOfSpaces = 20 - ("Health: " + this.health).length();
        System.out.print("Health: " + this.health);
        Functions.printSpaces(amountOfSpaces);
        System.out.println("Strength: " + this.strength);

        amountOfSpaces = 20 - ("Mana: " + this.mana).length();
        System.out.print("Mana: " + this.mana);
        Functions.printSpaces(amountOfSpaces);
        System.out.println("Inteligence: " + this.inteligence);

        amountOfSpaces = 20 - ("Armor Rating: " + this.armorRating).length();
        System.out.print("Armor Rating: " + this.armorRating);
        Functions.printSpaces(amountOfSpaces);
        System.out.println("Constitution: " + this.constitution);

        amountOfSpaces = 20 - ("Golden Coins: " + this.goldCoins).length();
        System.out.print("Golden Coins: " + this.goldCoins);
        Functions.printSpaces(amountOfSpaces);
        System.out.println("Charisma: " + this.charisma);

        amountOfSpaces = 20 - ("Weapon: " + this.equippedWeapon.name).length();
        System.out.print("Weapon: " + this.equippedWeapon.name);
        Functions.printSpaces(amountOfSpaces);
        System.out.println("Dexterity: " + this.dexterity);
    }
    public void inventoryFeature() {
        Scanner input = new Scanner(System.in);
        int choice;
        int iterator;

        while (true) {
            iterator = 1;
            if (this.characterItems.isEmpty()) {
                System.out.println("You have no items in your inventory.");
                Functions.pressEnterToContinue();
                return;
            }
            System.out.println("Items in your inventory are: ");

            if (this.characterItems.get(0) != null) {
                for (Item item : this.characterItems) {
                    System.out.println(iterator + ". " + item.name);
                    iterator++;
                }
            }
            System.out.println("0. Exit Inventory");
            choice = input.nextInt() - 1;
            if (choice == -1)
                return;
            if (weaponPool.containsValue(this.characterItems.get(choice))) {
                this.characterItems.add(this.equippedWeapon);
                this.equippedWeapon = (Weapon) this.characterItems.get(choice);
                this.characterItems.remove(choice);
                System.out.println("You equiped a " + this.equippedWeapon.name);
            } else if (armorPool.containsValue(this.characterItems.get(choice))) {
                this.characterItems.add(this.wornArmor);
                this.wornArmor = (Armor) this.characterItems.get(choice);
                this.armorRating = this.wornArmor.armorRating;
                this.characterItems.remove(choice);
                System.out.println("You equiped a " + this.wornArmor.name);
            } else {
                System.out.println("You drank your " + this.characterItems.get(choice).name);
                ((Consumable)this.characterItems.get(choice)).consume(this, choice);
            }
        }
    }
    public void seeSpells() {
        Scanner input = new Scanner(System.in);
        int iterator;
        int choice;
        while (true) {
            iterator = 1;
            for (Spell spell : this.characterSpells) {
                System.out.println(iterator + ". " + spell.name);
                iterator++;
            }
            System.out.println("0. Exit Spells");
            choice = input.nextInt() - 1;
            if (choice == -1)
                return;
            this.characterSpells.get(choice).printDescription();
            Functions.pressEnterToContinue();
        }
    }
    public void foundShop() {
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
            System.out.println("You have " + this.goldCoins + " gold coins.");
            for (Item item : availableItems) {
                System.out.println(iterator + ". " + item.name + " for " + item.cost);
                iterator++;
            }
            System.out.println("0. Exit");
            choice = input.nextInt() - 1;
            if (choice == -1)
                break;

            if (this.goldCoins - availableItems.get(choice).cost >= 0) {
                this.characterItems.add(availableItems.get(choice));
                for (Item item : this.characterItems) {
                    System.out.println(item.name);
                }
                System.out.println("You bought a " + availableItems.get(choice).name + " for " + availableItems.get(choice).cost + " gold coins.");
                this.goldCoins -= availableItems.get(choice).cost;
                availableItems.remove(choice);
            } else {
                System.out.println("You don't have enough money.");
            }

        }
        System.out.println("You leave the shop.");
        Functions.pressEnterToContinue();
    }
    public void rest() {
        this.health = this.level.health + (this.level.ordinal() + 1) * (this.constitution / 2);
        this.mana = this.level.mana + (this.level.ordinal() + 1) * (this.inteligence / 2);
        System.out.println("You rest regenerating your health and mana");
    }
    public void attackEnemy(ArrayList<Enemy> enemies) {
        Scanner input = new Scanner(System.in);
        int action;

        System.out.println("Who do you wish to attack: ");
        for (int i = 0; i < enemies.size(); i++) {
            System.out.println((i + 1) + ". " + enemies.get(i).name);
        }
        while (true) {
            action = input.nextInt();
            try {
                this.equippedWeapon.attack(enemies.get(action-1), this);
                break;
            } catch (IndexOutOfBoundsException e) {
                System.out.println("No such option");
            }
        }
    }
    public void fight(ArrayList<Enemy> enemies) {
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
                        attackEnemy(enemies);
                        endYourTurn = true;
                        break;
                    case 2:
                        if (!this.characterSpells.isEmpty()) {
                            while (true) {
                                for (int i = 0; i < this.characterSpells.size(); i++) {
                                    System.out.println((i + 1) + ". " + this.characterSpells.get(i).name);
                                }
                                System.out.println("0. Back");
                                action = input.nextInt() - 1;
                                if (action == -1)
                                    break;
                                this.characterSpells.get(action).cast(enemies, this);
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
                        this.inventoryFeature();
                        break;
                    default:
                        System.out.println("No such option. Try again.");
                }
            }
            for (int i = 0; i < enemies.size(); i++) {
                if (enemies.get(i).health <= 0) {
                    System.out.println(enemies.get(i).name + " dies.");
                    System.out.println("You gain " + enemies.get(i).expForKill + " experience.");
                    this.addExp(enemies.get(i).expForKill);
                    killedEnemies++;
                    enemies.remove(i);
                }
            }

            for (Enemy enemy : enemies) {
                playerDead = enemy.attackPlayer(this);
                if (playerDead)
                    break fightLoop;
            }
            if(enemies.isEmpty()) {
                break;
            }

            Functions.pressEnterToContinue();

            if (!enemies.isEmpty())
                this.printAllInfo();
            for (Enemy enemy : enemies) {
                enemy.printAllInfo();
            }

            endYourTurn = false;
        }
        if (killedEnemies == amountOfEnemies) {
            System.out.print("You won the fight.");
            System.out.println(" From the bodies you looted " + goldFromFight + " gold coins.");
            this.goldCoins += goldFromFight;
        } else if (ranAway) {
            System.out.println("You ran away.");
        }
        Functions.pressEnterToContinue();
    }
    public void initiateFight() {
        this.combatRating = Functions.countCombatRating(this.health, this.mana, this.equippedWeapon.minDamage, this.equippedWeapon.maxDamage, this.armorRating);
        int sumOfCombatRatings = 0;
        ArrayList<Enemy> enemies = new ArrayList<>();
        Enemy randomEnemy;

        Functions.pressEnterToContinue();
        this.printAllInfo();
        System.out.println();
        while (this.combatRating > sumOfCombatRatings / 2) {
            randomEnemy = Functions.pickRandomEnemy(enemyPool);
            if (randomEnemy.combatRating + sumOfCombatRatings <= this.combatRating * 3) {
                enemies.add(randomEnemy);
                sumOfCombatRatings += randomEnemy.combatRating;
                randomEnemy.printAllInfo();
                Functions.refreshEnemyPool();
            }
        }

        fight(enemies);
    }
    public void explore() {
        if (Functions.randomInt(1, 100) <= 75) {
            System.out.println("While exploring you stumbled upon enemies");
            initiateFight();
        } else {
            this.foundShop();
        }
    }
    void addExp(int experience) {
        this.experience += experience;
        this.levelUp();
    }
    void levelUp() {
        if (this.level.ordinal() + 1 < 1000) {
            if (experience >= (this.level.ordinal() + 1) * 500) {
                Levels previousLevel = this.level;
                this.level = Levels.values()[this.level.ordinal() + 1];

                Scanner input = new Scanner(System.in);
                System.out.println("You just leveled up. Choose ability score to increase: ");
                System.out.println("1. Strength");
                System.out.println("2. inteligence");
                System.out.println("3. constitution");
                System.out.println("4. charisma");
                System.out.println("5. dexterity");
                String choice = input.next();

                loop:
                while (true) {
                    switch (choice) {
                        case "1":
                            this.strength++;
                            break loop;
                        case "2":
                            this.inteligence++;
                            break loop;
                        case "3":
                            this.constitution++;
                            break loop;
                        case "4":
                            this.charisma++;
                            break loop;
                        case "5":
                            this.dexterity++;
                            break loop;
                        default:
                    }
                }

                this.strength = this.strength + (this.level.strength - previousLevel.strength);
                this.inteligence = this.inteligence + (this.level.inteligence - previousLevel.inteligence);
                this.constitution = this.constitution + (this.level.constitution - previousLevel.constitution);
                this.charisma = this.charisma + (this.level.charisma - previousLevel.charisma);
                this.dexterity = this.dexterity + (this.level.dexterity - previousLevel.dexterity);

                this.health = this.level.health + (this.level.ordinal() + 1) * (this.constitution / 2);
                this.mana = this.level.mana + (this.level.ordinal() + 1) * (this.inteligence / 2);
                this.experience = 0;
                this.printAllInfo();
            }
        }
    }
    public void saveCharacter() {
        try {
            FileWriter fileWriter = new FileWriter("Character.txt");
            fileWriter.write(this.name + ";" + this.experience + ";" + this.charactersClass + ";" + this.health + ";" + this.mana + ";" + this.strength + ";" + this.inteligence + ";" + this.constitution + ";" + this.charisma + ";" + this.dexterity + ";" + this.goldCoins + ";" + this.armorRating + ";" + this.level + ";" + this.pastIntro + ";" + this.equippedWeapon.name + ";");
            if (this.wornArmor != null) {
                fileWriter.write(this.wornArmor.name);
            } else {
                fileWriter.write("None");
            }
            for (Item item : this.characterItems) {
                if (item != null)
                    fileWriter.write(";" + item.name);
            }
            fileWriter.write("\n");
            for (Spell spell : this.characterSpells) {
                fileWriter.write(spell.name + ";");
            }
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Something went wrong");
        }
    }
}
