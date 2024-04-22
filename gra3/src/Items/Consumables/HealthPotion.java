package Items.Consumables;

import Items.Consumable;
import Character.Character;

public class HealthPotion extends Consumable {
    public int healthRecoveryValue;

    public HealthPotion(String name, int cost, double weight, int healthRecoveryValue) {
        super(name, cost, weight);
        this.healthRecoveryValue = healthRecoveryValue;
    }

    public HealthPotion() {
        super("Health Potion", 50, 0.2);
        this.healthRecoveryValue = 10;
    }
    public void consume(Character player) { player.health += this.healthRecoveryValue; }
}
