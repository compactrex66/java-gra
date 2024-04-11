package Items;

public class Armor extends Item{
    public int armorRating;

    public Armor(String name, int cost, double weight, int armorRating) {
        super(name, cost, weight);
        this.armorRating = armorRating;
    }
}
