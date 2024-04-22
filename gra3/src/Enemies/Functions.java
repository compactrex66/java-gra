package Enemies;

import Keys.Keys;

import java.util.HashMap;

import static Character.Pools.*;

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
    public static void pressEnterToContinue() {
        System.out.println("Press Enter key to continue...");
        try {
            System.in.read();
        }
        catch(Exception ignored) {
        }
    }
    public static String stringToKey (String string) {
        StringBuilder result = new StringBuilder();
        string = string.toUpperCase();
        for (int i = 0; i < string.length(); i++) {
            char letter = string.charAt(i);
            if (letter == ' ') {
                result.append('_');
            } else {
                result.append(letter);
            }
        }

        return  result.toString();
    }
    static public void refreshEnemyPool() {
        enemyPool.clear();
        enemyPool.put(Keys.GOBLIN,  new Enemy("Goblin", 13, 25, 5, 15, 8, 9, weaponPool.get(Keys.SWORD)));
        enemyPool.put(Keys.ORC,  new Enemy("Orc", 34, 62, 21, 29, 15, 17, weaponPool.get(Keys.GREATAXE)));
    }
    static public Enemy pickRandomEnemy(HashMap<Keys, Enemy> enemyPool) {
        int randomIndex = Functions.randomInt(0, enemyPool.size() - 1);
        int currentIndex = 0;
        for (Enemy value : enemyPool.values()) {
            if (currentIndex == randomIndex) {
                return value;
            }
            currentIndex++;
        }
        return null;
    }
}
