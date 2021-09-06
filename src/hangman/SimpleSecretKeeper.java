package hangman;

import util.HangmanDictionary;

public class SimpleSecretKeeper {
    private String secretWord;

    public SimpleSecretKeeper(HangmanDictionary dictionary, int wordLength) {
        secretWord = dictionary.getRandomWord(wordLength).toLowerCase();
    }

    public String getSecretWord() {
        return secretWord;
    }

}
