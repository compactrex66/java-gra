package Items;

public class Weapon extends Item{
    public int minDamage;
    public int maxDamage;

    public Weapon(String name, int cost, double weight, int minDamage, int maxDamage) {
        super(name, cost, weight);
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
    }
}
