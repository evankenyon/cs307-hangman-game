package secret_keepers;

import util.HangmanDictionary;

public abstract class SecretKeeper {
    private String secretWord;

    public SecretKeeper(HangmanDictionary dictionary, int wordLength) {
        secretWord = dictionary.getRandomWord(wordLength).toLowerCase();
    }

    public String getSecretWord() {
        return secretWord;
    }
}
