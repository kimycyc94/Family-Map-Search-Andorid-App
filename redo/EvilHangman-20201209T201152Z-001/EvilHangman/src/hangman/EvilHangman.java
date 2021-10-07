package hangman;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class EvilHangman {

    public static void main(String[] args) throws IOException, EmptyDictionaryException, BadInputException {
        String dictionary = "";
        int wordLength = 0;
        int numGuess = 0;

        if(args.length == 3) {
            dictionary = args[0];
            wordLength = Integer.parseInt(args[1]);
            numGuess = Integer.parseInt(args[2]);
        }
        else {
            System.out.println("Invalid inputs. Your inputs should be: 1. Dictionary 2. word length 3. number of guesses.");
        }
        EvilHangmanGame game = new EvilHangmanGame();
        game.startGame(new File(dictionary), wordLength);
        playGame(game, numGuess);
    }

    public static void playGame(EvilHangmanGame game, int guess) throws BadInputException {
        boolean endWhile = true;
        int count = 0;
        Set<Character> usedLetters = new TreeSet<>();
        while(guess > 0 && endWhile) {                 // When guess == 0 or endWhile is false, end while loop.
            printGame(game, guess, usedLetters);
            Scanner scan = new Scanner(System.in);
            String guessed = scan.next().toLowerCase();
            try {
                if (guessed.length() > 1) {
                    throw new BadInputException();
                }
                char guessChar = guessed.charAt(0);
                if (!Character.isLetter(guessChar)) {
                    throw new BadInputException();
                }

                int freq = 0;
                Set<String> saved = game.makeGuess(guessChar);
                if (!saved.iterator().next().contains(Character.toString(guessChar))) {
                    response(false, guessChar, 0);
                    guess--;
                }
                else {
                    for (int i = 0; i < game.getWordPattern().length(); i++) {
                        if (game.getWordPattern().charAt(i) == guessChar) {
                            freq++;
                        }
                    }
                    response(true, guessChar, freq);
                }

                usedLetters.add(guessChar);
            }
            catch (BadInputException error) {
                System.out.println("Invalid input\n");
            }
            catch (GuessAlreadyMadeException error) {
                System.out.println("Guess already made\n");

            }
            count++;
            if (count == 26) {         // used all alphabets
                endWhile = false;
            }
            if (!game.getWordPattern().contains("*")) {
                endWhile = false;
            }
        }

        boolean winGame = true;
        for(int i = 0; i < game.getWordPattern().length(); i++) {
            if (game.getWordPattern().charAt(i) == '*') {
                winGame = false;
            }
            else {
                winGame = true;
            }
        }
        if (winGame) {
            notLikelyHappen(game.getFinalWord());
        }
        else {
            userLost(game.getFinalWord());
        }
    }

    private static void response(boolean itContains, char guessLetter, int num) {             // Print when user guesses alphabets
        if (itContains) {
            System.out.println("Yes, there is " + num + " " + guessLetter + "\n");
        }
        else {
            System.out.println("Sorry, there are no " + guessLetter + "\n");
        }
    }

    public static void printGame(EvilHangmanGame game, int guessNum, Set<Character> usedAlphabets) {   // Print 게임진행을 위한 strings
        StringBuilder strB = new StringBuilder("");
        String pattern = game.getWordPattern();
        System.out.println("You have " + guessNum + " guesses left");
        strB.append("[");
        for (Character str : usedAlphabets) {
            if (strB.length() > 1) {
                strB.append(", ");
            }
            strB.append(str);
        }
        strB.append("]");                       // 마지막 콜럼 지우고 브레킷으로 대체
        System.out.println("Used letters: " + new String(strB));                    //이거 strB.toString()로 넣어도 됨
        System.out.println("Word: " + pattern);
        System.out.println("Enter guess: ");
    }

    // Print the result //
    private static void userLost(String word) {                                          //When you lost (duh)
        System.out.println("Sorry, you lost! The word was: " + word);
        System.exit(0);                                                     //Terminate the program
    }

    private static void notLikelyHappen(String word) {                                  //No way, you won?
        System.out.println("You win! You guessed the word: " + word);
        System.exit(0);                                                     //Terminate the program
    }
    // Done //
}
