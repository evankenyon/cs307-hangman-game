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
    public static final int CLEVER = 1;
    public static final int INTERACTIVE = 2;
    public static final int SIMPLE = 3;

    protected String secretWord;

    /**
     * Purpose: Get the secret word to be guessed
     * @return the secret word to be guessed
     */
    public String getSecretWord() {
        return secretWord;
    }

    /**
     * Purpose: Get the type of this secret keeper
     * @return the type of this secret keeper
     */
    public abstract int getType();
}
