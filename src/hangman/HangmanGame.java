package hangman;

import java.util.ArrayList;
import java.util.List;

import guessers.Guesser;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import secret_keepers.SecretKeeper;
import util.DisplayWord;
import runner.Main;


/**
 * Purpose (comment borrowed from Prof. Duvall): This class represents a game of Hangman between:
 *  - an interactive guesser
 *  - a secret word keeper that chooses a random secret word
 *
 * @author Robert C. Duvall
 */
public class HangmanGame {
    // constant
    public static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    public static final int BOTTOM_GALLOWS_START_X = Main.SIZE/2 - 200;
    public static final int BOTTOM_GALLOWS_END_X = Main.SIZE/2 - 100;
    public static final int BOTTOM_GALLOWS_Y = Main.SIZE/2 + 100;
    public static final int VERTICAL_GALLOWS_X = (BOTTOM_GALLOWS_START_X + BOTTOM_GALLOWS_END_X)/2;
    public static final int VERTICAL_GALLOWS_END_Y = BOTTOM_GALLOWS_Y - 250;
    public static final int TOP_GALLOWS_END_X = VERTICAL_GALLOWS_X + 100;
    public static final int ROPE_GALLOWS_END_Y = VERTICAL_GALLOWS_END_Y + 50;
    public static final int HEAD_OFFSET_Y = 25;
    public static final int BODY_TOP_OFFSET_Y = 50;
    public static final int ARM_OFFSET_Y = 65;
    public static final int LEG_OFFSET_Y = 125;
    public static final int LIMB_END_OFFSET = 25;
    public static final int LETTER_X_OFFSET_X_ONE = 10;
    public static final int LETTER_X_OFFSET_X_TWO = 5;
    public static final int LETTER_X_OFFSET_Y_ONE = 15;
    public static final int LETTER_X_OFFSET_Y_TWO = 25;

    // what is shown to the user
    private DisplayWord displayWord;
    // JFX variables
    private Scene scene;
    private Timeline animation;
    private Group gallowsDisplay;
    private List<Text> secretWordDisplay;
    private List<Text> lettersLeftToGuessDisplay;
    private List<String> correctLettersGuessedSkeleton;
    private List<String> incorrectLettersGuessed;
    private Guesser guesser;
    private SecretKeeper secretKeeper;
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
        correctLettersGuessedSkeleton = new ArrayList<>();
        incorrectLettersGuessed = new ArrayList<>();
        for(int x = 0; x < wordLength; x++) {
            correctLettersGuessedSkeleton.add("_");
        }
        // SHOULD NOT PUBLIC, but makes it easier to test
        System.out.println("*** " + secretKeeper.getSecretWord());
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
            displayLetter.setFont(Font.font("Arial", FontWeight.BOLD, fontSize));
            letterDisplay.add(displayLetter);
        }
        return letterDisplay;
    }

    /**
     * Purpose (comment borrowed from Prof. Duvall): Play one round of the game.
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
        guesser.setCurrGuess(null, incorrectLettersGuessed, correctLettersGuessedSkeleton);

        handleEndOfGame();
    }

    private void handleRecordGuess(int index) {
        guesser.handleLetterAtIndexGuessed(index);
        checkGuessInSecretWord(index);
        updateDisplay(guesser.getMyLettersLeftToGuess().toString(), lettersLeftToGuessDisplay);
        updateHungPerson();
        updateDisplay(displayWord.toString(), secretWordDisplay);
    }

    private void updateHungPerson() {
        if (guesser.getNumGuessesLeft() == 7) {
            primaryRoot.getChildren().add(new Circle(TOP_GALLOWS_END_X, ROPE_GALLOWS_END_Y + HEAD_OFFSET_Y, HEAD_OFFSET_Y));
        } else if (guesser.getNumGuessesLeft() == 6) {
            primaryRoot.getChildren().add(new Line(TOP_GALLOWS_END_X, ROPE_GALLOWS_END_Y + BODY_TOP_OFFSET_Y, TOP_GALLOWS_END_X, ROPE_GALLOWS_END_Y + BODY_TOP_OFFSET_Y + 75));
        } else if (guesser.getNumGuessesLeft() == 5) {
            primaryRoot.getChildren().add(new Line(TOP_GALLOWS_END_X, ROPE_GALLOWS_END_Y + ARM_OFFSET_Y, TOP_GALLOWS_END_X - LIMB_END_OFFSET, ROPE_GALLOWS_END_Y + ARM_OFFSET_Y + LIMB_END_OFFSET));
        } else if (guesser.getNumGuessesLeft() == 4) {
            primaryRoot.getChildren().add(new Line(TOP_GALLOWS_END_X, ROPE_GALLOWS_END_Y + ARM_OFFSET_Y, TOP_GALLOWS_END_X + LIMB_END_OFFSET, ROPE_GALLOWS_END_Y + ARM_OFFSET_Y + LIMB_END_OFFSET));
        } else if (guesser.getNumGuessesLeft() == 3) {
            primaryRoot.getChildren().add(new Line(TOP_GALLOWS_END_X, ROPE_GALLOWS_END_Y + LEG_OFFSET_Y, TOP_GALLOWS_END_X - LIMB_END_OFFSET, ROPE_GALLOWS_END_Y + LEG_OFFSET_Y + LIMB_END_OFFSET));
        } else if (guesser.getNumGuessesLeft() == 2) {
            primaryRoot.getChildren().add(new Line(TOP_GALLOWS_END_X, ROPE_GALLOWS_END_Y + LEG_OFFSET_Y, TOP_GALLOWS_END_X + LIMB_END_OFFSET, ROPE_GALLOWS_END_Y + LEG_OFFSET_Y + LIMB_END_OFFSET));
        } else if (guesser.getNumGuessesLeft() == 1) {
            produceLeftX();
        } else if (guesser.getNumGuessesLeft() == 0) {
            produceRightX();
        }
    }

    private void produceLeftX() {
        Line leftXOne = new Line(TOP_GALLOWS_END_X - LETTER_X_OFFSET_X_ONE, ROPE_GALLOWS_END_Y + LETTER_X_OFFSET_Y_ONE, TOP_GALLOWS_END_X - LETTER_X_OFFSET_X_TWO, ROPE_GALLOWS_END_Y + LETTER_X_OFFSET_Y_TWO);
        Line leftXTwo = new Line(TOP_GALLOWS_END_X - LETTER_X_OFFSET_X_ONE, ROPE_GALLOWS_END_Y + LETTER_X_OFFSET_Y_TWO, TOP_GALLOWS_END_X - LETTER_X_OFFSET_X_TWO, ROPE_GALLOWS_END_Y + LETTER_X_OFFSET_Y_ONE);
        leftXOne.setStroke(Color.RED);
        leftXTwo.setStroke(Color.RED);
        primaryRoot.getChildren().addAll(leftXOne, leftXTwo);
    }

    private void produceRightX() {
        Line rightXOne = new Line(TOP_GALLOWS_END_X + LETTER_X_OFFSET_X_TWO, ROPE_GALLOWS_END_Y + LETTER_X_OFFSET_Y_ONE, TOP_GALLOWS_END_X + LETTER_X_OFFSET_X_ONE, ROPE_GALLOWS_END_Y + LETTER_X_OFFSET_Y_TWO);
        Line rightXTwo = new Line(TOP_GALLOWS_END_X + LETTER_X_OFFSET_X_TWO, ROPE_GALLOWS_END_Y + LETTER_X_OFFSET_Y_TWO, TOP_GALLOWS_END_X + LETTER_X_OFFSET_X_ONE, ROPE_GALLOWS_END_Y + LETTER_X_OFFSET_Y_ONE);
        rightXOne.setStroke(Color.RED);
        rightXTwo.setStroke(Color.RED);
        primaryRoot.getChildren().addAll(rightXOne, rightXTwo);

    }

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
        secretKeeper.setSecretWord(guesser.getCurrGuess(), incorrectLettersGuessed, correctLettersGuessedSkeleton);
        if (! secretKeeper.getSecretWord().contains(guesser.getCurrGuess())) {
            handleIncorrectGuess(index);
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
        Group root = new Group();
        primaryRoot = root;
        lettersLeftToGuessDisplay = setupLetterDisplay(50, 50, 20, ALPHABET);
        root.getChildren().addAll(lettersLeftToGuessDisplay);
        setupGallowsDisplay(width, height);
        root.getChildren().add(gallowsDisplay);
        secretWordDisplay = setupLetterDisplay(200, 500, 40, displayWord.toString());
        root.getChildren().addAll(secretWordDisplay);
        return root;
    }

    private void setupGallowsDisplay(int width, int height) {
        Line bottomGallows = new Line(BOTTOM_GALLOWS_START_X, BOTTOM_GALLOWS_Y, BOTTOM_GALLOWS_END_X, BOTTOM_GALLOWS_Y);
        Line verticalGallows = new Line(VERTICAL_GALLOWS_X, BOTTOM_GALLOWS_Y, VERTICAL_GALLOWS_X, VERTICAL_GALLOWS_END_Y);
        Line topGallows = new Line(VERTICAL_GALLOWS_X, VERTICAL_GALLOWS_END_Y, TOP_GALLOWS_END_X, VERTICAL_GALLOWS_END_Y);
        Line ropeGallows = new Line(TOP_GALLOWS_END_X, VERTICAL_GALLOWS_END_Y, TOP_GALLOWS_END_X, ROPE_GALLOWS_END_Y);
        gallowsDisplay = new Group(bottomGallows, verticalGallows, topGallows, ropeGallows);
    }

    // Record user's input to be used in the game loop
    private void handleKeyInput (KeyCode code) {
        guesser.setCurrGuess(code.getChar().toLowerCase(), incorrectLettersGuessed, correctLettersGuessedSkeleton);
    }

    private void setCorrectLettersGuessedSkeleton() {
        correctLettersGuessedSkeleton.clear();
        correctLettersGuessedSkeleton.addAll(List.of(displayWord.toString().split(" ")));
    }

}
