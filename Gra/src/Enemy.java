import java.util.ArrayList;

public class Enemy {
    String name;
    int health;
    int mana;
    int armorRating;
    Items equippedWeapon;
    int expForKill;

    public Enemy(String name) {
        this.name = name;
    }

    void printAllInfo() {
        System.out.print("---" + this.name + "---");
        System.out.println();
        System.out.print("Health: " + this.health);
        System.out.print(" Mana: " + this.mana);
        System.out.println(" Armor Rating: " + this.armorRating);
        System.out.println();
    }
}
