public enum Items {
    SWORD(3, 8, 0, 75, 8),
    GREATAXE(6, 11, 0, 150, 16),
    DAGGER(2, 6, 0, 150, 16),
    BOW(5, 10, 0, 50, 4),
    STAFF(0, 10, 0, 125, 5),
    SHIELD(0, 0, 12, 80, 15),
    CHESTPLATE(0, 0, 20, 175, 21),
    LEGGINGS(0, 0, 18, 125, 17),
    HELMET(0, 0, 13, 100, 7),
    BOOTS(0, 0, 9, 70, 7);

    int maxDamage;
    int minDamage;
    int armorRating;
    int cost;
    double weight;

    Items(int minDamage, int maxDamage, int armorRating, int cost, double weight) {
        this.maxDamage = maxDamage;
        this.minDamage = minDamage;
        this.armorRating = armorRating;
        this.cost = cost;
        this.weight = weight;
    }
}
