import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Stack;

public class day10{
    public static void main(String[] args){
        System.out.println(solution(false));
        System.out.println(solution(true));
    }
    public static long solution(boolean isPart2){
        ArrayList<String[]> data = openFile("input.txt");
        // The scores which make up the error scores
        HashMap<String, Integer> errorScores = new HashMap<String, Integer>();
        errorScores.put(")", 3);
        errorScores.put("]", 57);
        errorScores.put("}", 1197);
        errorScores.put(">", 25137);
        // The scores which make up the completion score
        HashMap<String, Integer> closingScores = new HashMap<String, Integer>();
        closingScores.put(")", 1);
        closingScores.put("]", 2);
        closingScores.put("}", 3);
        closingScores.put(">", 4);
        HashMap<String, String> reverseBracket = new HashMap<String, String>();
        reverseBracket.put("(",")");
        reverseBracket.put("[", "]");
        reverseBracket.put("{", "}");
        reverseBracket.put("<", ">");
        // Some of the scores are very big
        ArrayList<Long> scores = new ArrayList<Long>();
        int errorTotal = 0;
        long closeTotal = 0;
        int lineNo = 0;
        int colNo = 0;
        for(String[] line: data){
            Stack<String> brackets = new Stack<String>();
            colNo = 0;
            closeTotal = 0;
            for(String c: line){
                if(c.matches("[(\\[{<]")){
                    // Opening brackets
                    brackets.add(c);
                } else if(c.matches("[)\\]}>]")){
                    // Closing brackets
                    String closing  = reverseBracket.get(brackets.peek());
                    if(!(closing.equals(c))){
                        // There is a bad closing bracket
                        if(!isPart2){
                            System.out.println("Error on line " + lineNo + ": " + "Expected " + closing + " but found " + c);
                        }
                        errorTotal += errorScores.get(c);
                        break;

                    } else{
                        brackets.pop();
                    }
                }
                if((colNo == line.length - 1) && isPart2){
                    // The end has been reached without error, start repairing
                    while(!brackets.empty()){
                        closeTotal *= 5;
                        closeTotal += closingScores.get(reverseBracket.get(brackets.pop()));
                    }
                    scores.add(closeTotal);
                }
                colNo++;
            }
            lineNo++;
        }
        if(isPart2){
            Collections.sort(scores);
            // System.out.println(scores.size());
            System.out.println(scores);
            System.out.println(scores.size() + "|" + (scores.size()/2));
            // System.out.println(5/2);
            return scores.get((scores.size()/2));
        } else{
            return errorTotal;
        }
    }
    public static ArrayList<String[]> openFile(String path){
        try{
            Scanner s = new Scanner(new File(path));
            ArrayList<String[]> items = new ArrayList<String[]>();
            while(s.hasNextLine()){
                items.add(s.nextLine().split(""));
            }
            s.close();
            return items;
        } catch(FileNotFoundException e){
            throw new RuntimeException(e);
        }
    }
}