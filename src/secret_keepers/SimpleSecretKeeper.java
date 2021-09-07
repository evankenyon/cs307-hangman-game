package secret_keepers;

import util.HangmanDictionary;

/**
 * Purpose:
 * Assumptions:
 * Dependencies:
 * Example:
 * Other details:
 *
 * @Author Evan Kenyon
 */
public class SimpleSecretKeeper extends SecretKeeper{

    /**
     * Purpose:
     * Assumptions:
     * @param dictionary
     * @param wordLength
     */
    public SimpleSecretKeeper(HangmanDictionary dictionary, int wordLength) {
        secretWord = dictionary.getRandomWord(wordLength).toLowerCase();
    }

}
