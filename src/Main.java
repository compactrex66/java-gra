import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import Enemies.Functions;
import Character.Character;

import static Character.Pools.*;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        initiateAllPools();
        Character player = new Character();
        String[] characterArr;
        String choice = "";

        try {
            File file = new File("Character.txt");
            file.createNewFile();
            Scanner fileReader = new Scanner(file);
            if (file.length() != 0) {
                characterArr = fileReader.nextLine().split(";");

                System.out.println("Load your character: ");
                System.out.println("1. " + characterArr[0]);
                System.out.println("0. Create new character");
                choice = input.next();
                if (choice.equals("1"))
                    player.loadCharacter();
            }
            if (choice.equals("0") || choice.equals("")) {
                player.createCharacter();
                player.saveCharacter();
            }

            if (!player.pastIntro) {
                player.printAllInfo();
                System.out.println();
                System.out.println();
                System.out.println();

                System.out.println("You wake up on a battlefield among what seems like thousands of fallen soldiers. You don't remember much. Except for your name.");
                System.out.println("As you stand up you get a little light headed, looking around your surroundings you realise you are in the middle of a desert. On the horizon you spot an oasis.");
                System.out.println("Stumbling over corpses of fallen warriors you begin to walk in a direction of the oasis. While getting closer to your destination you think about what happened here, and why am i the only one that's survived and... .");
                System.out.println("Your thoughts are interrupted by a strange noise beneath your feet. You decide not to investigate and keep on walking very disturbed. It takes much of your remaining strength but you finally get there.");
                System.out.println("You kneel to drink the water from the oasis lake. You drink until your thirst is quenched and as you feel satisfied you pass out from the heat of the desert day. You wake up at night and a sensation goes through your body. Shivers, you are cold.");
                while (!choice.equals("1")) {
                    System.out.println("You stand up and you don't know what to do next so you decide to:");
                    System.out.println("1. Try to find a way out of this desert");
                    choice = input.next();
                }
                System.out.println("You are surprised by how fast you find your way out of the desert but there is just one problem. Goblins. Before you can react in any way they spot you. There is no sense running you need to fight.");
                player.initiateFight();
                player.pastIntro = true;
            }

            mainGameLoop:
            while (true) {
                System.out.println("What do you want to do now: ");
                System.out.println("1. Explore");
                System.out.println("2. Rest");
                System.out.println("3. Check inventory");
                System.out.println("4. See spells");
                System.out.println("5. See stats");
                System.out.println("6. Save");
                System.out.println("7. Exit");
                choice = input.next();
                switch (choice) {
                    case "1" -> player.explore();
                    case "2" -> player.rest();
                    case "3" -> player.inventoryFeature();
                    case "4" -> player.seeSpells();
                    case "5" -> {
                        player.printAllInfo();
                        Functions.pressEnterToContinue();
                    }
                    case "6" -> player.saveCharacter();
                    case "7" -> {
                        break mainGameLoop;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }
}