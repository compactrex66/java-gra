import Items.Weapon;

import java.util.ArrayList;
import java.util.Scanner;

public class Character {
    Levels level = Levels.LEVEL_1;
    int experience = 0;
    CharacterClass charactersClass;
    int health = this.level.health;
    int mana = this.level.mana;
    short strength;
    short inteligence;
    short constitution;
    short charisma;
    short dexterity;
    int goldCoins = 0;
    int armorRating = 0;
    String name;
    boolean pastIntro = false;
    Weapon equippedWeapon;
    ArrayList<Object> characterItems = new ArrayList<>();

    public Character() {

    }

    public Character(Levels level, int experience, CharacterClass charactersClass, int health, int mana, short strength, short inteligence, short constitution, short charisma, short dexterity, int goldCoins, int armorRating, String name, Weapon equippedWeapon) {
        this.level = level;
        this.experience = experience;
        this.charactersClass = charactersClass;
        this.health = health;
        this.mana = mana;
        this.strength = strength;
        this.inteligence = inteligence;
        this.constitution = constitution;
        this.charisma = charisma;
        this.dexterity = dexterity;
        this.goldCoins = goldCoins;
        this.armorRating = armorRating;
        this.name = name;
        this.equippedWeapon = equippedWeapon;
        this.characterItems = characterItems;
    }

    void printAllInfo() {
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

    void addExp(int experience) {
        this.experience += experience;
        this.levelUp();
    }

    void levelUp() {
        if (experience >= (this.level.ordinal() + 1) * 500) {
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

            this.health = this.level.health + (this.level.ordinal() + 1) * (this.constitution / 2);
            this.mana = this.level.mana + (this.level.ordinal() + 1) * (this.inteligence / 2);
            this.experience = 0;
            this.printAllInfo();
        }
    }
}
