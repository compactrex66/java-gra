package Items.Weapons;

import Items.Weapon;

public class Staff extends Weapon {
    int bonusMagicDamage;

    public Staff() {
        super("Staff", 100, 4, 5, 8);
        this.bonusMagicDamage = 2;
    }
}
