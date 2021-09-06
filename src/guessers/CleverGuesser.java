package guessers;

import hangman.HangmanGame;
import util.HangmanDictionary;

import java.util.*;

public class CleverGuesser extends Guesser{
    private List<String> possibleWords;
    private Map<String, Integer> letterFrequencyInPossibleWords;

    public CleverGuesser(int numGuesses, int wordLength, HangmanDictionary dictionary) {
        super(numGuesses);
        possibleWords = new ArrayList<>(dictionary.getWords(wordLength));
        letterFrequencyInPossibleWords = new HashMap<>();
    }

    @Override
    public void setCurrGuess(String currGuess) {
        for(String letter: incorrectlyGuessedLetters) {
            possibleWords.removeIf(word -> word.contains(letter));
        }
        initializeLetterFrequencyInPossibleWords();
        recordLetterFrequencies();
        setCurrGuessToMaxFreqLetter();
        letterFrequencyInPossibleWords.clear();
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
        for(String key: letterFrequencyInPossibleWords.keySet()) {
            for(String word: possibleWords) {
                if(word.contains(key)) {
                    letterFrequencyInPossibleWords.put(key, letterFrequencyInPossibleWords.get(key) + 1);
                }
            }
        }
    }

    private void setCurrGuessToMaxFreqLetter() {
        int maxFrequency = 0;
        for(String key: letterFrequencyInPossibleWords.keySet()) {
            if(letterFrequencyInPossibleWords.get(key) > maxFrequency) {
                maxFrequency = letterFrequencyInPossibleWords.get(key);
                this.currGuess = key;
            }
        }
    }

    // For testing purposes, delete for final product
    private void printList(List<String> list) {
        for(String word: list) {
            System.out.print(word + " ");
        }
        System.out.println();
    }
}
