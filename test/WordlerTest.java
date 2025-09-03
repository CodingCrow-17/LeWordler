package test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

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
        int[] results = {0,0,0,0,0,0,0};
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
                results[6] = results[6]+1;
            }
            else{
                results[turnsTaken-1] = results[turnsTaken-1]+1;
            }
            totalTurnsTaken = totalTurnsTaken+turnsTaken;
        }
        assertTrue("Failed on the following words: " + failList,passed);
        double average = ((double)totalTurnsTaken)/((double)words.size());
        System.out.println("average turns taken " +average);
        for (int i=0;i<6;i++){
            System.out.println(results[i]);
        }
    }
}
