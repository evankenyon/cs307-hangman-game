package secret_keepers;

import util.HangmanDictionary;

import java.util.ArrayList;
import java.util.List;

public abstract class SecretKeeper {
    private String secretWord;
    protected List<String> correctlyGuessedLetters;

    public SecretKeeper(HangmanDictionary dictionary, int wordLength) {
        secretWord = dictionary.getRandomWord(wordLength).toLowerCase();
        correctlyGuessedLetters = new ArrayList<>();
    }

    public String getSecretWord() {
        return secretWord;
    }

    public void addCorrectlyGuessedLetter(String letter) {
        correctlyGuessedLetters.add(letter);
    }
}
