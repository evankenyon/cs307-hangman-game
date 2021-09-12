package hangman;

import java.util.ArrayList;
import java.util.List;

import guessers.CleverGuesser;
import guessers.Guesser;
import guessers.InteractiveGuesser;
import guessers.SimpleGuesser;
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
import secret_keepers.CleverSecretKeeper;
import secret_keepers.SecretKeeper;
import util.DisplayWord;
import runner.Main;


/**
 * Purpose: This class represents a game of Hangman between a guesser who guesses a secret word
 * and a secret word keeper that chooses a secret word (sometimes multiple times if using a CleverSecretKeeper)
 * Assumptions: public static final ints are changed to allow for the hung person and the gallows to properly fit
 * the screen if Main.SIZE is changed significantly
 * Dependencies: JavaFX, Main, DisplayWord, SecretKeeper, Guesser, ArrayList, List
 * Example: Instantiate a HangmanGame object to play a game of hangman between a guesser and a secret keeper in a GUI,
 * with a hung person slowly developed as incorrect guesses from the guesser add up
 *
 * @author Evan Kenyon
 */
public class HangmanGame {
    // constant
    // make private if not used outside of class
    public static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    // Don't have to put the comments below
    // what is shown to the user
    private DisplayWord displayWord;
    // JFX variables
    private Scene scene;
    private Timeline animation;
    private List<Text> secretWordDisplay;
    private List<Text> lettersLeftToGuessDisplay;
    private List<String> correctLettersGuessedSkeleton;
    private List<String> incorrectLettersGuessed;
    private Guesser guesser;
    private SecretKeeper secretKeeper;
    private HungPersonDisplay hungPersonDisplay;
    private Group primaryRoot;


    /**
     * Purpose (comment partially borrowed from Prof. Duvall): Create Hangman game with the given guesser and secretKeeper, whose specific
     * types and attributes are determined by the call to this constructor in main class, and  with words of the given length
     * Assumptions: guesser and secretKeeper are properly constructed with same wordLength used if applicable
     * @param wordLength length of word to be guessed
     * @param guesser object that guesses the word
     * @param secretKeeper object that generates the word to be guessed
     */
    public HangmanGame(int wordLength, Guesser guesser, SecretKeeper secretKeeper) {
        this.guesser = guesser;
        this.secretKeeper = secretKeeper;
        displayWord = new DisplayWord(secretKeeper.getSecretWord());
        hungPersonDisplay = new HungPersonDisplay();
        correctLettersGuessedSkeleton = new ArrayList<>();
        incorrectLettersGuessed = new ArrayList<>();
        for(int x = 0; x < wordLength; x++) {
            correctLettersGuessedSkeleton.add("_");
        }
    }

    /**
     * Purpose (comment borrowed from Prof. Duvall): Start the game by animating the display of changes in the GUI every speed seconds.
     * Assumptions: speed is long enough so that computer can register distinct key inputs as single characters
     * @param speed second_delay for GUI
     */
    public void start (double speed) {
        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(new KeyFrame(Duration.seconds(speed), e -> playGuess()));
        animation.play();
    }

    /**
     * Purpose (comment borrowed from Prof. Duvall): Create the game's "scene": what shapes will be in the game at the beginning
     * and their starting properties.
     * Assumptions: background is not null
     * @param background color of game background
     * @return scene where hangman game takes place
     */
    public Scene setupDisplay (Paint background) {
        scene = new Scene(setupDisplays(Main.SIZE, Main.SIZE), Main.SIZE, Main.SIZE, background);
        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        return scene;
    }

    private List<Text> setupLetterDisplay(int xOffset, int yOffset, int fontSize, String letterCollection) {
        List<Text> letterDisplay = new ArrayList<>();
        for (int k = 0; k < letterCollection.length(); k += 1) {
            Text displayLetter = new Text(xOffset+20*k, yOffset, letterCollection.substring(k, k+1));
            // Should make font type a constant
            displayLetter.setFont(Font.font("Arial", FontWeight.BOLD, fontSize));
            letterDisplay.add(displayLetter);
        }
        return letterDisplay;
    }

    /**
     * Purpose (comment borrowed from Prof. Duvall): Play one round of the game.
     */
    public void playGuess () {
        setNewGuess();

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

        handleEndOfGame();
    }

    private void setNewGuess() {
        if(guesser.getType() == Guesser.SIMPLE) {
            ((SimpleGuesser) guesser).setCurrGuess();
        } else if(guesser.getType() == Guesser.CLEVER) {
            ((CleverGuesser) guesser).setCurrGuess(incorrectLettersGuessed, correctLettersGuessedSkeleton);
        }
    }

    private void handleRecordGuess(int index) {
        guesser.handleLetterAtIndexGuessed(index);
        checkGuessInSecretWord(index);
        updateDisplay(guesser.getMyLettersLeftToGuess().toString(), lettersLeftToGuessDisplay);
        updateDisplay(displayWord.toString(), secretWordDisplay);
    }

    private void updateHungPerson() {
        primaryRoot.getChildren().addAll(hungPersonDisplay.getBodyPart(guesser.getNumGuessesLeft()));
    }

    // Make strings below into constants
    private void handleEndOfGame() {
        if (guesser.getNumGuessesLeft() == 0) {
            handleEndOfGameCleanup("YOU ARE HUNG!");
        }
        else if (displayWord.equals(secretKeeper.getSecretWord())) {
            handleEndOfGameCleanup("YOU WIN!");
        }
    }

    private void handleEndOfGameCleanup(String endGameMessage) {
        System.out.println(endGameMessage);
        scene.setOnKeyPressed(null);
        animation.stop();
    }

    private void updateDisplay(String string, List<Text> stringDisplay) {
        for (int k = 0; k < string.length(); k += 1) {
            stringDisplay.get(k).setText(string.substring(k, k+1));
        }
    }

    private void checkGuessInSecretWord(int index) {
        if(secretKeeper.getType() == SecretKeeper.CLEVER) {
            ((CleverSecretKeeper) secretKeeper).setNewSecretWord(guesser.getCurrGuess(), incorrectLettersGuessed, correctLettersGuessedSkeleton);
        }

        if (! secretKeeper.getSecretWord().contains(guesser.getCurrGuess())) {
            handleIncorrectGuess(index);
            updateHungPerson();
        } else {
            handleCorrectGuess(index);
        }
    }

    private void handleIncorrectGuess(int index) {
        guesser.handleLetterAtIndexGuessed(index);
        guesser.decrementNumGuesses();
        incorrectLettersGuessed.add(guesser.getCurrGuess());
    }

    private void handleCorrectGuess(int index) {
        guesser.handleLetterAtIndexGuessed(index);
        displayWord.update(guesser.getCurrGuess().charAt(0), secretKeeper.getSecretWord());
        setCorrectLettersGuessedSkeleton();
    }

    private Group setupDisplays(int width, int height) {
        primaryRoot = new Group();
        lettersLeftToGuessDisplay = setupLetterDisplay(50, 50, 20, ALPHABET);
        primaryRoot.getChildren().addAll(lettersLeftToGuessDisplay);
        primaryRoot.getChildren().addAll(hungPersonDisplay.setupGallowsDisplay());
        secretWordDisplay = setupLetterDisplay(200, 500, 40, displayWord.toString());
        primaryRoot.getChildren().addAll(secretWordDisplay);
        return primaryRoot;
    }



    // Record user's input to be used in the game loop
    private void handleKeyInput (KeyCode code) {
        if(guesser.getType() == Guesser.INTERACTIVE) {
            ((InteractiveGuesser) guesser).setCurrGuess(code);
        }
    }

    private void setCorrectLettersGuessedSkeleton() {
        correctLettersGuessedSkeleton.clear();
        correctLettersGuessedSkeleton.addAll(List.of(displayWord.toString().split(" ")));
    }

}
