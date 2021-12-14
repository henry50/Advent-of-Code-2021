import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.awt.Point;

public class day13{
    public static void main(String[] args){
        System.out.println(solution(false));
        solution(true);
    }
    public static int solution(boolean isPart2){
        Data data = openFile("input.txt");
        ArrayList<Fold> folds;
        if(isPart2){
            folds = data.folds;
        } else{
            folds = new ArrayList<Fold>();
            folds.add(data.folds.get(0));
        }
        for(Fold f: folds){
            switch(f.type){
                case UP:
                    ArrayList<Point> above = new ArrayList<Point>();
                    ArrayList<Point> below = new ArrayList<Point>();
                    // Get points below fold line
                    for(Point p: data.points){
                        if(p.y > f.coord){
                            below.add(p);
                        } else{
                            above.add(p);
                        }
                    }
                    // Fold points below the line up
                    for(Point p: below){
                        p.y = f.coord -  (p.y - f.coord);
                    }
                    data.points = union(above, below);
                    break;
                case LEFT:
                    ArrayList<Point> left = new ArrayList<Point>();
                    ArrayList<Point> right = new ArrayList<Point>();
                    // Get points right of fold line
                    for(Point p: data.points){
                        if(p.x > f.coord){
                            right.add(p);
                        } else{
                            left.add(p);
                        }
                    }
                    // Fold points right of line left
                    for(Point p: right){
                        p.x = f.coord - (p.x - f.coord);
                    }
                    data.points = union(left, right);
                    break;
            }
        }
        if(isPart2){
            printPoints(data.points);
            return 0;
        } else{
            return data.points.size();
        }
    }
    public static void printPoints(ArrayList<Point> a){
        int maxX = 0;
        int maxY = 0;
        for(Point p: a){
            if(p.x > maxX){
                maxX = p.x;
            } else if(p.y > maxY){
                maxY = p.y;
            }
        }
        for(int y = 0; y <= maxY; y++){
            for(int x = 0; x <= maxX; x++){
                if(a.contains(new Point(x,y))){
                    System.out.print("█");
                } else{
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
    public static ArrayList<Point> union(ArrayList<Point> a, ArrayList<Point> b){
        ArrayList<Point> result = new ArrayList<Point>(a);
        for(Point p: b){
            if(!result.contains(p)){
                result.add(p);
            }
        }
        return result;
    }
    public static Data openFile(String path){
        try{
            Scanner s = new Scanner(new File(path));
            ArrayList<Point> points = new ArrayList<Point>();
            ArrayList<Fold> folds = new ArrayList<Fold>();
            boolean split = false;
            while(s.hasNextLine()){
                String nextLine = s.nextLine();
                if(nextLine.length() == 0){
                    // Split points and folds
                    split = true;
                    continue;
                }
                if(!split){
                    // Store points
                    Point p = new Point(Integer.parseInt(nextLine.split(",")[0]), Integer.parseInt(nextLine.split(",")[1]));
                    points.add(p);
                } else{
                    // Store folds
                    String[] fold = nextLine.split("=");
                    String axis = fold[0].substring(fold[0].length() - 1); // axis is last character of split by =
                    int num = Integer.parseInt(fold[1]);
                    folds.add(new Fold(axis, num));
                }
                
            }
            s.close();
            Data data = new Data(points, folds);
            return data;
        } catch(FileNotFoundException e){
            throw new RuntimeException(e);
        }
    }
}

enum FoldType{
    UP,
    LEFT
}

class Fold{
    FoldType type;
    int coord;
    public Fold(String axis, int coord){
        this.type = axis.equals("x") ? FoldType.LEFT : FoldType.UP;
        this.coord = coord;
    }
}

class Data{
    ArrayList<Point> points;
    ArrayList<Fold> folds;
    public Data(ArrayList<Point> points, ArrayList<Fold> folds){
        this.points = points;
        this.folds = folds;
    }
}