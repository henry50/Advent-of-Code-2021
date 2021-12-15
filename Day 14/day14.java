import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class day14{
    public static void main(String[] args) {
        System.out.println(part1());
        System.out.println(part2());
    }
    public static int part1(){
        Data data = openFile("input.txt");
        // Repeat 10 times
        for(int i = 0; i < 10; i++){
            ArrayList<Pair> insertions = new ArrayList<Pair>();
            int insertOffset = 0;
            for(int j = 1; j < data.base.size(); j++){
                String pair = data.base.get(j-1) + data.base.get(j);
                String insert = data.pairs.get(pair);
                if(insert != null){
                    insertions.add(new Pair(insert, insertOffset + j));
                    insertOffset++;
                }
            }
            for(Pair p: insertions){
                data.base.add(p.position, p.element);
            }
        }
        // Count elements
        HashMap<String, Integer> counts  = new HashMap<String, Integer>();
        for(String elem: data.base){
            int count;
            if(counts.get(elem) != null){
                count = counts.get(elem) + 1;
            } else{
                count = 1;
            }
            counts.put(elem, count);
        }
        // Find max/min
        int max = 0;
        int min = -1;
        for(String s: counts.keySet()){
            if(counts.get(s) > max){
                max = counts.get(s);
            } else if(counts.get(s) < min || min == -1){
                min = counts.get(s);
            }
        }
        return max - min;
    }
    public static long part2(){
        Data data = openFile("input.txt");
        String baseStr = String.join("", data.base);
        HashMap<String, Long> counts = new HashMap<String, Long>();
        String firstPair = baseStr.substring(0,2);
        boolean firstSet = false;
        for(int i = 1; i < baseStr.length(); i++){
            String pair = baseStr.substring(i-1, i+1);
            incrementKey(pair, 1, counts);
        }
        final int iterations = 40;
        for(int i = 0; i < iterations; i++){
            HashMap<String, Long> thisCount = new HashMap<String, Long>();
            for(String key: counts.keySet()){
                String k = data.pairs.get(key);
                String pair1 = key.charAt(0) + k;
                if(key.equals(firstPair) && !firstSet){
                    firstPair = pair1;
                    firstSet = true;
                }
                incrementKey(pair1, counts.get(key), thisCount);
                incrementKey(k + key.charAt(1), counts.get(key), thisCount);
            }
            firstSet = false;
            counts = thisCount;
        }
        HashMap<String, Long> totals = new HashMap<String, Long>();
        boolean firstDone = false;
        // Calculate total number of elements from all pairs
        for(String pair: counts.keySet()){
            // Count each pair n times
            if(pair.equals(firstPair) && !firstDone){
                // Add both elements of the first pair
                incrementKey(String.valueOf(pair.charAt(0)), 1, totals);
                firstDone = true;
            }
            incrementKey(String.valueOf(pair.charAt(1)), counts.get(pair), totals);
        }
        long max = 0;
        long min = -1;
        for(String element: totals.keySet()){
            if(totals.get(element) > max){
                max = totals.get(element);
            }
            if(totals.get(element) < min || min == -1){
                min = totals.get(element);
            }
        }
        long result = max - min;
        return result;
    }
    public static void incrementKey(String key, long amount, HashMap<String, Long> map){
        if(map.get(key) == null){
            map.put(key, (long)amount);
        } else{
            map.put(key, map.get(key) + amount);
        }
    }
    public static Data openFile(String path){
        try{
            Scanner s = new Scanner(new File(path));
            // Take the first line and split it into it's elements
            ArrayList<String> base = new ArrayList<String>(Arrays.asList(s.nextLine().split("")));
            // Hashmap for pairs
            HashMap<String, String> pairs = new HashMap<String, String>();
            s.nextLine(); // Skip the blank
            // Get the pairs
            while(s.hasNextLine()){
                String[] parts = s.nextLine().split(" -> ");
                pairs.put(parts[0], parts[1]);
            }
            s.close();
            return new Data(base, pairs);
        } catch(FileNotFoundException e){
            throw new RuntimeException(e);
        }
    }
}


class Pair{
    String element;
    int position;
    public Pair(String element, int position){
        this.element = element;
        this.position = position;
    }
}

class Data{
    ArrayList<String> base;
    HashMap<String, String> pairs;
    public Data(ArrayList<String> base, HashMap<String, String> pairs){
        this.base = base;
        this.pairs = pairs;
    }
}