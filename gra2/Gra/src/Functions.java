public enum Functions {;
    public static int randomInt(int min, int max) {
        return (int)Math.floor(Math.random() * (max - min + 1) + min);
    }
    public static void printSpaces(int amountOfSpaces) {
        for (int i = 0; i < amountOfSpaces; i++) {
            System.out.print(" ");
        }
    }
}
