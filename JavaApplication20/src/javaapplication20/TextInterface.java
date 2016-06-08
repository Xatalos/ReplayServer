package javaapplication20;

import java.util.Scanner;

public class TextInterface {

    private Logic logic;
    private Scanner scanner;

    public TextInterface(Logic logic) {
        this.logic = logic;
        this.scanner = new Scanner(System.in);
    }

    public void runGame() {
        while (true) {
            String answerString = getAnswerFromUser();
            int answer = convertAnswerToInteger(answerString);
            if (answer == -1) {
                continue;
            }
            logic.addGuess();
            boolean correctAnswer = evaluateAnswer(answer);
            if (correctAnswer) {
                break;
            }
        }
    }

    public String getAnswerFromUser() {
        System.out.println("Your guess for the randomed number (1-100):");
        return scanner.nextLine();
    }

    public int convertAnswerToInteger(String answerString) {
        try {
            return Integer.parseInt(answerString);
        } catch (NumberFormatException ex) {
            System.out.println("Not a valid answer!");
            return -1;
        }
    }

    public boolean evaluateAnswer(int answer) {
        if (answer == logic.getSolution()) {
            System.out.println("You got it right! It took " + logic.getGuesses() + " guesses!");
            return true;
        } else {
            if (logic.getSolution() > answer) {
                System.out.println("Your answer was too low!");
            } else {
                System.out.println("Your answer was too high!");
            }
            return false;
        }
    }
}