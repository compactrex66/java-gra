package Items.Consumables;

import Items.Consumable;
import Character.Character;

public class ManaPotion extends Consumable {
    public int manaRecoveryValue;

    public ManaPotion(String name, int cost, double weight, int manaRecoveryValue) {
        super(name, cost, weight);
        this.manaRecoveryValue = manaRecoveryValue;
    }

    public ManaPotion() {
        super("Mana Potion", 50, 0.2);
        this.manaRecoveryValue = 10;
    }
    public void consume(Character player) {
        player.health += this.manaRecoveryValue;
    }
}
