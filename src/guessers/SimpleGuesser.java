package guessers;


/**
 * Purpose: Represent a guesser object that simply guesses letters in order of their frequency
 * in English words
 * Dependencies: List
 * Example: Instantiate a SimpleGuesser object to pass into a HangmanGame constructor in order to
 * guess the secret word's letters by guessing letters in order of their frequency in English words
 *
 * @Author Evan Kenyon
 */
public class SimpleGuesser extends Guesser {
    private static final String LETTERS_ORDERED_BY_FREQUENCY = "etaoinshrldcumfpgwybvkxjqz";
    private int currLetterIndex;

    /**
     * Purpose: Create a simple guesser that guesses letter by frequency in English words
     */
    public SimpleGuesser() {
        currLetterIndex = 0;
    }

    /**
     * Purpose: Set the current guess to the next most frequent letter
     * Assumptions: LETTERS_ORDERED_BY_FREQUENCY is correctly instantiated
     */
    public String setCurrGuess() {
        this.currGuess = "" + LETTERS_ORDERED_BY_FREQUENCY.charAt(currLetterIndex);
        currLetterIndex++;
        return currGuess;
    }

    /**
     * Purpose: Get the type of this guesser
     * @return the type of this guesser (SIMPLE)
     */
    public int getType() {
        return SIMPLE;
    }
}
