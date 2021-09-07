package guessers;

import java.util.List;

/**
 * Purpose:
 * Assumptions:
 * Dependencies:
 * Example:
 * Other details:
 *
 * @Author Evan Kenyon
 */
public class SimpleGuesser extends Guesser {
    private static final String LETTERS_ORDERED_BY_FREQUENCY = "etaoinshrldcumfpgwybvkxjqz";
    private int currLetterIndex;

    /**
     * Purpose:
     * Assumptions:
     * @param numGuesses
     */
    public SimpleGuesser(int numGuesses) {
        super(numGuesses);
        currLetterIndex = 0;
    }

    /**
     * Purpose:
     * Assumptions:
     * @param currGuess
     * @param incorrectLettersGuessed
     * @param correctLettersGuessedSkeleton
     */
    @Override
    public void setCurrGuess(String currGuess, List<String> incorrectLettersGuessed, List<String> correctLettersGuessedSkeleton) {
        this.currGuess = "" + LETTERS_ORDERED_BY_FREQUENCY.charAt(currLetterIndex);
        currLetterIndex++;
    }
}
