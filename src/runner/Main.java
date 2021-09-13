package runner;

import guessers.CleverGuesser;
import guessers.Guesser;
import guessers.InteractiveGuesser;
import hangman.HangmanGame;
import guessers.SimpleGuesser;
import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import secret_keepers.CleverSecretKeeper;
import secret_keepers.InteractiveSecretKeeper;
import secret_keepers.SecretKeeper;
import secret_keepers.SimpleSecretKeeper;
import util.HangmanDictionary;


/**
 * Purpose (comment borrowed from Prof. Duvall): This class launches the Hangman game and plays once.
 * Assumptions: NUM_MISSES stays 8, HangmanGame class public static final ints are changed accordingly if
 * SIZE is changed
 * Dependencies: guessers package, secret_keepers package, HangmanDictionary, JavaFX, HangmanGame
 * Example: Put the desired types of guesser and secretkeeper in the HangmanGame constructor, then run in
 * order to launch the game with those players
 * Other details: Comment out interactiveSecretKeeper out when not in use since constructing it prompts
 * the user to input a secret word, which isn't relevant for the other secret keepers
 *
 * @author Evan Kenyon
 */
public class Main extends Application {
    // constants - JFX
    public static final String TITLE = "HangPerson";
    public static final int SIZE = 600;
    public static final Paint BACKGROUND = Color.THISTLE;
    public static final double SECOND_DELAY = 1;
    // constants - Game
    public static final String DICTIONARY = "lowerwords.txt";
    public static final int NUM_LETTERS = 6;
    public static final int NUM_MISSES = 8;

    /**
     * Purpose (first sentence borrowed from Prof. Duvall): Organize display of game in a scene and start the game.
     * Also, construct all different types of secret keepers and guessers to make it easy to swap them out of HangmanGame
     * constructor
     * @param stage Stage object to contain scene in which game will be played
     */
    @Override
    public void start (Stage stage) {
        HangmanDictionary gameDictionary =  new HangmanDictionary(DICTIONARY);
        SecretKeeper interactiveSecretKeeper = new InteractiveSecretKeeper(NUM_LETTERS);
        SecretKeeper simpleSecretKeeper = new SimpleSecretKeeper(gameDictionary, NUM_LETTERS);
        SecretKeeper cleverSecretKeeper = new CleverSecretKeeper(gameDictionary, NUM_LETTERS);
        Guesser interactiveGuesser = new InteractiveGuesser();
        Guesser simpleGuesser = new SimpleGuesser();
        Guesser cleverGuesser = new CleverGuesser(NUM_LETTERS, gameDictionary);

        HangmanGame game = new HangmanGame(NUM_LETTERS, simpleGuesser, interactiveSecretKeeper);
        stage.setScene(game.setupDisplay(BACKGROUND));
        stage.setTitle(TITLE);
        stage.show();

        game.start(SECOND_DELAY);
    }
}
