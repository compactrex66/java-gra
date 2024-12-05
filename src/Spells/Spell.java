package Spells;

import Enemies.Enemy;
import Character.Character;
import java.util.ArrayList;

public class Spell {
    public String name;
    public String description;
    public int manaCost;
    public boolean usableOutsideCombat;

    public Spell(String name, String description, int manaCost, boolean usableOutsideCombat) {
        this.name = name;
        this.description = description;
        this.manaCost = manaCost;
        this.usableOutsideCombat = usableOutsideCombat;
        this.description += " - " + this.manaCost + " mana";
    }
    public void printDescription() {
        System.out.println(description);
    }
    public void cast(ArrayList<Enemy> enemy, Character player) {

    }
}
