package guessers;

import util.HangmanDictionary;

import java.util.*;

/**
 * Purpose: Represent a guesser for a hangman game, specifically one that chooses a letter to guess based on the most possible words
 * (i.e. words that fit the currently displayed letters and do not contain incorrect letters) remaining containing that letter
 * Assumptions: JavaFX installed on device, wordLength and dictionary are both reasonable (i.e. wordLength should generally
 * be less than the number of guesses the guesser has and dictionary should be fairly large) and same as used in secret keeper
 * Dependencies: HangmanDictionary, List, Map
 * Example: Construct a clever guesser to use as an argument for the Hangman class to guess letters as described in the purpose
 *
 * @Author Evan Kenyon
 */
public class CleverGuesser extends Guesser{
    private List<String> possibleWords;
    private Map<String, Integer> letterFrequencyInPossibleWords;

    /**
     * Purpose: Construct a clever guesser object that guesses a letter based on the following algorithm:
     * https://courses.cs.duke.edu/compsci101/spring14/assign/05_hangman/howto.php#Clever
     * Assumptions: dictionary is not null, same one as used in secret keeper if applicable (i.e. if not interactive
     * secret keeper), wordLength is same one as used in secret keeper
     * @param wordLength length of word to be guessed
     * @param dictionary possible words to be guessed
     */
    public CleverGuesser(int wordLength, HangmanDictionary dictionary) {
        possibleWords = new ArrayList<>(dictionary.getWords(wordLength));
        letterFrequencyInPossibleWords = new HashMap<>();
    }

    /**
     * Purpose: Set the current guess for this guesser to guess in the hangman game based on the algorithm linked to above
     * Assumptions: incorrectLettersGuessed is a list of incorrectly guessed letters and correctLettersGuessedSkeleton is
     * the toString of displayWord (from HangmanGame) in List form (i.e. "___e__" would be ["_", "_", "_", "e", "_", "_"]
     * @param incorrectLettersGuessed incorrectly guessed letters thus far (i.e. none of the possible words to be guessed can have
     *                                these letters, so used to filter that list)
     * @param correctLettersGuessedSkeleton described in assumptions, used to filter possibleWords such that no possible words will
     *                                      have letters that are in the spot of a letter that's already correctly guessed in that spot
     */
    public void setCurrGuess(List<String> incorrectLettersGuessed, List<String> correctLettersGuessedSkeleton) {
        possibleWords.removeIf(word -> shouldRemoveWord(word, incorrectLettersGuessed, correctLettersGuessedSkeleton));
        initializeLetterFrequencyInPossibleWords();
        recordLetterFrequencies();
        setCurrGuessToMaxFreqLetter();
        letterFrequencyInPossibleWords.clear();
    }

    /**
     * Purpose: Get the type of this guesser
     * @return the type of this guesser (CLEVER)
     */
    public int getType() {
        return CLEVER;
    }

    private boolean shouldRemoveWord(String word, List<String> incorrectLettersGuessed, List<String> correctLettersGuessedSkeleton) {
        for(int x = 0; x < word.length(); x++) {
            if(!correctLettersGuessedSkeleton.get(x).equals("_") && !String.valueOf(word.charAt(x)).equals(correctLettersGuessedSkeleton.get(x))) {
                return true;
            }
            if(incorrectLettersGuessed.contains(String.valueOf(word.charAt(x)))){
                return true;
            }
        }
        return false;
    }

    private void initializeLetterFrequencyInPossibleWords() {
        for (String word: possibleWords) {
            // Borrowed code for splitting a String into individual chars from
            // https://stackoverflow.com/questions/1521921/splitting-words-into-letters-in-java
            for(String character: word.split("")) {
                // Borrowed code for checking if a StringBuilder contains a character from
                // https://stackoverflow.com/questions/3202861/java-how-to-check-stringbuilder-charcters-to-see-if-it-contains-same-characters/49486437
                if(myLettersLeftToGuess.indexOf(character) != -1) {
                    letterFrequencyInPossibleWords.put(character, 0);
                }
            }
        }
    }

    private void recordLetterFrequencies() {
        for(String letter: letterFrequencyInPossibleWords.keySet()) {
            for(String word: possibleWords) {
                if(word.contains(letter)) {
                    letterFrequencyInPossibleWords.put(letter, letterFrequencyInPossibleWords.get(letter) + 1);
                }
            }
        }
    }

    private void setCurrGuessToMaxFreqLetter() {
        int maxFrequency = 0;
        for(String letter: letterFrequencyInPossibleWords.keySet()) {
            if(letterFrequencyInPossibleWords.get(letter) > maxFrequency) {
                maxFrequency = letterFrequencyInPossibleWords.get(letter);
                this.currGuess = letter;
            }
        }
    }
}
