package guessers;

import javafx.scene.input.KeyCode;

/**
 * Purpose: Represent an interactive guesser that allows a person to guess letters by inputting them
 * on the keyboard
 * Assumptions: Keyboard unavailable or not functioning
 * Example: instantiate an InteractiveGuesser object for use in a HangmanGame constructor, allowing a user
 * to play the game as a guesser as described in the purpose
 * Other details: No methods overridden and no other methods needed from Guesser parent class
 *
 * @Author Evan Kenyon
 */
public class InteractiveGuesser extends Guesser {

    /**
     * Purpose: Construct an interactive guesser that guesses letters by inputting them on the keyboard
     */
    public InteractiveGuesser() {
        super();
    }

    public String setNewGuess(KeyCode code) {
        currGuess = code.getChar().toLowerCase();
        return currGuess;
    }

    public int getType() {
        return INTERACTIVE;
    }

}
