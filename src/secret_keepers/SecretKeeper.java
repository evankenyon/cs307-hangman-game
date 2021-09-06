package secret_keepers;

import util.DisplayWord;
import util.HangmanDictionary;

import java.util.ArrayList;
import java.util.List;

public abstract class SecretKeeper {
    protected String secretWord;

    public SecretKeeper(HangmanDictionary dictionary, int wordLength) {
        secretWord = dictionary.getRandomWord(wordLength).toLowerCase();
    }

    public String getSecretWord() {
//        System.out.println("Secret word: " + secretWord);
        return secretWord;
    }

    public void setSecretWord(String guesserGuess, List<String> incorrectLettersGuessed, List<String> correctLettersGuessedSkeleton) {

    }

}
