package Items.Consumables;

import Items.Consumable;
import Character.Character;

public class ManaPotion extends Consumable {
    public int manaRecoveryValue;

    public ManaPotion() {
        super("Mana Potion", 50, 0.2);
        this.manaRecoveryValue = 10;
    }
    public void drinkManaPotion(Character player) {
        player.health += this.manaRecoveryValue;
    }
}
