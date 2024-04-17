package Items;

import Enemies.Enemy;
import Enemies.Functions;
import Character.Character;

public class Weapon extends Item{
    public int minDamage;
    public int maxDamage;
    public int bonusSpellDamage;

    public Weapon(String name, int cost, double weight, int minDamage, int maxDamage, int bonusSpellDamage) {
        super(name, cost, weight);
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
        this.bonusSpellDamage = bonusSpellDamage;
    }
    public void attack(Enemy enemy, Character player) {
        int baseDamage = Functions.randomInt(minDamage, maxDamage) + player.strength / 2;
        int damage = (int) (baseDamage - baseDamage  * ((double)enemy.armorRating / 100));
        enemy.health -= damage;
        System.out.println("You struck at your opponent dealing " + damage + " damage");
    }
}
