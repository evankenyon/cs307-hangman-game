package secret_keepers;

import util.HangmanDictionary;

public class SimpleSecretKeeper extends SecretKeeper{

    public SimpleSecretKeeper(HangmanDictionary dictionary, int wordLength) {
        secretWord = dictionary.getRandomWord(wordLength).toLowerCase();
    }

}
