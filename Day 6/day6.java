import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;


public class day6{
    public static void main(String[] args){
        System.out.println(part1());
        System.out.println(part2());
    }
    public static int part1(){
        ArrayList<Integer> data = openFile("input.txt");
        for(int i = 0; i < 80; i++){
            for(int j = 0; j < data.size(); j++){
                int fish = data.get(j);
                if(fish == 0){
                    data.set(j, 6);
                    data.add(9);
                } else{
                    data.set(j, fish - 1);
                }
            }
        }
        return data.size();
    }
    public static long part2(){
        // The method for part 1 causes an OutOfMemoryError, so it had to be redone
        // This method also uses long instead of int because the result is hefty
        ArrayList<Integer> data = openFile("input.txt");
        ArrayList<Long> numFish = new ArrayList<Long>();
        // Populate numFish with input data
        for(int i = 0; i < 9; i++){
            long numOfThisFish = 0;
            for(int j = 0; j < data.size(); j++){
                if(data.get(j) == i){
                    numOfThisFish++;
                }
            }
            numFish.add(numOfThisFish);
        }
        for(int i = 0; i < 256; i++){
            long zeroVal = numFish.get(0);
            // Shift left
            for(int j = 1; j < numFish.size(); j++){
                numFish.set(j-1, numFish.get(j));
            }
            // Do zero stuff
            numFish.set(6, numFish.get(6) + zeroVal);
            numFish.set(8, zeroVal);
        }
        // Get total
        long total = 0;
        for(int i = 0; i < numFish.size(); i++){
            total += numFish.get(i);
        }
        return total;
    }
    public static ArrayList<Integer> openFile(String path){
            try{
                Scanner s = new Scanner(new File(path));
                ArrayList<Integer> lines = new ArrayList<Integer>();
                while(s.hasNextLine()){
                    String[] split = s.nextLine().split(",");
                    for(int i = 0; i < split.length; i++){
                        lines.add(Integer.parseInt(split[i]));
                    }
                }
                s.close();
                return lines;
            } catch(FileNotFoundException e){
                throw new RuntimeException(e);
            }
        }
}