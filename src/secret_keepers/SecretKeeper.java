package secret_keepers;

import util.DisplayWord;
import util.HangmanDictionary;

import java.util.ArrayList;
import java.util.List;

public abstract class SecretKeeper {
    protected String secretWord;

    public String getSecretWord() {
        return secretWord;
    }

    public void setSecretWord(String guesserGuess, List<String> incorrectLettersGuessed, List<String> correctLettersGuessedSkeleton) {
        return;
    }

}
