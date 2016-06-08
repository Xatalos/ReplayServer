package javaapplication20;

/**
 *
 * @author Teemu
 */
public class Logic {

    int solution;
    private int guesses;
    
    public Logic(int solution) {
        this.solution = solution;
        this.guesses = 0;
    }

    public int getGuesses() {
        return this.guesses;
    }
    
    public int getSolution() {
        return this.solution;
    }

    public void addGuess() {
        this.guesses++;
    }
}
