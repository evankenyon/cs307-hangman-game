package secret_keepers;

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
public abstract class SecretKeeper {
    protected String secretWord;

    /**
     * Purpose: Get the secret word to be guessed
     * @return the secret word to be guessed
     */
    public String getSecretWord() {
        return secretWord;
    }

    /**
     * Purpose: Used only in CleverGuesser. Described in detail there. Still needed to put in the parent class since called on
     * SecretKeeper object in HangmanGame, just does nothing unless CleverGuesser as designed.
     * Assumptions:
     * @param guesserGuess
     * @param incorrectLettersGuessed
     * @param correctLettersGuessedSkeleton
     */
    public void setSecretWord(String guesserGuess, List<String> incorrectLettersGuessed, List<String> correctLettersGuessedSkeleton) {
    }

}
