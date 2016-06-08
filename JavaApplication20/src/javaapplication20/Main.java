package javaapplication20;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        Random random = new Random();
        int solution = random.nextInt(100) + 1;
        Logic logic = new Logic(solution);
        TextInterface textInterface = new TextInterface(logic);
        textInterface.runGame();
    }
}
