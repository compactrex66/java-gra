import Items.Weapon;
import Items.Weapons.Greataxe;

public class Orc extends Enemy{
    public Orc(String name) {
        super(name);
        this.health = Functions.randomInt(80, 100);
        this.mana = Functions.randomInt(25, 50);
        this.armorRating = Functions.randomInt(14, 15);
        this.equippedWeapon = (new Greataxe());
        this.expForKill = (int)(150 + this.health * 0.5);
    }
}