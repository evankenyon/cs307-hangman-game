package hangman;

public class Guesser {
    private int numGuessesLeft;
    private StringBuilder myLettersLeftToGuess;
    private String currGuess;

    public Guesser(int numGuesses) {
        numGuessesLeft = numGuesses;
        myLettersLeftToGuess = new StringBuilder(HangmanGame.ALPHABET);
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

    public boolean isCurrGuessValid() {
        return currGuess.length() == 1 && HangmanGame.ALPHABET.contains(currGuess);
    }

}
