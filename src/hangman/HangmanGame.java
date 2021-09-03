package hangman;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import util.DisplayWord;
import util.HangmanDictionary;


/**
 * This class represents a game of Hangman between:
 *  - an interactive guesser
 *  - a secret word keeper that chooses a random secret word
 *
 * @author Robert C. Duvall
 */
public class HangmanGame {
    // constant
    public static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    // word that is being guessed
    private String mySecretWord;
    // what is shown to the user
    private DisplayWord myDisplayWord;
    // JFX variables
    private Scene myScene;
    private Timeline myAnimation;
    private Text myNumGuessesLeftDisplay;
    private List<Text> mySecretWordDisplay;
    private List<Text> myLettersLeftToGuessDisplay;
    private Guesser guesser;


    /**
     * Create Hangman game with the given dictionary of words to play a game with words
     * of the given length and giving the user the given number of chances.
     */
    public HangmanGame(HangmanDictionary dictionary, int wordLength, int numGuesses) {
        mySecretWord = dictionary.getRandomWord(wordLength).toLowerCase();
        myDisplayWord = new DisplayWord(mySecretWord);
        guesser = new Guesser(numGuesses);
        // SHOULD NOT PUBLIC, but makes it easier to test
        System.out.println("*** " + mySecretWord);
    }

    /**
     * Start the game by animating the display of changes in the GUI every speed seconds.
     */
    public void start (double speed) {
        myAnimation = new Timeline();
        myAnimation.setCycleCount(Timeline.INDEFINITE);
        myAnimation.getKeyFrames().add(new KeyFrame(Duration.seconds(speed), e -> playGuess()));
        myAnimation.play();
    }

    /**
     * Create the game's "scene": what shapes will be in the game and their starting properties.
     */
    public Scene setupDisplay (int width, int height, Paint background) {
        myScene = new Scene(setupDisplays(width, height), width, height, background);
        myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        return myScene;
    }

    private List<Text> setupLetterDisplay(int xOffset, int yOffset, int fontSize, String letterCollection) {
        List<Text> letterDisplay = new ArrayList<>();
        for (int k = 0; k < letterCollection.length(); k += 1) {
            Text displayLetter = new Text(xOffset+20*k, yOffset, letterCollection.substring(k, k+1));
            displayLetter.setFont(Font.font("Arial", FontWeight.BOLD, fontSize));
            letterDisplay.add(displayLetter);
        }
        return letterDisplay;
    }

    /**
     * Play one round of the game.
     */
    public void playGuess () {
        if (guesser.getCurrGuess() == null) {
            return;
        }
        if (guesser.isCurrGuessValid()) {
            int index = guesser.getCurrGuessIndex();
            if (index < 0) {
                return;
            }
            handleRecordGuess(index);
        }
        else {
            System.out.println("Please enter a single alphabetic letter ...");
        }
        guesser.setCurrGuess(null);

        handleEndOfGame();
    }

    private void handleRecordGuess(int index) {
        guesser.handleLetterAtIndexGuessed(index);
        checkGuessInSecretWord();
        updateDisplay(guesser.getMyLettersLeftToGuess().toString(), myLettersLeftToGuessDisplay);
        myNumGuessesLeftDisplay.setText(""+guesser.getNumGuessesLeft());
        updateDisplay(myDisplayWord.toString(), mySecretWordDisplay);
    }

    private void handleEndOfGame() {
        if (guesser.getNumGuessesLeft() == 0) {
            handleEndOfGameCleanup("YOU ARE HUNG!");
        }
        else if (myDisplayWord.equals(mySecretWord)) {
            handleEndOfGameCleanup("YOU WIN!");
        }
    }

    private void handleEndOfGameCleanup(String endGameMessage) {
        System.out.println(endGameMessage);
        myScene.setOnKeyPressed(null);
        myAnimation.stop();
    }

    private void updateDisplay(String string, List<Text> stringDisplay) {
        for (int k = 0; k < string.length(); k += 1) {
            stringDisplay.get(k).setText(string.substring(k, k+1));
        }
    }

    private void checkGuessInSecretWord() {
        if (! mySecretWord.contains(guesser.getCurrGuess())) {
            guesser.setNumGuessesLeft(guesser.getNumGuessesLeft() - 1);
        } else {
            myDisplayWord.update(guesser.getCurrGuess().charAt(0), mySecretWord);
        }
    }

    private Group setupDisplays(int width, int height) {
        Group root = new Group();
        myLettersLeftToGuessDisplay = setupLetterDisplay(50, 50, 20, ALPHABET);
        root.getChildren().addAll(myLettersLeftToGuessDisplay);
        myNumGuessesLeftDisplay = new Text(width/2 - 100, height/2 + 100, ""+guesser.getNumGuessesLeft());
        myNumGuessesLeftDisplay.setFont(Font.font("Verdana", FontWeight.BOLD, 300));
        root.getChildren().add(myNumGuessesLeftDisplay);
        mySecretWordDisplay = setupLetterDisplay(200, 500, 40, myDisplayWord.toString());
        root.getChildren().addAll(mySecretWordDisplay);
        return root;
    }

    // Record user's input to be used in the game loop
    private void handleKeyInput (KeyCode code) {
        guesser.setCurrGuess(code.getChar().toLowerCase());
    }
}
