package secret_keepers;

import util.DisplayWord;
import util.HangmanDictionary;

import java.util.ArrayList;
import java.util.List;

public abstract class SecretKeeper {
    private String secretWord;
    protected List<String> correctLettersGuessedSkeleton;

    public SecretKeeper(HangmanDictionary dictionary, int wordLength) {
        secretWord = dictionary.getRandomWord(wordLength).toLowerCase();
        correctLettersGuessedSkeleton = new ArrayList<>();
    }

    public String getSecretWord() {
        return secretWord;
    }

    public void setCorrectLettersGuessedSkeleton(DisplayWord displayWord) {
        correctLettersGuessedSkeleton.clear();
        correctLettersGuessedSkeleton.addAll(List.of(displayWord.toString().split(" ")));
    }

    public void setSecretWord(String guesserGuess) {

    }

}
