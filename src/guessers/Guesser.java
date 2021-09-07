package guessers;

import hangman.HangmanGame;

import java.util.List;

/**
 * Purpose:
 * Assumptions:
 * Dependencies:
 * Example:
 * Other details:
 *
 * @Author Evan Kenyon
 */
public abstract class Guesser {
    private int numGuessesLeft;
    protected StringBuilder myLettersLeftToGuess;
    protected String currGuess;

    /**
     * Purpose:
     * Assumptions:
     * @param numGuesses
     */
    public Guesser(int numGuesses) {
        numGuessesLeft = numGuesses;
        myLettersLeftToGuess = new StringBuilder(HangmanGame.ALPHABET);
    }

    /**
     * Purpose:
     * Assumptions:
     * @return
     */
    public String getCurrGuess() {
        return currGuess;
    }

    /**
     * Purpose:
     * Assumptions:
     * @param currGuess
     * @param incorrectLettersGuessed
     * @param correctLettersGuessedSkeleton
     */
    public void setCurrGuess(String currGuess, List<String> incorrectLettersGuessed, List<String> correctLettersGuessedSkeleton) {
        this.currGuess = currGuess;
    }

    /**
     * Purpose:
     * Assumptions:
     * @return
     */
    public int getNumGuessesLeft() {
        return numGuessesLeft;
    }

    /**
     * Purpose:
     * Assumptions:
     * @param numGuessesLeft
     */
    public void setNumGuessesLeft(int numGuessesLeft) {
        this.numGuessesLeft = numGuessesLeft;
    }

    /**
     * Purpose:
     * Assumptions:
     * @return
     */
    public int getCurrGuessIndex() {
        return myLettersLeftToGuess.indexOf(currGuess);
    }

    /**
     * Purpose:
     * Assumptions:
     * @return
     */
    public StringBuilder getMyLettersLeftToGuess() {
        return myLettersLeftToGuess;
    }

    /**
     * Purpose:
     * Assumptions:
     * @param index
     */
    public void handleLetterAtIndexGuessed(int index) {
        myLettersLeftToGuess.setCharAt(index, ' ');
    }

    /**
     * Purpose:
     * Assumptions:
     * @return
     */
    public boolean isCurrGuessValid() {
        return currGuess.length() == 1 && HangmanGame.ALPHABET.contains(currGuess);
    }
}
