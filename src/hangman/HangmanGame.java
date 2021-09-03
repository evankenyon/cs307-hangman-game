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
    // how many guesses are remaining
    private int myNumGuessesLeft;
    // what is shown to the user
    private DisplayWord myDisplayWord;
    // tracks letters guessed
    private StringBuilder myLettersLeftToGuess;
    // current player's guess
    private String myGuess;
    // JFX variables
    private Scene myScene;
    private Timeline myAnimation;
    private Text myNumGuessesLeftDisplay;
    private List<Text> mySecretWordDisplay;
    private List<Text> myLettersLeftToGuessDisplay;


    /**
     * Create Hangman game with the given dictionary of words to play a game with words
     * of the given length and giving the user the given number of chances.
     */
    public HangmanGame(HangmanDictionary dictionary, int wordLength, int numGuesses) {
        myNumGuessesLeft = numGuesses;
        myLettersLeftToGuess = new StringBuilder(ALPHABET);
        mySecretWord = dictionary.getRandomWord(wordLength).toLowerCase();
        myDisplayWord = new DisplayWord(mySecretWord);
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

        // create place to see and interact with the shapes
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
        // has user guessed?
        if (myGuess == null) {
            return;
        }
        // handle only valid guesses
        if (myGuess.length() == 1 && ALPHABET.contains(myGuess)) {
            int index = myLettersLeftToGuess.indexOf(myGuess);
            // do not count repeated guess as a miss
            if (myLettersLeftToGuess.indexOf(myGuess) < 0) {
                return;
            }
            handleRecordGuess(index);
        }
        else {
            System.out.println("Please enter a single alphabetic letter ...");
        }
        myGuess = null;

        handleEndOfGame();
    }

    private void handleRecordGuess(int index) {
        myLettersLeftToGuess.setCharAt(index, ' ');
        checkGuessInSecretWord();
        // update letters displayed to the user
        updateDisplay(myLettersLeftToGuess.toString(), myLettersLeftToGuessDisplay);
        myNumGuessesLeftDisplay.setText(""+myNumGuessesLeft);
        updateDisplay(myDisplayWord.toString(), mySecretWordDisplay);
    }

    private void handleEndOfGame() {
        if (myNumGuessesLeft == 0) {
            handleEndOfGameCleanup("YOU ARE HUNG!");
        }
        else if (myDisplayWord.equals(mySecretWord)) {
            handleEndOfGameCleanup("YOU WIN!");
        }
    }

    private void handleEndOfGameCleanup(String endGameMessage) {
        System.out.println(endGameMessage);
        // stop responding to key events when game is over
        myScene.setOnKeyPressed(null);
        // stop guessing when game is over
        myAnimation.stop();
    }

    private void updateDisplay(String string, List<Text> stringDisplay) {
        for (int k = 0; k < string.length(); k += 1) {
            stringDisplay.get(k).setText(string.substring(k, k+1));
        }
    }

    private void checkGuessInSecretWord() {
        if (! mySecretWord.contains(myGuess)) {
            myNumGuessesLeft -= 1;
        } else {
            myDisplayWord.update(myGuess.charAt(0), mySecretWord);
        }
    }

    private Group setupDisplays(int width, int height) {
        // show letters available for guessing
        Group root = new Group();
        myLettersLeftToGuessDisplay = setupLetterDisplay(50, 50, 20, ALPHABET);
        root.getChildren().addAll(myLettersLeftToGuessDisplay);
        // show "hanged man" simply as a number that counts down
        myNumGuessesLeftDisplay = new Text(width/2 - 100, height/2 + 100, ""+myNumGuessesLeft);
        myNumGuessesLeftDisplay.setFont(Font.font("Verdana", FontWeight.BOLD, 300));
        root.getChildren().add(myNumGuessesLeftDisplay);
        // show word being guessed, with letters hidden until they are guessed
        mySecretWordDisplay = setupLetterDisplay(200, 500, 40, myDisplayWord.toString());
        root.getChildren().addAll(mySecretWordDisplay);
        return root;
    }

    // Record user's input to be used in the game loop
    private void handleKeyInput (KeyCode code) {
        myGuess = code.getChar().toLowerCase();
    }
}
