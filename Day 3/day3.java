import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.RuntimeException;

public class day3{
    public static void main(String[] args){
        System.out.println(part1());
        System.out.println(part2());
    }
    public static int part1(){
        // Read the text file into an ArrayList of string arrays
        ArrayList<String[]> lines = openFile();
        // Transpose the ArrayList of string arrays
        ArrayList<String[]> transpose = new ArrayList<String[]>();
        int width = lines.get(0).length;
        for(int l=0; l < width; l++){
            transpose.add(new String[lines.size()]);
        }
        for(int i=0; i<lines.size(); i++){
            String[] line = lines.get(i);
            for(int j=0; j<line.length; j++){
                transpose.get(j)[i] = line[j];
            }
        }
        // Calculate the gamma/epsilon by counting the zeros, then appending
        // the appropriate most common bit to the string.
        int zeroCount;
        int length;
        String gamma = "";
        String epsilon = "";
        for(int i = 0; i<transpose.size(); i++){
            zeroCount = 0;
            length = transpose.get(i).length;
            for(int j = 0; j < length; j++){
                if(transpose.get(i)[j].equals("0")){
                    zeroCount++;
                }
            }
            gamma += (zeroCount > (length-zeroCount) ? "0" : "1");
            epsilon += (zeroCount < (length-zeroCount) ? "0" : "1");
        }
        // Convert binary string to int
        int gammaInt = Integer.parseInt(gamma, 2);
        int epsilonInt = Integer.parseInt(epsilon, 2);
        return gammaInt * epsilonInt;
    }
    public static int part2(){
        // Read the text file
        ArrayList<String[]> lines = openFile();
        // Make copy arrays
        ArrayList<ArrayList<String[]>> lists = new ArrayList<ArrayList<String[]>>(2);
        ArrayList<String[]> oxygen = new ArrayList<String[]>(lines);
        ArrayList<String[]> co2 = new ArrayList<String[]>(lines);
        lists.add(oxygen);
        lists.add(co2);
        int lineWidth = lines.get(0).length;
        int zeroCount = 0;
        int oneCount = 0;
        // For every column
        for(int col = 0; col < lineWidth; col++){
            // For each list
            for(int list = 0; list < lists.size(); list++){
                // Count the 0s
                ArrayList<String[]> thisList = lists.get(list);
                zeroCount = 0;
                for(int row = 0; row < thisList.size(); row++){
                    if(thisList.get(row)[col].equals("0")){
                        zeroCount++;
                    }
                }
                // Remove the non-matching values for every column
                oneCount = thisList.size() - zeroCount;
                if(zeroCount <= oneCount){
                    removeLines(thisList, col, list == 0 ? "0" : "1");
                } else{
                    removeLines(thisList, col, list == 0 ? "1" : "0");
                }
            }            
        }
        // Convert string to int
        int oxygenInt = Integer.parseInt(String.join("", oxygen.get(0)), 2);
        int co2Int = Integer.parseInt(String.join("", co2.get(0)), 2);
        return oxygenInt * co2Int;
    }
    public static ArrayList<String[]> openFile(){
        try{
            Scanner s = new Scanner(new File("input.txt"));
            ArrayList<String[]> lines = new ArrayList<String[]>();
            while(s.hasNext()){
                lines.add(s.next().split(""));
            }
            s.close();
            return lines;
        } catch(FileNotFoundException e){
            throw new RuntimeException(e);
        }
    }
    public static void removeLines(ArrayList<String[]> arr, int col, String rem){
        // Iterate through array and remove appropriate lines
        Iterator<String[]> i = arr.iterator();
        while(i.hasNext()){
            String[] line = i.next();
            if(line[col].equals(rem) && arr.size() != 1){
                i.remove();
            }
        }
    }
}