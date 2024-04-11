package Items.Consumables;

import Items.Consumable;
import Character.Character;

public class HealthPotion extends Consumable {
    public int healthRecoveryValue;

    public HealthPotion() {
        super("Health Potion", 50, 0.2);
        this.healthRecoveryValue = 10;
    }
    public void drinkHealthPotion(Character player) {
        player.health += this.healthRecoveryValue;
    }
}
