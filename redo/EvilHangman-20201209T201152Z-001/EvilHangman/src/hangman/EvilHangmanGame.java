package hangman;

import java.io.File;
/*import java.io.FileNotFoundException;*/
import java.io.IOException;
import java.util.*;

public class EvilHangmanGame implements IEvilHangmanGame {
    Set<String> setWords = new TreeSet<>();
    Set<String> usedWords = new TreeSet<>();
    Map<String, TreeSet<String>> mapWords = new TreeMap<>();
    String wordPattern = "";

    @Override
    public void startGame(File dictionary, int wordLength) throws IOException, EmptyDictionaryException/*, FileNotFoundException*/ {
        setWords.clear();
        usedWords.clear();
        mapWords.clear();   //Reset all of the lists before the game get started.

        if (wordLength < 2) {
            throw new EmptyDictionaryException();
        }
        if (dictionary.length() == 0) {
            throw new EmptyDictionaryException();
        }
        for (int i = 0; i < wordLength; i++) {            // Create "word pattern plate"
            wordPattern += "*";
        }
        Scanner scanner = new Scanner(dictionary);                // Read words in the dictionary
        while (scanner.hasNext()) {                       // Scanner has next value,
            String currentWord = scanner.next();          // current word is the next word in the dictionary
            if (wordLength == currentWord.length()) {     // if current word's length equals to wordLength,
                setWords.add(currentWord);                   // add current word into the TreeSet "words"
            }
        }
        if (setWords.isEmpty()) {
            throw new EmptyDictionaryException();
        }
        scanner.close();                                   //close the scanner
    }

    public String getWordPattern() {
        return wordPattern;
    }

    public Set<String> getSetWords() {
        return setWords;
    }

    public String getFinalWord() {
        for (String str : setWords) {
            return str;
        }
        /*return "";*/
        return null;
    }

    @Override
    public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {
        mapWords.clear();
        guess = Character.toLowerCase(guess);
        String strGuess = Character.toString(guess);          // Convert Char to String
        if (!(usedWords.contains(strGuess))) {                   // Check if the guessed word is already in the usedWords set.
            usedWords.add(strGuess);                              // Add guessed word into the usedWords set.
        }
        else {
            throw new GuessAlreadyMadeException();
        }

        //Build map and pattern starts//
        for (String tempWord : setWords) {                    //Create the pattern//
            StringBuilder pattern = new StringBuilder();
            for (int i = 0; i < tempWord.length(); i++) {
                if (tempWord.charAt(i) == guess) {            //If guess word and word.charAT(i) in the set is equal
                    pattern.append(guess);                         //Add guess word in that position
                }
                else if (wordPattern.charAt(i) != '*') {      //If some alphabet is already added
                    pattern.append(wordPattern.charAt(i));             //Add that alphabet in the pattern string
                }
                else {
                    pattern.append("*");                           //Else, add blank.
                }
            }
            if (mapWords.containsKey(pattern.toString())) {               // if map contains the pattern (Key)
                mapWords.get(pattern.toString()).add(tempWord);           // add the current word to that key.
            }
            else {
                TreeSet<String> currPattern = new TreeSet<>();   //If not, create new TreeSet
                currPattern.add(tempWord);                         // add the current word to new TreeSet
                mapWords.put(pattern.toString(), currPattern);                // And add the pattern (Key) and new TreeSet (value) into map
            }
        }
        //Build map and pattern ends//

        String keyOfMap = selectKey(guess);                   // Call function that helps find a specific key in the map
        setWords = mapWords.get(keyOfMap);                    // Update setWords
        return setWords;
    }

    private String selectKey(char guess) {
        String key = "";
        int maxSize = 0;

        //Remove all values except the largest set (value)//
        Set<String> removeWords = new TreeSet<>();                     // Create new TreeSet that contains words that have to be removed.
        for (Map.Entry<String, TreeSet<String>> entry : mapWords.entrySet()) {   // Get the largest size of Set(value) in Map   // 맵이더레이트이렇게함. entrySet써서
            if (maxSize < entry.getValue().size()) {                                                                        // entry is key
                maxSize = entry.getValue().size();
            }
        }
        for (Map.Entry<String, TreeSet<String>> entry : mapWords.entrySet()) {
            if (entry.getValue().size() != maxSize) {
                removeWords.add(entry.getKey());                             // Add keys into removedWords when its Set (value) size < maxSize.
            }
        }
        mapWords.keySet().removeAll(removeWords);
        //Now my map only has the key that has the largest Set (value)//  (it can be multiple keys)

        //Check Frequency of key and charAt(i)  [Filter part1]//
        if (mapWords.size() > 1) {
            int freqKey = 0;
            int freqChar = 0;
            int least = 100;
            for (Map.Entry<String, TreeSet<String>> entry : mapWords.entrySet()) {       // Check frequency of keys
                for (int i = 0; i < entry.getKey().length(); i++) {
                    if(entry.getKey().charAt(i) == guess) {
                        freqKey++;
                    }
                }
                if (least > freqKey) {
                    least = freqKey;
                }
            }
            Set<String> remove1 = new TreeSet<>();
            for (Map.Entry<String, TreeSet<String>> entry : mapWords.entrySet()) {
                for (int i = 0; i < entry.getKey().length(); i++) {
                    if (entry.getKey().charAt(i) == guess) {
                        freqChar++;
                    }
                }
                if (least != freqChar) {
                    remove1.add(entry.getKey());
                }
            }
            mapWords.keySet().removeAll(remove1);

            //  [Filter part2] //
            if (mapWords.size() > 1) {
                String comp = "*";
                for (Map.Entry<String, TreeSet<String>> entry : mapWords.entrySet()) {
                    String curr = entry.getKey();
                    if (curr.compareTo(comp) < 0) {
                        comp = curr;
                    }
                }
                Set<String> remove2 = new TreeSet<>();
                for (Map.Entry<String, TreeSet<String>> entry : mapWords.entrySet()) {
                    if (!entry.getKey().equals(comp)) {
                        remove2.add(entry.getKey());
                    }
                }
                mapWords.keySet().removeAll(remove2);
            }
            //  ends //
        }
        //Now I only have pattern (key) that matches the most //

        for (String keyStr : mapWords.keySet()) {
            key = keyStr;
        }

        wordPattern = key;
        return key;
    }

    @Override
    public SortedSet<Character> getGuessedLetters() {
        SortedSet<Character> chars = new TreeSet<>();
        for (int i = 0; i < usedWords.size(); i++) {
            chars.add(usedWords.toString().charAt(i));
        }
        return/* null;*/chars;
    }
}
