import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;

public class day1{
    // The "throws FileNotFoundException" is required because Scanner creates a checked exception.
    public static void main(String[] args) throws FileNotFoundException{
        System.out.println(part1());
        System.out.println(part2());
    }
    public static int part1() throws FileNotFoundException{
        // Create a scanner object from the text file
        Scanner s = new Scanner(new File("input.txt"));
        int increases = 0;
        int prev = -1;
        int thisInt;
        // While there are lines in the text file
        while (s.hasNext()){
            // Convert the string to an integer
            thisInt = Integer.parseInt(s.next());
            // If it's larger than the previous and not the number, increment "increases"
            if(thisInt > prev && prev != -1){
                increases++;
            }
            // Set the previous integer to this integer
            prev = thisInt;
        }
        // Close the scanner and print the result
        s.close();
        return increases;
    }
    public static int part2() throws FileNotFoundException{
        Scanner s = new Scanner(new File("input.txt"));
        int increases = 0;
        int prevSum = -1;
        int thisSum = 0;
        int thisInt;
        LinkedList<Integer> previous = new LinkedList<Integer>();
        // For each line in the file
        while (s.hasNext()){
            // Parse the integer and append it to the list
            thisInt = Integer.parseInt(s.next());
            previous.add(thisInt);
            // If there are 3 items, compare sums and then remove the first one
            if(previous.size() == 3){
                // Sum the list
                for(int i = 0; i < previous.size(); i++){
                    thisSum += previous.get(i);
                }
                // Compare the sums
                if(thisSum > prevSum && prevSum != -1){
                    increases++;
                }
                // Remove the first item in the list
                previous.remove();
                prevSum = thisSum;
                thisSum = 0;
            }
        }
        s.close();
        return increases;
    }
}