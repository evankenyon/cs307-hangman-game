import guessers.CleverGuesser;
import guessers.Guesser;
import guessers.InteractiveGuesser;
import hangman.HangmanAutoGame;
import hangman.HangmanGame;
import guessers.SimpleGuesser;
import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import secret_keepers.CleverSecretKeeper;
import secret_keepers.SecretKeeper;
import secret_keepers.SimpleSecretKeeper;
import util.HangmanDictionary;


/**
 * This class launches the Hangman game and plays once.
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
     * Organize display of game in a scene and start the game.
     */
    @Override
    public void start (Stage stage) {
        HangmanDictionary gameDictionary =  new HangmanDictionary(DICTIONARY);
        SecretKeeper simpleSecretKeeper = new SimpleSecretKeeper(gameDictionary, NUM_LETTERS);
        SecretKeeper cleverSecretKeeper = new CleverSecretKeeper(gameDictionary, NUM_LETTERS);
        Guesser interactiveGuesser = new InteractiveGuesser(NUM_MISSES);
        Guesser cleverGuesser = new CleverGuesser(NUM_MISSES, NUM_LETTERS, gameDictionary);
        HangmanGame game = new HangmanGame(
//        HangmanAutoGame game = new HangmanAutoGame(
                gameDictionary, NUM_LETTERS, interactiveGuesser, cleverSecretKeeper);
        stage.setScene(game.setupDisplay(SIZE, SIZE, BACKGROUND));
        stage.setTitle(TITLE);
        stage.show();

        game.start(SECOND_DELAY);
    }
}
