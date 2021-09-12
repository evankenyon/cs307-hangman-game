package guessers;

import hangman.HangmanGame;
import runner.Main;

import java.util.List;

// Should make getCurrGuess have all the logic for setting guess as well, don't want hangman class to call setCurrGuess
/**
 * Purpose: Template for specific implementations of a guesser class, specifically for this project
 * an interactive guesser, a simple guesser, and clever guesser. Contains methods used by all 3 in order
 * to employ principles of inheritance.
 * Dependencies: List, Main, HangmanGame (for ALPHABET)
 * Example: Instantiate a guesser object, specifically one of the above, for use in the HangmanGame constructor
 * in order to play the game with a guesser as intended
 * Other details: setCurrGuess's incorrectLettersGuessed and correctLettersGuessedSkeleton are only used in CleverGuesser,
 * but kept in this class since the setCurrGuess method is called on every guesser object and this was preferable to introducing
 * extraneous booleans in HangmanGame's constructor.
 *
 * @Author Evan Kenyon
 */
public abstract class Guesser {
    private int numGuessesLeft;
    // If already need getters and setters, can make private and use those methods
    protected StringBuilder myLettersLeftToGuess;
    protected String currGuess;

    /**
     * Purpose: Construct a guesser object that guesses letters in HangmanGame
     * Assumptions: Hangman.ALPHABET is the entire English alphabet in lowercase
     */
    public Guesser() {
        numGuessesLeft = Main.NUM_MISSES;
        myLettersLeftToGuess = new StringBuilder(HangmanGame.ALPHABET);
    }

    /**
     * Purpose: Get this guesser's current guess
     * @return this guesser's current guess
     */
    public String getCurrGuess() {
        return currGuess;
    }

    /**
     * Purpose: Sets this guesser's current guess to the currGuess argument
     * Assumptions: incorrectLettersGuessed is a list of incorrectly guessed letters and correctLettersGuessedSkeleton is
     * the toString of displayWord (from HangmanGame) in List form (i.e. "___e__" would be ["_", "_", "_", "e", "_", "_"]
     * @param currGuess the letter to set this guesser's current guess to
     * @param incorrectLettersGuessed only used in CleverGuesser, described in detail there
     * @param correctLettersGuessedSkeleton only used in CleverGuesser, described in detail there
     */
    public void setCurrGuess(String currGuess, List<String> incorrectLettersGuessed, List<String> correctLettersGuessedSkeleton) {
        this.currGuess = currGuess;
    }

    /**
     * Purpose: Get this guesser's number of guesses left before a game over
     * @return this guesser's number of guesses left before a game over
     */
    public int getNumGuessesLeft() {
        return numGuessesLeft;
    }

    /**
     * Purpose: Decrement this guesser's number of guesses left by 1
     */
    public void decrementNumGuesses() {
        this.numGuessesLeft -= 1;
    }

    /**
     * Purpose: Get the index of this guesser's current guess in the myLettersLeftToGuess member var
     * Assumptions: myLettersLeftToGuess is the English alphabet in order a-z
     * @return
     */
    public int getCurrGuessIndex() {
        return myLettersLeftToGuess.indexOf(currGuess);
    }

    /**
     * Purpose: Get this guesser's myLettersLeftToGuess member var
     * @return this guesser's myLettersLeftToGuess member var
     */
    public StringBuilder getMyLettersLeftToGuess() {
        return myLettersLeftToGuess;
    }

    /**
     * Purpose: Replace the letter at index with the space character to indicate the letter at the index was guessed
     * @param index the index in myLettersLeftToGuess to set to the space character
     */
    public void handleLetterAtIndexGuessed(int index) {
        myLettersLeftToGuess.setCharAt(index, ' ');
    }

    /**
     * Purpose: Check to see if the current guess is valid
     * Assumptions: HangmanGame.ALPHABET is the entire English alphabet
     * @return if the current guess is valid
     */
    public boolean isCurrGuessValid() {
        return currGuess.length() == 1 && HangmanGame.ALPHABET.contains(currGuess);
    }
}
