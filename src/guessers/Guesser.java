package guessers;

import hangman.HangmanGame;

import java.util.ArrayList;
import java.util.List;

public abstract class Guesser {
    private int numGuessesLeft;
    protected StringBuilder myLettersLeftToGuess;
    protected String currGuess;
    protected List<String> incorrectlyGuessedLetters;

    public Guesser(int numGuesses) {
        numGuessesLeft = numGuesses;
        myLettersLeftToGuess = new StringBuilder(HangmanGame.ALPHABET);
        incorrectlyGuessedLetters = new ArrayList<>();
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

    public void addIncorrectlyGuessedLetter(String letter) {
        incorrectlyGuessedLetters.add(letter);
    }

    public boolean isCurrGuessValid() {
        return currGuess.length() == 1 && HangmanGame.ALPHABET.contains(currGuess);
    }
}
