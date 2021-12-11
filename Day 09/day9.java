import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.awt.Point;


public class day9{
    public static int total;
    public static void main(String[] args){
        System.out.println(part1());
        System.out.println(part2());
    }
    public static int part1(){
        ArrayList<ArrayList<Integer>> data = openFile("input.txt");
        ArrayList<Integer> lowPoints = new ArrayList<Integer>();
        int r = 0, c = 0;
        for(ArrayList<Integer> row: data){
            for(int col: row){
                ArrayList<Boolean> surround = new ArrayList<Boolean>();
                // Only check above if not first row
                if(!(r == 0)){
                    surround.add(data.get(r - 1).get(c) > col);
                }
                // Only check below if not last row
                if(!(r == data.size() - 1)){
                    surround.add(data.get(r+1).get(c) > col);
                }
                // Only check left if not first column
                if(!(c == 0)){
                    surround.add(data.get(r).get(c-1) > col);
                }
                // Only check right if not last column
                if(!(c == row.size() - 1)){
                    surround.add(data.get(r).get(c+1) > col);
                }
                // If there are no surroundings greater than the col add it to lowPoints
                if(!(surround.contains(false))){
                    lowPoints.add(col);
                }
                c++;
            }
            c = 0;
            r++;
        }
        int total = 0;
        for(int low: lowPoints){
            total += 1 + low;
        }
        return total;
    }
    public static int part2(){
        // Most of this is similar to part 1
        ArrayList<ArrayList<Integer>> data = openFile("input.txt");
        ArrayList<Integer> basinSizes = new ArrayList<Integer>();
        int r = 0, c = 0;
        for(ArrayList<Integer> row: data){
            for(int col: row){
                // Look at each square and determine if it is a low point
                ArrayList<Square> surround = getSurroundings(r, c, data, row.size());
                boolean isLow = true;
                for(Square s: surround){
                    if(!(s.value > col)){
                        isLow = false;
                    }
                }
                // If it's a low point then recursively search for the basin
                if(isLow){
                    ArrayList<Square> alreadySeen = new ArrayList<Square>();
                    alreadySeen.add(new Square(new Point(r,c), col));
                    total = 1;
                    int size = recursiveSearch(alreadySeen.get(0), alreadySeen, data, row.size());
                    basinSizes.add(size);
                    
                }
                c++;
            }
            c = 0;
            r++;
        }
        // Sort the list
        Collections.sort(basinSizes);
        // Take the highest 3 items
        List<Integer> max3 = basinSizes.subList(basinSizes.size() - 3, basinSizes.size());
        // Calculate the product
        int result = 1;
        for(int b: max3){
            result *= b;
        }
        return result;
    }
    public static int recursiveSearch(Square s, ArrayList<Square> alreadySeen, ArrayList<ArrayList<Integer>> data, int rowSize){
        // Check the surroundings
        for(Square ss: getSurroundings(s.point.x, s.point.y, data, rowSize)){
            // If the square is new and not 9, increment the total and explore it's surroundings
            if(!alreadySeen.contains(ss) && ss.value != 9){
                total++;
                alreadySeen.add(ss);
                recursiveSearch(ss, alreadySeen, data, rowSize);
            }
        }
        return total;
    }
    public static ArrayList<Square> getSurroundings(int r, int c, ArrayList<ArrayList<Integer>> data, int rowSize){
        ArrayList<Square> surround = new ArrayList<Square>();
        // Only check above if not first row
        if(!(r == 0)){
            surround.add(new Square(new Point(r-1, c), data.get(r - 1).get(c)));
        }
        // Only check below if not last row
        if(!(r == data.size() - 1)){
            surround.add(new Square(new Point(r+1, c), data.get(r+1).get(c)));
        }
        // Only check left if not first column
        if(!(c == 0)){
            surround.add(new Square(new Point(r, c-1), data.get(r).get(c-1)));
        }
        // Only check right if not last column
        if(!(c == rowSize - 1)){
            surround.add(new Square(new Point(r, c+1), data.get(r).get(c+1)));
        }
        return surround;
    }   
    public static ArrayList<ArrayList<Integer>> openFile(String path){
        try{
            Scanner s = new Scanner(new File(path));
            ArrayList<ArrayList<Integer>> items = new ArrayList<ArrayList<Integer>>();
            while(s.hasNextLine()){
                ArrayList<Integer> row = new ArrayList<Integer>();
                for(String item: s.nextLine().split("")){
                    row.add(Integer.parseInt(item));
                }
                items.add(row);
            }
            s.close();
            return items;
        } catch(FileNotFoundException e){
            throw new RuntimeException(e);
        }
    }
}
/**
 * This class basically just stores stuff
 * but has an overridden equals method which
 * took longer than I would have liked to make
 * work.
 */
class Square{
    public Point point;
    public int value;
    public Square(Point point, int value){
        this.point = point;
        this.value = value;
    }
    @Override
    public boolean equals(Object o){
        if(o instanceof Square){
            Square p = (Square) o;
            return p.point.equals(point);
        }
        return false;        
    }
    @Override
    public String toString(){
        return "Point("+ point.x + ", " + point.y + ") Value = " + value;
    }
}