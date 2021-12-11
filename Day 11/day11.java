import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.awt.Point;

public class day11{
    public static void main(String[] args){
        System.out.println(part1());
        System.out.println(part2());
    }
    public static int part1(){
        ArrayList<ArrayList<Integer>> data = openFile("input.txt");
        int flashes = 0;
        for(int i = 1; i <= 100; i++){
            // Increment all light levels by 1
            for(int r = 0; r < data.size(); r++){
                for(int c = 0; c < data.get(r).size(); c++){
                    incrementCell(data, r, c);
                }
            }
            ArrayList<Point> zeroList = new ArrayList<Point>();
            // While there are flashes, flash them
            while(containsFlash(data)){
                outerloop:
                for(int r = 0; r < data.size(); r++){
                    for(int c = 0; c < data.get(r).size(); c++){
                        if(data.get(r).get(c) > 9){
                            flash(r, c, data.get(r).size()-1, data.size()-1, data, zeroList);
                            flashes++;
                            break outerloop;
                        }
                    }
                }
            }
            // Zero all flashes at the end of the step
            for(Point p: zeroList){
                data.get(p.x).set(p.y, 0); 
            }
            zeroList.clear();
        }
        return flashes;
    }
    public static void flash(int r, int c, int rmax, int cmax, ArrayList<ArrayList<Integer>> data, ArrayList<Point> zeroList){
        if(r != 0){
            // Not first row
            incrementCell(data, r-1, c);
            if(c != 0){
                // Not top left
                incrementCell(data, r-1, c-1);
            }
            if(c != cmax){
                // Not top right
                incrementCell(data, r-1, c+1);
            }
        }
        if(r != rmax){
            // Not last row
            incrementCell(data, r+1, c);
            if(c != 0){
                // Not bottom left
                incrementCell(data, r+1, c-1);
            }
            if(c != cmax){
                // Not bottom right
                incrementCell(data, r+1, c+1);
            }
        }
        if(c != 0){
            // Not first column
            incrementCell(data, r, c-1);
        }
        if(c != cmax){
            // Not last column
            incrementCell(data, r, c+1);
        }
        data.get(r).set(c, -100); // Make sure this point will not be flashed again this step
        zeroList.add(new Point(r,c)); // Add to list of points to zero later
    }
    public static boolean containsFlash(ArrayList<ArrayList<Integer>> data){
        for(ArrayList<Integer> i: data){
            for(int j: i){
                if(j > 9){
                    return true;
                }
            }
        }
        return false;
    }
    public static void incrementCell(ArrayList<ArrayList<Integer>> data, int r, int c){
        // Increments cell and returns true if new value is 9
        int incr = data.get(r).get(c) + 1;
        data.get(r).set(c, incr);

    }
    public static int part2(){
        ArrayList<ArrayList<Integer>> data = openFile("input.txt");
        int steps = 0;
        while(true){
            // Increment all light levels by 1
            for(int r = 0; r < data.size(); r++){
                for(int c = 0; c < data.get(r).size(); c++){
                    incrementCell(data, r, c);
                }
            }
            ArrayList<Point> zeroList = new ArrayList<Point>();
            // While there are flashes, flash them
            while(containsFlash(data)){
                outerloop:
                for(int r = 0; r < data.size(); r++){
                    for(int c = 0; c < data.get(r).size(); c++){
                        if(data.get(r).get(c) > 9){
                            flash(r, c, data.get(r).size()-1, data.size()-1, data, zeroList);
                            break outerloop;
                        }
                    }
                }
            }
            steps++;
            if(zeroList.size() == (data.size() * data.get(0).size())){
                return steps;
            }
            // Zero all flashes at the end of the step
            for(Point p: zeroList){
                data.get(p.x).set(p.y, 0); 
            }
            zeroList.clear();
        }
    }
    public static ArrayList<ArrayList<Integer>> openFile(String path){
        try{
            Scanner s = new Scanner(new File(path));
            ArrayList<ArrayList<Integer>> items = new ArrayList<ArrayList<Integer>>();
            while(s.hasNextLine()){
                ArrayList<Integer> line = new ArrayList<Integer>();
                for(String r: s.nextLine().split("")){
                    line.add(Integer.parseInt(r));
                }
                items.add(line);
            }
            s.close();
            return items;
        } catch(FileNotFoundException e){
            throw new RuntimeException(e);
        }
    }
}