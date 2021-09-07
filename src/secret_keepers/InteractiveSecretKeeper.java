package secret_keepers;

import javafx.scene.control.TextInputDialog;

import java.util.Optional;

/**
 * Purpose:
 * Assumptions:
 * Dependencies:
 * Example:
 * Other details:
 *
 * @Author Evan Kenyon
 */
public class InteractiveSecretKeeper extends SecretKeeper{

    /**
     * Purpose:
     * Assumptions:
     * @param wordLength
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
