import Items.Weapon;
import Items.Weapons.Sword;

public class Goblin extends Enemy{
    public Goblin(String name) {
        super(name);
        this.health = Functions.randomInt(12, 25);
        this.mana = Functions.randomInt(5, 15);
        this.armorRating = Functions.randomInt(8, 9);
        this.equippedWeapon = (new Sword());
        this.expForKill = (int)(50 + this.health * 0.5);
    }
}
