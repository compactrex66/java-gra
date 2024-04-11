package Enemies;

public enum Functions {;
    public static int randomInt(int min, int max) {
        return (int)Math.floor(Math.random() * (max - min + 1) + min);
    }
    public static void printSpaces(int amountOfSpaces) {
        for (int i = 0; i < amountOfSpaces; i++) {
            System.out.print(" ");
        }
    }
    public static int countCombatRating(int health, int mana, int minDamage, int maxDamage, int armorRating) {
        return (health + mana + armorRating) * ((minDamage + maxDamage) / 2);
    }
}
