package secret_keepers;

import util.HangmanDictionary;

import java.util.*;

public class CleverSecretKeeper extends SecretKeeper {

    private List<String> possibleWords;
    private Map<String, List<String>> patternToWordsMap;

    public CleverSecretKeeper(HangmanDictionary dictionary, int wordLength) {
        super(dictionary, wordLength);
        possibleWords = new ArrayList<>(dictionary.getWords(wordLength));
        patternToWordsMap = new HashMap<>();
        for (int x = 0; x < wordLength; x++) {
            correctLettersGuessedSkeleton.add("_");
        }
    }


    @Override
    public void setSecretWord(String guesserGuess) {
        initializePatternToWordsMap(guesserGuess);
        filterPossibleWords(cleanListToString(getMostWordsPattern()), guesserGuess);
        // Borrowed code for selecting random word from a List from
        // https://www.baeldung.com/java-random-list-element
        Random rand = new Random();
        secretWord = possibleWords.get(rand.nextInt(possibleWords.size()));
    }

    @Override
    public void addIncorrectLetterGuessed(String letter) {
        super.addIncorrectLetterGuessed(letter);
        possibleWords.removeIf(word -> word.contains(letter));
    }

    private void initializePatternToWordsMap(String guesserGuess) {
        patternToWordsMap.clear();
        patternToWordsMap.put(correctLettersGuessedSkeleton.toString(), new ArrayList<>());

        for (String word : possibleWords) {
            if (!word.contains(guesserGuess)) {
                patternToWordsMap.get(correctLettersGuessedSkeleton.toString()).add(word);
            } else {
                addWordToPatternsMap(guesserGuess, word);
            }
        }
    }

    private void addWordToPatternsMap(String guesserGuess, String word) {
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
        System.out.println(cleanListToString);
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


