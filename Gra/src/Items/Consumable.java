package Items;

public class Consumable extends Item{
    public int healthRecoveryValue;
    public int manaRecoveryValue;

    public Consumable(String name, int cost, double weight, int healthRecoveryValue, int manaRecoveryValue) {
        super(name, cost, weight);
        this.healthRecoveryValue = healthRecoveryValue;
        this.manaRecoveryValue = manaRecoveryValue;
    }
}
