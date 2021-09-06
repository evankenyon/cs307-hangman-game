package guessers;

import hangman.HangmanGame;
import util.DisplayWord;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Guesser {
    private int numGuessesLeft;
    protected StringBuilder myLettersLeftToGuess;
    protected String currGuess;
    protected List<String> correctLettersGuessedSkeleton;
    protected List<String> incorrectLettersGuessed;

    public Guesser(int numGuesses) {
        numGuessesLeft = numGuesses;
        myLettersLeftToGuess = new StringBuilder(HangmanGame.ALPHABET);
        correctLettersGuessedSkeleton = new ArrayList<>();
        incorrectLettersGuessed = new ArrayList<>();
    }

    public String getCurrGuess() {
        return currGuess;
    }

    public void setCurrGuess(String currGuess) {
        this.currGuess = currGuess;
    }

    public int getNumGuessesLeft() {
        return numGuessesLeft;
    }

    public void setNumGuessesLeft(int numGuessesLeft) {
        this.numGuessesLeft = numGuessesLeft;
    }

    public int getCurrGuessIndex() {
        return myLettersLeftToGuess.indexOf(currGuess);
    }

    public StringBuilder getMyLettersLeftToGuess() {
        return myLettersLeftToGuess;
    }

    public void handleLetterAtIndexGuessed(int index) {
        myLettersLeftToGuess.setCharAt(index, ' ');
    }

    // rename
    public void setCorrectLettersGuessedSkeleton(DisplayWord displayWord) {
        correctLettersGuessedSkeleton.clear();
        correctLettersGuessedSkeleton.addAll(List.of(displayWord.toString().split(" ")));
//        for(int x = 0; x < displayWord.toString().split(" ").length; x++) {
//            System.out.print(displayWord.toString().split(" ")[x]);
//        }
//        System.out.println();
    }

    public void addIncorrectLetterGuessed(String letter) {
        incorrectLettersGuessed.add(letter);
    }

    public boolean isCurrGuessValid() {
        return currGuess.length() == 1 && HangmanGame.ALPHABET.contains(currGuess);
    }
}
