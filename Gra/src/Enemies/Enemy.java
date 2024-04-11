package Enemies;

import Items.Weapon;

import java.util.ArrayList;

public class Enemy {
    public String name;
    public int health;
    public int mana;
    public int armorRating;
    public Weapon equippedWeapon;
    public int expForKill;
    public int combatRating;

    public Enemy(String name) {
        this.name = name;
    }

    public void printAllInfo() {
        System.out.print("---" + this.name + "---");
        System.out.println();
        System.out.print("Health: " + this.health);
        System.out.print(" Mana: " + this.mana);
        System.out.println(" Armor Rating: " + this.armorRating);
        System.out.println();
    }
}
