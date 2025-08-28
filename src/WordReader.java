package src;
import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;

public class WordReader {
    public static ArrayList<String> readWords(String relativeFilePath){
        ArrayList<String> wordList = new ArrayList<String>();
        try{
            File file = new File(relativeFilePath);
            Scanner reader = new Scanner(file); 
            while (reader.hasNextLine()){
                String word = reader.nextLine();
                wordList.add(word);
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("An error occured reading the file");
            e.printStackTrace();
        }
        return wordList;     
    }
}
