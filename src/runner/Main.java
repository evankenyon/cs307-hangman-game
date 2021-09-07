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
 *
 * @author Robert C. Duvall
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
     * Purpose (comment borrowed from Prof. Duvall): Organize display of game in a scene and start the game.
     * Assumptions:
     * @param stage
     */
    @Override
    public void start (Stage stage) {
        HangmanDictionary gameDictionary =  new HangmanDictionary(DICTIONARY);
        // Commented interactiveSecretKeeper out since constructing it prompts the user to input a secret word,
        // which isn't relevant for the other secret keepers
//        SecretKeeper interactiveSecretKeeper = new InteractiveSecretKeeper(NUM_LETTERS);
        SecretKeeper simpleSecretKeeper = new SimpleSecretKeeper(gameDictionary, NUM_LETTERS);
        SecretKeeper cleverSecretKeeper = new CleverSecretKeeper(gameDictionary, NUM_LETTERS);
        Guesser interactiveGuesser = new InteractiveGuesser(NUM_MISSES);
        Guesser simpleGuesser = new SimpleGuesser(NUM_MISSES);
        Guesser cleverGuesser = new CleverGuesser(NUM_MISSES, NUM_LETTERS, gameDictionary);

        HangmanGame game = new HangmanGame(NUM_LETTERS, cleverGuesser, cleverSecretKeeper);
        stage.setScene(game.setupDisplay(SIZE, SIZE, BACKGROUND));
        stage.setTitle(TITLE);
        stage.show();

        game.start(SECOND_DELAY);
    }
}
