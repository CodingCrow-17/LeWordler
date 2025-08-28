package src;

public class Adversary {
    private String word;
    public Adversary(){
        this.word = "honey";
    }
    public Adversary(String word){
        if (word.length() != 5){
            this.word = "honey";
        }
        this.word = word;
    }

    public String getWord(){
        return word;
    }

    public String checkGuess(String guess){
        String result = "";
        for (int i=0; i< 5; i++){
            if (word.substring(i,i+1).contains(guess.substring(i,i+1))){
                result = result+"g";
            }
            else if (word.contains(guess.substring(i,i+1))){
                result = result+"y";
            }
            else{
                result = result+"r";
            }
        }
        return result;
    }
}
