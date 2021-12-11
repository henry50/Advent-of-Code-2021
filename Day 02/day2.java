import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class day2{
    public static void main(String[] args) throws FileNotFoundException{
        System.out.println(part1());
        System.out.println(part2());
    }
    public static int part1() throws FileNotFoundException{
        int horiz = 0;
        int depth = 0;
        String instruction;
        int num;
        Scanner s = new Scanner(new File("input.txt"));
        while(s.hasNextLine()){
            // Split the line into instruction and number
            String[] line = s.nextLine().split(" ");
            instruction = line[0];
            num = Integer.parseInt(line[1]);
            // Switch based on the instruction
            switch(instruction){
                case "forward":
                    horiz += num;
                    break;
                case "up":
                    depth -= num;
                    break;
                case "down":
                    depth += num;
                    break;
            }
        }
        s.close();
        // Return the product
        return horiz * depth;
    }
    public static int part2() throws FileNotFoundException{
        // Very similar to part 1, but with the aim variable.
        int horiz = 0;
        int depth = 0;
        int aim = 0;
        String instruction;
        int num;
        Scanner s = new Scanner(new File("input.txt"));
        while(s.hasNextLine()){
            // Split the line into instruction and number
            String[] line = s.nextLine().split(" ");
            instruction = line[0];
            num = Integer.parseInt(line[1]);
            // Switch based on the instruction
            switch(instruction){
                case "forward":
                    horiz += num;
                    depth += (aim * num);
                    break;
                case "up":
                    aim -= num;
                    break;
                case "down":
                    aim += num;
                    break;
            }
        }
        s.close();
        // Return the product
        return horiz * depth;
    }
}