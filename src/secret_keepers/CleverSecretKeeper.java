package secret_keepers;

import util.HangmanDictionary;

import java.util.*;

/**
 * Purpose: Represent a clever secret keeper in the game of hangman. Chooses a random secret word after
 * each of the guesser's guess based on which display word pattern has the most possible words (algorithm
 * described in more detail in the link found in the constructor's javadocs)
 * Assumptions: dictionary and wordLength are reasonable (i.e. dictionary has a plethora of words of
 * wordLength)
 * Dependencies: List, Map, HangmanDictionary
 * Example: Instantiate a clever secret keeper to use in a HangmanGame constructor in order to generate
 * secret words as described in the purpose
 *
 * @Author Evan Kenyon
 */
public class CleverSecretKeeper extends SecretKeeper {

    private List<String> possibleWords;
    private Map<String, List<String>> patternToWordsMap;

    /**
     * Purpose: Construct a clever secret keeper object that implements the following algorithm to generate a new
     * secret word after each of the guesser's guesses:
     * https://courses.cs.duke.edu/compsci101/spring14/assign/05_hangman/howto.php#Clever
     * Assumptions: dictionary is not null, same one as in InteractiveGuesser object
     * @param dictionary possible secret words to select from
     * @param wordLength required length of every secret word
     */
    public CleverSecretKeeper(HangmanDictionary dictionary, int wordLength) {
        secretWord = dictionary.getRandomWord(wordLength).toLowerCase();
        possibleWords = new ArrayList<>(dictionary.getWords(wordLength));
        patternToWordsMap = new HashMap<>();
    }


    /**
     * Purpose: Set a new secret word after each of the guesser's guess
     * Assumptions: guesserGuess is most recent guesser's guess,incorrectLettersGuessed is a list of incorrectly guessed letters
     * and correctLettersGuessedSkeleton is the toString of displayWord (from HangmanGame) in List form
     * (i.e. "___e__" would be ["_", "_", "_", "e", "_", "_"]
     * @param guesserGuess used for generation of potential patterns and words that fall into those patterns
     * @param incorrectLettersGuessed cannot have any secret words contain already incorrectly guessed letters
     * @param correctLettersGuessedSkeleton foundation for generating patterns (i.e. potential patterns can be this pattern
     *                                      or this pattern with the most recently guessed letter in one or more empty spots)
     */
    public void setNewSecretWord(String guesserGuess, List<String> incorrectLettersGuessed, List<String> correctLettersGuessedSkeleton) {
        for(String letter: incorrectLettersGuessed) {
            possibleWords.removeIf(word -> word.contains(letter));
        }
        initializePatternToWordsMap(guesserGuess, correctLettersGuessedSkeleton);
        filterPossibleWords(cleanListToString(getMostWordsPattern()), guesserGuess);
        // Borrowed code for selecting random word from a List from
        // https://www.baeldung.com/java-random-list-element
        Random rand = new Random();
        secretWord = possibleWords.get(rand.nextInt(possibleWords.size()));
    }

    /**
     * Purpose: Get the type of this secret keeper
     * @return the type of this secret keeper (CLEVER)
     */
    public int getType() {
        return CLEVER;
    }

    private void initializePatternToWordsMap(String guesserGuess, List<String> correctLettersGuessedSkeleton) {
        patternToWordsMap.clear();
        patternToWordsMap.put(correctLettersGuessedSkeleton.toString(), new ArrayList<>());

        for (String word : possibleWords) {
            if (!word.contains(guesserGuess)) {
                patternToWordsMap.get(correctLettersGuessedSkeleton.toString()).add(word);
            } else {
                addWordToPatternsMap(guesserGuess, word, correctLettersGuessedSkeleton);
            }
        }
    }

    private void addWordToPatternsMap(String guesserGuess, String word, List<String> correctLettersGuessedSkeleton) {
        // Borrowed code to get all indices of letter from
        // https://stackoverflow.com/questions/5034442/indexes-of-all-occurrences-of-character-in-a-string
        List<String> temp = new ArrayList<>(correctLettersGuessedSkeleton);
        for (int index = word.indexOf(guesserGuess); index != -1; index = word.indexOf(guesserGuess, index + 1)) {
            temp.set(index, guesserGuess);
        }
        if (!patternToWordsMap.containsKey(temp.toString())) {
            patternToWordsMap.put(temp.toString(), new ArrayList<>());
        }
        patternToWordsMap.get(temp.toString()).add(word);
    }

    private String getMostWordsPattern() {
        int numWords = 0;
        String mostWordsPattern = "";
        for(String pattern: patternToWordsMap.keySet()) {
            if(patternToWordsMap.get(pattern).size() > numWords) {
                numWords = patternToWordsMap.get(pattern).size();
                mostWordsPattern = pattern;
            }
        }
        return mostWordsPattern;
    }


    private String cleanListToString(String listToString) {
        String cleanListToString = "";
        // Borrowed code to replace [ and ], which gave idea to replace , from
        // https://stackoverflow.com/questions/32774059/output-arraylist-to-string-without-brackets-appearing/53165714
        cleanListToString = listToString.replace("[", "");
        cleanListToString = cleanListToString.replace("]", "");
        cleanListToString = cleanListToString.replace(", ", "");
        return cleanListToString;
    }

    private void filterPossibleWords(String pattern, String guesserGuess) {
        possibleWords.removeIf(word -> {
            for(int charPlace = 0; charPlace < word.length(); charPlace++) {
                if(shouldRemoveWord(pattern, word, guesserGuess, charPlace)) {
                    return true;
                }
            }
            return false;
        });
    }

    private boolean shouldRemoveWord(String pattern, String word, String guesserGuess, int charPlace) {
        return (!(pattern.charAt(charPlace) == '_') && word.charAt(charPlace) != pattern.charAt(charPlace)) || (pattern.charAt(charPlace) == '_' && word.charAt(charPlace) == guesserGuess.charAt(0));
    }
}


