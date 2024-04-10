public class Goblin extends Enemy{
    public Goblin(String name) {
        super(name);
        this.health = Functions.randomInt(12, 25);
        this.mana = Functions.randomInt(5, 15);
        this.armorRating = Functions.randomInt(8, 9);
        this.equippedWeapon = (Items.SWORD);
        this.expForKill = (int)(300 + this.health * 0.5);
    }
}
