package secret_keepers;

import java.util.List;

/**
 * Purpose: Template for specific implementations of a secret keeper class, specifically for this project
 * an interactive secret keeper, a simple secret keeper, and clever secret keeper. Contains methods used by
 * all 3 in order to employ principles of inheritance.
 * Dependencies: List
 * Example: Instantiate a secret keeper object, specifically one of the above, for use in the HangmanGame constructor
 * in order to play the game with a secret keeper as intended
 * Other details: setSecretWords's is only used in CleverSecretKeeper, but kept in this class since calling it for
 * every SecretKeeper in HangmanGame was preferable to introducing extraneous booleans in HangmanGame's constructor.
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
     * Purpose: Only used in CleverSecretKeeper. Set a new secret word after each of the guesser's guess
     * Assumptions: guesserGuess is most recent guesser's guess,incorrectLettersGuessed is a list of incorrectly guessed letters
     * and correctLettersGuessedSkeleton is the toString of displayWord (from HangmanGame) in List form
     * (i.e. "___e__" would be ["_", "_", "_", "e", "_", "_"]
     * @param guesserGuess used for generation of potential patterns and words that fall into those patterns
     * @param incorrectLettersGuessed cannot have any secret words contain already incorrectly guessed letters
     * @param correctLettersGuessedSkeleton foundation for generating patterns (i.e. potential patterns can be this pattern
     *                                      or this pattern with the most recently guessed letter in one or more empty spots)
     */
    public void setSecretWord(String guesserGuess, List<String> incorrectLettersGuessed, List<String> correctLettersGuessedSkeleton) {
    }

}
