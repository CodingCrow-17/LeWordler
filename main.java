import java.util.ArrayList;
import java.util.Scanner;

import src.Adversary;
import src.WordReader;
import src.Wordler;

public class main {
    public static void main(String[] args){
        ArrayList<String> words = WordReader.readWords("./wordleWords.txt");
        Wordler wordler = new Wordler(words); 
        Scanner scanner = new Scanner(System.in);
        boolean playGame=true;
        while (playGame){
            System.out.println("let's play");
            String result = "00000";
            printGuess(wordler.firstGuess());
            while (result.equals("ggggg") == false){
                System.out.println("please enter the result (g for green, y for yellow, r for blank)");
                result = scanner.nextLine();
                printGuess(wordler.calculateNextGuess(result));
            }
            System.out.println("all done!");
            playGame = false;
        }
    }

    private static void printGuess(String guess){
        System.out.println("we guess "+ guess);
    }
}
