package Spells;

import Enemies.Enemy;
import Enemies.Functions;
import Character.Character;

import java.util.ArrayList;
import java.util.Scanner;

public class Icicle extends Spell{
    public int minDamage;
    public int maxDamage;

    public Icicle(String name, String description, int minDamage, int maxDamage, int manaCost, boolean usableOutsideCombat) {
        super(name, description, manaCost, usableOutsideCombat);
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
    }
    public void cast(ArrayList<Enemy> enemies, Character player) {
        Scanner input = new Scanner(System.in);
        int action;
        int damage;

        if (player.mana - this.manaCost >= 0) {
            System.out.println("Who do you wish to attack: ");
            for (int i = 0; i < enemies.size(); i++) {
                System.out.println((i + 1) + ". " + enemies.get(i).name);
            }
            while (true) {
                action = input.nextInt();
                try {
                    damage = Functions.randomInt(minDamage, maxDamage) + player.inteligence / 2 + player.equippedWeapon.bonusSpellDamage;
                    player.mana -= this.manaCost;
                    enemies.get(action - 1).health -= damage;
                    enemies.get(action - 1).armorRating -= 2;
                    System.out.println("You attacked " + enemies.get(action - 1).name + " for " + damage + " damage");
                    break;
                } catch (Exception e) {
                    System.out.println("No such option");
                }
            }
        } else {
            System.out.println("Not enough mana");
        }
    }
}
