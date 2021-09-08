package secret_keepers;

import javafx.scene.control.TextInputDialog;

import java.util.Optional;

/**
 * Purpose: Represent an interactive secret keeper that generates a secret word
 * for the user to guess based off of keyboard input
 * Assumptions: user running program has a functional keyboard
 * Dependencies: Optional, JavaFX
 * Example: Instantiate an InteractiveSecretKeeper object to use in a HangmanGame
 * constructor in order to generate the secret word to be guessed in the game
 *
 * @Author Evan Kenyon
 */
public class InteractiveSecretKeeper extends SecretKeeper{

    /**
     * Purpose: Construct an interactive secret keeper that generates a secret word from input from the keyboard
     * Assumptions: wordLength is consistent with wordLength argument for guesser class if applicable
     * @param wordLength length of secret word
     */
    public InteractiveSecretKeeper(int wordLength) {
        secretWord = getInput(String.format("Please enter a secret word %d letters long", wordLength), wordLength);
    }

    private String getInput (String prompt, int numCharacters) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setContentText(prompt);
        // DO NOT USE IN GENERAL - this is a TERRIBLE solution from UX design, and we will better ways later
        Optional<String> answer = dialog.showAndWait();
        while (answer.isEmpty() || answer.get().length() != numCharacters) {
            answer = dialog.showAndWait();
        }
        return answer.get();
    }
}
