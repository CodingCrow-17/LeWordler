package src;
import java.util.ArrayList;
import java.util.Arrays;

public class Wordler {
    private ArrayList<String> possibleWords;
    private ArrayList<String> guessingWords;
    private ArrayList<String> guessedWords;
    private ArrayList<String> results;
    private String bestResult = "00000";
    private final int GUESS_LIMIT = 6;
    private final int WORD_SIZE = 5;
    private final int ASCII_LOWERCASE_A = 97;
    private ArrayList<String> redletters;

    public Wordler(ArrayList<String> words){
        possibleWords = (ArrayList<String>)words.clone();
        guessingWords = (ArrayList<String>)words.clone();
        guessedWords = new ArrayList<String>();
        redletters = new ArrayList<String>();
        results = new ArrayList<String>();
    }

    public String firstGuess(){
        String first = "alert"; //calculateFrequencyBasedGuess();
        guessedWords.add(first);
        return(first);
    }

    public String calculateNextGuess(String lastResult){
        results.add(lastResult);
        String lastGuess = guessedWords.get(guessedWords.size()-1);
        updateRedLetters(lastGuess, lastResult);
        filterPossibleWords(lastGuess, lastResult);
        updateBestResult(lastGuess,lastResult);
        String nextGuess = "";
        if (guessedWords.size() == 5){
            nextGuess = possibleWords.get(0);
        }
        else if (possibleWords.size() <= ((GUESS_LIMIT-1)-guessedWords.size())){
            nextGuess = possibleWords.get(0);
        }
        else{
            nextGuess = calculateFrequencyBasedGuess();
        }
        guessedWords.add(nextGuess);
        return nextGuess;
    }

    private void updateBestResult(String lastGuess, String lastResult){
        for (int i=0;i<WORD_SIZE;i++){
            if (lastResult.charAt(i) == 'g'){
                bestResult = bestResult.substring(0,i)+lastGuess.charAt(i)+bestResult.substring(i+1,5);
            }
        }
        //extra deductions
        String firstPossible = possibleWords.get(0);
        for (int i=0; i<WORD_SIZE; i++){
            if (bestResult.charAt(i) == '0'){
                char potentialLetter = firstPossible.charAt(i);
                boolean common = true;
                for(String possibleWord : possibleWords){
                    common = common && (possibleWord.charAt(i)==potentialLetter);
                }
                if (common){
                    bestResult = bestResult.substring(0,i)+potentialLetter+bestResult.substring(i+1,5);
                }
            }
        }
    }

    private String calculateFrequencyBasedGuess(){
        int[] scoringTable = generateScoringTable();
        guessingWords.sort(
            (a,b) -> {return ((Integer)sumScore(b, scoringTable)).compareTo(((Integer)sumScore(a, scoringTable)));}
        );
        int index = 0;
        String guessWord = guessingWords.get(index);
        while (guessedWords.contains(guessWord)){
            index++;
            guessWord = guessingWords.get(index);
        }

        return guessWord;
    }

    private int[] generateScoringTable(){
        int[] scoringTable = new int[26];
        for (String possibleWord : possibleWords){
            for (int i = 0; i<WORD_SIZE;i++){
                if (bestResult.charAt(i) == '0'){
                    int index = possibleWord.charAt(i)-ASCII_LOWERCASE_A;
                    scoringTable[index] = scoringTable[index]+1;
                }
            }
        }
        for (String redletter : redletters){
            char charLetter = redletter.charAt(0);
            int index = charLetter-ASCII_LOWERCASE_A;
            scoringTable[index] = 0;
        }
        return scoringTable;
    }

    private int sumScore(String word, int[] scoringTable){
        int score = 0;
        ArrayList<String> pickedLetters = new ArrayList<String>();
        for (int i=0; i<WORD_SIZE; i++){
            char letter = word.charAt(i);
            if (pickedLetters.contains(String.valueOf(letter)) == false){
                if (bestResult.charAt(i) != letter){
                    int index = letter-ASCII_LOWERCASE_A;
                    score = score+scoringTable[index];
                    pickedLetters.add(String.valueOf(letter));
                }
            }
        }
        return score;
    }

    private void updateRedLetters(String lastGuess, String lastResult){
        for (int i=0;i<WORD_SIZE;i++){
            if (lastResult.charAt(i) == 'r'){
                if (redletters.indexOf(String.valueOf(lastGuess.charAt(i))) < 0 ){
                    redletters.add(String.valueOf(lastGuess.charAt(i)));
                }
            }
        }
    }

    private void filterPossibleWords(String lastGuess, String lastResult){
        ArrayList<String> removalList = new ArrayList<String>();
        for (String possibleword : possibleWords){
            boolean remove = false;
            for (int i=0;i<WORD_SIZE;i++){
                remove = remove 
                    || (lastResult.charAt(i)=='g' && lastGuess.charAt(i)!=possibleword.charAt(i)) 
                    || (lastResult.charAt(i)!='g' && lastGuess.charAt(i)==possibleword.charAt(i)) 
                    || ((lastResult.charAt(i))=='y' && !possibleword.contains(lastGuess.substring(i, i+1)))
                    || ((lastResult.charAt(i))=='r' && possibleword.contains(lastGuess.substring(i, i+1)) && !bestResult.contains(lastGuess.substring(i, i+1)));
            }
            if (remove)
                removalList.add(possibleword);
        }
        possibleWords.removeAll(removalList);
    }

}
