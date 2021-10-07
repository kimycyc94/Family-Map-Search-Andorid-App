package spell;

import java.io.IOException;
import java.io.File;         // Note: File call class
import java.util.*;          // Note: Call all the classes

public class SpellCorrector implements ISpellCorrector {
    private Trie myTrie;
    public SpellCorrector() { myTrie = new Trie(); }      // SpellCorrector constructor

    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        File fileName = new File(dictionaryFileName);
        Scanner scanner = new Scanner(fileName);
        String word;
        while(scanner.hasNext()) {                                    // Note: hasNext() 는 Return T or F. 다음 토큰 있는지 체크하는 기능
            word = scanner.next();                                    // Note: next()는 space 발견까지 vs nextLine() 는 enter 발견까지
            myTrie.add(word.toLowerCase());
        }                                                             // ㄴ LowerCase to pass the test case.
        scanner.close();                                              // Close the file
    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        inputWord = inputWord.toLowerCase();
        Set<String> wordList = new TreeSet<>();
        String resultWord;
        if(myTrie.find(inputWord) != null) {    // if it finds the word, simply return the word
            return inputWord;
        }
        else {                               // Or if it can't find the word, check these things. (1-edit distance check)
            Deletion(inputWord, wordList);
            Transposition(inputWord, wordList);
            Alteration(inputWord, wordList);
            Insertion(inputWord, wordList);
            resultWord = CompareString(wordList);

            if (resultWord == null) {                           //if resultWord is null, find 2-edit distance
                Set<String> wordList2 = new TreeSet<>();  //Create a new world list
                for (String myVar : wordList) {                 //Iterate through previous word list
                    Deletion(myVar, wordList2);                 //Do the samething with the new word(myVar) and store the result in the new list (wordList2)
                    Transposition(myVar, wordList2);
                    Alteration(myVar, wordList2);
                    Insertion(myVar, wordList2);
                }
                resultWord = CompareString(wordList2);
            }
        }
        return resultWord;
    }

    private String CompareString(Set<String> list) {
        String finalWord = null;
        int highest = 0;
        for (String myVar : list) {
            if(myTrie.find(myVar) != null) {
                INode node = myTrie.find(myVar);
                if(node.getValue() > highest) {
                    highest = node.getValue();
                    finalWord = myVar;
                }
            }
        }
        return finalWord;
    }

    private void Deletion(String word, Set<String> list) {       //단어에 알파벳 하나씩 사라진캐이스 만들어서 리스트에추가
        for (int i = 0; i < word.length(); i++) {
            StringBuilder strB = new StringBuilder(word);        // 스트링빌더에 인풋월드 넣고
            strB.deleteCharAt(i);                                // i 번째에 있는 알파벳 지우고
            String str = new String(strB);
            list.add(str);                           // 그 단어를 리스트에 고대로 추가
        }
    }

    private void Transposition(String word, Set<String> list) {      // 단어 바로옆알파벳끼리 바뀐 캐이스 만들어서 리스트에추가.
        char[] letter = word.toCharArray();                          // 단어의 알파벳 하나씩 char 로 쪼개서 어레이로만듬      // 자바는 char 랑 string 이랑 좀 자유자제로 왔다갔따하는듯
        for (int i = 0; i < word.length()-1; i++) {
            char tempChar = letter[i];                               //142에서 하던 기본ㅋ 알파벳 양옆꺼 하나씩 그냥 바꾸는거
            letter[i] = letter[i+1];
            letter[i+1] = tempChar;
            String str = new String(letter);
            list.add(str);                                              //순서 하나씩바뀐 단어 리스트에 고대로추가              // 자바는 char 랑 string 이랑 좀 자유자제로 왔다갔따하는듯
            letter = word.toCharArray();                              // 알파벳 순서바꾼거 원상복구
        }
    }

    private void Alteration(String word, Set<String> list) {                  //단어에 알파벳 하나가 다른알파벳으로 바뀐 케이스 만들어서 리스트에추가
        for (int i = 0; i < word.length(); i++) {                             //더블 폴룹. i는 단어의 알파벳지칭하는 index #.
            for (char c = 'a'; c <= 'z'; c++) {                                    //This is how iterate a to z. (c 에 a부터 z 까지 저장됨)
                String str = word.substring(0,i) + c + word.substring(i+1);
                list.add(str);                                                  //고대로 리스트에 추가
            }
        }
    }

    private void Insertion(String word, Set<String> list) {                      //단어 사이사이에 다른알파벳하나 껴넣은 케이스 만들어서 리스트에 추가
        for (int i = 0; i <= word.length(); i++) {
            for (char c = 'a'; c <= 'z'; c++) {
                String str = word.substring(0,i) + c + word.substring(i);
                list.add(str);                                                   //고대로 리스트에 추가
            }
        }
    }
}
