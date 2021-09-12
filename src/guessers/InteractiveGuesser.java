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
    }

    /**
     * Purpose: set currGuess to letter input on keyboard
     * @param code key pressed
     */
    public void setCurrGuess(KeyCode code) {
        currGuess = code.getChar().toLowerCase();
    }

    /**
     * Purpose: Get the type of this guesser
     * @return the type of this guesser (INTERACTIVE)
     */
    public int getType() {
        return INTERACTIVE;
    }

}
