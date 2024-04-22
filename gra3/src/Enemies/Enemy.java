package Enemies;

import Items.Weapon;
import Character.Character;
import Exceptions.zeroOrLessHpException;

public class Enemy {
    public String name;
    public int health;
    public int mana;
    public int armorRating;
    public Weapon equippedWeapon;
    public int expForKill;
    public int combatRating;

    public Enemy(String name, int minHealth, int maxHealth, int minMana, int maxMana, int minArmorRating, int maxArmorRating, Weapon equippedWeapon) {
        this.name = name;
        this.health = Functions.randomInt(minHealth, maxHealth);
        this.mana = Functions.randomInt(minMana, maxMana);
        this.armorRating = Functions.randomInt(minArmorRating, maxArmorRating);
        this.equippedWeapon = equippedWeapon;
        this.expForKill = (int)(50 + this.health * 0.5);
        this.combatRating = countCombatRating();
    }

    public void printAllInfo() {
        System.out.print("---" + this.name + "---");
        System.out.println();
        System.out.print("Health: " + this.health);
        System.out.print(" Mana: " + this.mana);
        System.out.println(" Armor Rating: " + this.armorRating);
        System.out.println();
    }
    public int countCombatRating() {
        return (health + mana + armorRating) * (equippedWeapon.minDamage + equippedWeapon.maxDamage) / 2;
    }
    public boolean attackPlayer(Character player) {
        try {
            int weaponDamage = Functions.randomInt(this.equippedWeapon.minDamage, this.equippedWeapon.maxDamage);
            int damage = (int) (weaponDamage - weaponDamage * ((double)player.armorRating / 100));
            System.out.println(this.name + " Hit you with his " + this.equippedWeapon.name + " for " + damage + " damage.");
            player.health -= damage;
            if (player.health <= 0)
                throw new zeroOrLessHpException();
        } catch (zeroOrLessHpException e) {
            System.out.println("During the battle you got knocked out. You lost half of your inventory and all experience");
            for (int i = 0; i < player.characterItems.size(); i++) {
                player.characterItems.remove(Functions.randomInt(0, player.characterItems.size() - 1));
            }
            player.experience = 0;
            player.health = player.level.health + (player.level.ordinal() + 1) * (player.constitution / 2);
            player.goldCoins /= 2;
            return true;
        }
        return false;
    }
}
