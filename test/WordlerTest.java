package test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import src.Adversary;
import src.WordReader;
import src.Wordler;

public class WordlerTest {

    @Test
    public void fullListTest(){
        boolean passed = true;
        ArrayList<String> failList = new ArrayList<String>();
        long totalTurnsTaken = 0;
        ArrayList<String> words = WordReader.readWords("././wordleWords.txt");
        for (String word : words){
            Wordler wordler = new Wordler(words);
            Adversary adversary = new Adversary(word);
            String res = adversary.checkGuess(wordler.firstGuess());
            int turnsTaken =1;
            while (!res.equals("ggggg")){
                res = adversary.checkGuess(wordler.calculateNextGuess(res));
                turnsTaken++;
            }
            if (turnsTaken >6){
                passed = false;
                failList.add(word);
            }
            totalTurnsTaken = totalTurnsTaken+turnsTaken;
        }
        assertTrue("Failed on the following words: " + failList,passed);
        double average = ((double)totalTurnsTaken)/((double)words.size());
        System.out.println("average time taken " +average);
    }
}
