package Items.Consumables;

import Items.Consumable;

public class HealthPotion extends Consumable {
    int healthRecoveryValue;

    public HealthPotion() {
        super(name, cost, weight);
        this.healthRecoveryValue = healthRecoveryValue;
    }
}
