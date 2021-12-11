import java.awt.Point;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

// This is really slow but I've already wasted enough time now.
public class day5{
    public static void main(String[] args){
        System.out.println(part1());
        System.out.println(part2());
    }
    public static int part1(){
        return solution(false);
    }
    public static int part2(){
        return solution(true);
    }
    public static int solution(boolean allowDiag){
        ArrayList<Line> data = openFile("input.txt");
        ArrayList<Point> pointsSeen = new ArrayList<Point>();
        ArrayList<Integer> numSeen = new ArrayList<Integer>();
        for(int i = 0; i < data.size(); i++){
            // Only consider horiz/vert lines
            if(!data.get(i).diag || allowDiag){
                ArrayList<Point> p = data.get(i).getPoints();
                for(int j = 0; j < p.size(); j++){
                    Point pp = p.get(j);
                    if(pointsSeen.contains(pp)){
                        int index = pointsSeen.indexOf(pp);
                        numSeen.set(index, numSeen.get(index) + 1);
                    } else{
                        pointsSeen.add(pp);
                        numSeen.add(1);
                    }
                }
            }
        }
        int dangers = 0;
        for(int i = 0; i < pointsSeen.size(); i++){
            if(numSeen.get(i) >= 2){
                dangers++;
            }
        }
        return dangers;
    }
    public static ArrayList<Line> openFile(String path){
        try{
            Scanner s = new Scanner(new File(path));
            ArrayList<Line> lines = new ArrayList<Line>();
            while(s.hasNextLine()){
                // Split into start and end
                String[] pointsStr = s.nextLine().split(" -> ");
                Point[] points = new Point[2];
                // For each start/end, split into x,y and make a Point object
                for(int x = 0; x < pointsStr.length; x++){
                    String[] xy = pointsStr[x].split(",");
                    Point p = new Point(Integer.parseInt(xy[0]), Integer.parseInt(xy[1]));
                    points[x] = p;
                }
                // Create a new line from the points
                Line l = new Line(points[0], points[1]);
                lines.add(l);
            }
            s.close();
            return lines;
        } catch(FileNotFoundException e){
            throw new RuntimeException(e);
        }
    }

}
class Line{
    public Point start;
    public Point end;
    public int grad;
    public boolean yline;
    public boolean diag;
    public int yintercept;
    public Line(Point start, Point end){
        this.start = start;
        this.end = end;
        if(end.x - start.x == 0){
            this.yline = true;
        } else{
            this.yline = false;
            this.grad = (end.y-start.y)/(end.x-start.x);
            this.yintercept = (start.y - (grad * start.x));
        }
        this.diag = !(this.yline || this.grad == 0);
    }
    public ArrayList<Point> getPoints(){
        ArrayList<Point> points = new ArrayList<Point>();
        int maxX = start.x > end.x ? start.x : end.x;
        int minX = start.x < end.x ? start.x : end.x;
        int maxY = start.y > end.y ? start.y : end.y;
        int minY = start.y < end.y ? start.y : end.y;
        if(this.yline){
            // Gradient is undefined, no y intercept
            for(int y = minY; y <= maxY; y++){
                points.add(new Point(maxX,y));
            }
        } else{
            for(int x = minX; x <= maxX; x++){
                int y = (int)((grad * x) + yintercept);
                points.add(new Point(x, y));
            }
        }
        return points;
    }
}   