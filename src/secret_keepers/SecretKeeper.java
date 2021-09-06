package secret_keepers;

import util.DisplayWord;
import util.HangmanDictionary;

import java.util.ArrayList;
import java.util.List;

public abstract class SecretKeeper {
    protected String secretWord;
    protected List<String> correctLettersGuessedSkeleton;
    protected List<String> incorrectLettersGuessed;

    public SecretKeeper(HangmanDictionary dictionary, int wordLength) {
        secretWord = dictionary.getRandomWord(wordLength).toLowerCase();
        correctLettersGuessedSkeleton = new ArrayList<>();
        incorrectLettersGuessed = new ArrayList<>();
    }

    public String getSecretWord() {
        System.out.println("Secret word: " + secretWord);
        return secretWord;
    }

    public void setCorrectLettersGuessedSkeleton(DisplayWord displayWord) {
        correctLettersGuessedSkeleton.clear();
        correctLettersGuessedSkeleton.addAll(List.of(displayWord.toString().split(" ")));
    }

    public void setSecretWord(String guesserGuess) {

    }

    public void addIncorrectLetterGuessed(String letter) {
        incorrectLettersGuessed.add(letter);
    }

}
