package guessers;

import hangman.HangmanGame;
import util.HangmanDictionary;

import java.util.*;

public class CleverGuesser extends Guesser{
    private List<String> possibleWords;
    private Map<String, Integer> letterFrequencyInPossibleWords;

    public CleverGuesser(int numGuesses, int wordLength, HangmanDictionary dictionary) {
        super(numGuesses);
        for(int x = 0; x < wordLength; x++) {
            correctLettersGuessedSkeleton.add("_");
        }
        possibleWords = new ArrayList<>(dictionary.getWords(wordLength));
        letterFrequencyInPossibleWords = new HashMap<>();
    }

    @Override
    public void setCurrGuess(String currGuess) {
        possibleWords.removeIf(word -> {
            for(int x = 0; x < word.length(); x++) {
                if(!correctLettersGuessedSkeleton.get(x).equals("_") && !String.valueOf(word.charAt(x)).equals(correctLettersGuessedSkeleton.get(x))) {
                    return true;
                }
//                if(possibleWords.size() < 10) {
//                    System.out.print(word.charAt(x));
//                    System.out.println(correctLettersGuessedSkeleton.get(x));

//                }
            }
            return false;
        });
//        System.out.println("Overall skeleton" + correctLettersGuessedSkeleton);

        initializeLetterFrequencyInPossibleWords();
        recordLetterFrequencies();
//        for(String letter: letterFrequencyInPossibleWords.keySet()) {
//            System.out.print("Letter " + letter + ":");
//            printList(possibleWords, letter);
//        }
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
//        System.out.println("Guess: " + this.currGuess);
    }

    // For testing purposes, delete for final product
    private void printList(List<String> list, String letter) {
        for(String word: list) {
            if(word.contains(letter)) {
                System.out.print(word + " ");
            }
        }
        System.out.println();
        System.out.println();
    }
}
