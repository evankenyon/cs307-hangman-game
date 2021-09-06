package secret_keepers;

import util.HangmanDictionary;

import java.util.ArrayList;
import java.util.List;

public class CleverSecretKeeper extends SecretKeeper {

    private List<String> possibleWords;

    public CleverSecretKeeper(HangmanDictionary dictionary, int wordLength) {
        super(dictionary, wordLength);
        possibleWords = new ArrayList<>(dictionary.getWords(wordLength));
    }

    @Override
    public String getSecretWord() {
        setSecretWord();
        return super.getSecretWord();
    }

    private void setSecretWord() {
//        correctlyGuessedLetters
    }

}
