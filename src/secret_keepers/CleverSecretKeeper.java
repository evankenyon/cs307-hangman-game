package secret_keepers;

import util.HangmanDictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
//        possibleWords.removeIf(word -> !word.contains(guesserGuess));
//        patternToWordsMap.
        initializePatternToWordsMap(guesserGuess);
        filterPossibleWords(cleanListToString(getMostWordsPattern()));
    }

    private void initializePatternToWordsMap(String guesserGuess) {
        patternToWordsMap.put(correctLettersGuessedSkeleton.toString(), new ArrayList<>());

        for (String word : possibleWords) {
            List<String> temp = new ArrayList<>(correctLettersGuessedSkeleton);
            if (!word.contains(guesserGuess)) {
                patternToWordsMap.get(correctLettersGuessedSkeleton.toString()).add(word);
            } else {
                for (int index = word.indexOf(guesserGuess); index < word.length(); index = word.indexOf(guesserGuess, index + 1)) {
                    temp.set(index, guesserGuess);
                }
                if (!patternToWordsMap.containsKey(temp.toString())) {
                    patternToWordsMap.put(temp.toString(), new ArrayList<>());
                }
                patternToWordsMap.get(temp.toString()).add(word);
            }
        }
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
        cleanListToString = listToString.replace("[", "");
        cleanListToString = cleanListToString.replace("]", "");
        cleanListToString = cleanListToString.replace(", ", "");
        return cleanListToString;
    }

    private void filterPossibleWords(String pattern) {
        possibleWords.removeIf(word -> {
            for(int x = 0; x < word.length(); x++) {
                if(!(pattern.charAt(x) == '_') && word.charAt(x) != pattern.charAt(x)) {
                    return true;
                }
            }
            return false;
        });
    }
}


