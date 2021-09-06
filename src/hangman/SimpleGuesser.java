package hangman;

public class SimpleGuesser extends Guesser{
    private static final String LETTERS_ORDERED_BY_FREQUENCY = "etaoinshrldcumfpgwybvkxjqz";
    private int currLetterIndex;

    public SimpleGuesser(int numGuesses) {
        super(numGuesses);
        currLetterIndex = 0;
    }

    @Override
    public void setCurrGuess(String currGuess) {
        this.currGuess = "" + LETTERS_ORDERED_BY_FREQUENCY.charAt(currLetterIndex);
        currLetterIndex++;
    }
}
