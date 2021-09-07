package guessers;

import hangman.HangmanGame;

import java.util.List;

public abstract class Guesser {
    private int numGuessesLeft;
    protected StringBuilder myLettersLeftToGuess;
    protected String currGuess;

    public Guesser(int numGuesses) {
        numGuessesLeft = numGuesses;
        myLettersLeftToGuess = new StringBuilder(HangmanGame.ALPHABET);
    }

    public String getCurrGuess() {
        return currGuess;
    }

    public void setCurrGuess(String currGuess, List<String> incorrectLettersGuessed, List<String> correctLettersGuessedSkeleton) {
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

    public boolean isCurrGuessValid() {
        return currGuess.length() == 1 && HangmanGame.ALPHABET.contains(currGuess);
    }
}
