import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;


public class day7{
    public static void main(String[] args){
        System.out.println(part1());
        System.out.println(part2());
    }
    public static int part1(){
        ArrayList<Integer> crabs = openFile("input.txt");
        HashSet<Integer> unique = new HashSet<Integer>(crabs);
        int minDiff = -1;
        for(int target: unique){
            int totalDiff = 0;
            for(int item: crabs){
                totalDiff += Math.abs(item - target);
            }
            if(totalDiff < minDiff || minDiff == -1){
                minDiff = totalDiff;
            }
        }
        return minDiff;
    }
    public static int part2(){
        ArrayList<Integer> crabs = openFile("input.txt");
        int minCrab = -1;
        int maxCrab = -1;
        for(int crab: crabs){
            if(crab < minCrab || minCrab == -1){
                minCrab = crab;
            }
            else if(crab > maxCrab || maxCrab == -1){
                maxCrab = crab;
            }
        }
        int minDiff = -1;
        // For every horizontal target
        for(int i = minCrab; i <= maxCrab; i++){
            int totalDiff = 0;
            // For each crab
            for(int item: crabs){
                // Get the sum distance between them
                for(int j = 1; j <= Math.abs(item - i); j++){
                    totalDiff += j;
                }
            }
            if(totalDiff < minDiff || minDiff == -1){
                minDiff = totalDiff;
            }
        }
        return minDiff;
    }
    public static ArrayList<Integer> openFile(String path){
        try{
            Scanner s = new Scanner(new File(path));
            ArrayList<Integer> items = new ArrayList<Integer>();
            while(s.hasNextLine()){
                for(String item: s.nextLine().split(",")){
                    items.add(Integer.parseInt(item));
                }
            }
            s.close();
            return items;
        } catch(FileNotFoundException e){
            throw new RuntimeException(e);
        }
    }
}