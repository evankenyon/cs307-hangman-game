package hangman;

import util.HangmanDictionary;

public class SecretKeeper {
    private String secretWord;

    public SecretKeeper(HangmanDictionary dictionary, int wordLength) {
        secretWord = dictionary.getRandomWord(wordLength).toLowerCase();
    }

    public String getSecretWord() {
        return secretWord;
    }

}
