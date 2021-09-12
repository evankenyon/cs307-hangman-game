package secret_keepers;

import util.HangmanDictionary;

/**
 * Purpose: Represent a simple secret keeper that generates one secret word when its constructed
 * by randomly choosing one of wordLength from dictionary
 * Assumptions: dictionary has words of wordLength
 * Dependencies: HangmanDictionary
 * Example: Instantiate a SimpleSecretKeeper object in order to randomly choose a word for a
 * guesser to guess in a HangmanGame
 * Other details: Want all words to be lower case to keep upper case letters from becoming an
 * issue (i.e. from being counted separately when that's not the desired functionality)
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

    /**
     * Purpose: Get the type of this secret keeper
     * @return the type of this secret keeper (SIMPLE)
     */
    public int getType() {
        return SIMPLE;
    }

}
