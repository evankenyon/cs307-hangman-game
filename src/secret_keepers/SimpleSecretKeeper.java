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
     * Purpose: Construct a simple secret keeper that randomly chooses a word of wordLength
     * from dictionary
     * Assumptions: dictionary is not null, consistent with guesser class if applicable
     * @param dictionary possible secret words
     * @param wordLength required length of secret word
     */
    public SimpleSecretKeeper(HangmanDictionary dictionary, int wordLength) {
        secretWord = dictionary.getRandomWord(wordLength).toLowerCase();
    }

}
