package Items;
import Character.Character;

public class Consumable extends Item{

    public Consumable(String name, int cost, double weight) {
        super(name, cost, weight);
    }
    public void consume(Character player, int index) {
        player.characterItems.remove(index);
    }
}
