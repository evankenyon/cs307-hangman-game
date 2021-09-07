package guessers;

import java.util.List;

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
        super();
        currLetterIndex = 0;
    }

    /**
     * Purpose: Set the current guess to the next most frequent letter
     * Assumptions: incorrectLettersGuessed is a list of incorrectly guessed letters and correctLettersGuessedSkeleton is
     * the toString of displayWord (from HangmanGame) in List form (i.e. "___e__" would be ["_", "_", "_", "e", "_", "_"]
     * @param currGuess used for InteractiveGuesser's setCurrGuess function, but not relevant here
     * @param incorrectLettersGuessed only used in CleverGuesser, described in detail there
     * @param correctLettersGuessedSkeleton only used in CleverGuesser, described in detail there
     */
    @Override
    public void setCurrGuess(String currGuess, List<String> incorrectLettersGuessed, List<String> correctLettersGuessedSkeleton) {
        this.currGuess = "" + LETTERS_ORDERED_BY_FREQUENCY.charAt(currLetterIndex);
        currLetterIndex++;
    }
}
