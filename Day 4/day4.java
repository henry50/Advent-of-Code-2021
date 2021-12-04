import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;

public class day4{
    public static void main(String[] args){
        System.out.println(part1());
        System.out.println(part2());
    }
    public static int part1(){
        Data data = new Data("input.txt");
        for(int i = 0; i < data.numbers.length; i++){
            // For each number
            int thisNum = data.numbers[i];
            for(int b = 0; b < data.boards.size(); b++){
                // For each board get the board and picked arrays
                Board thisBoard = data.boards.get(b);
                int[][] board = thisBoard.board;
                boolean[][] picked = thisBoard.picked;
                // Iterate each row/col and add if picked
                for(int row = 0; row < board.length; row++){
                    for(int col = 0; col < board.length; col++){
                        if(board[row][col] == thisNum){
                            picked[row][col] = true;
                        }
                    }
                }
                boolean winningBoard = false;
                for(int row = 0; row < picked.length; row++){
                    boolean rowTrue = true;
                    boolean colTrue = true;
                    for(int col = 0; col < picked.length; col++){
                        if(!picked[row][col]){
                            rowTrue = false;
                        }
                        if(!picked[col][row]){
                            colTrue = false;
                        }
                    }
                    if(rowTrue || colTrue){
                        winningBoard = true;
                        break;
                    }
                }
                int unmarked = 0;
                if(winningBoard){
                    for(int row = 0; row < board.length; row++){
                        for(int col = 0; col < board.length; col++){
                            if(!picked[row][col]){
                                unmarked += board[row][col];
                            }
                        }
                    }
                    return thisNum * unmarked;
                }
            }
        }
        return 1;
    }
    public static int part2(){
        Data data = new Data("input.txt");
        Board lastWinner = new Board();
        for(int i = 0; i < data.numbers.length; i++){
            // For each number
            int thisNum = data.numbers[i];
            ArrayList<Board> removeBoards = new ArrayList<Board>();
            for(int b = 0; b < data.boards.size(); b++){
                // For each board get the board and picked arrays
                Board thisBoard = data.boards.get(b);
                int[][] board = thisBoard.board;
                boolean[][] picked = thisBoard.picked;
                // Iterate each row/col and add if picked
                for(int row = 0; row < board.length; row++){
                    for(int col = 0; col < board.length; col++){
                        if(board[row][col] == thisNum){
                            picked[row][col] = true;
                        }
                    }
                }
                boolean winningBoard = false;
                for(int row = 0; row < picked.length; row++){
                    boolean rowTrue = true;
                    boolean colTrue = true;
                    for(int col = 0; col < picked.length; col++){
                        if(!picked[row][col]){
                            rowTrue = false;
                        }
                        if(!picked[col][row]){
                            colTrue = false;
                        }
                    }
                    if(rowTrue || colTrue){
                        winningBoard = true;
                        break;
                    }
                }
                if(winningBoard){
                    thisBoard.winningNum = thisNum;
                    removeBoards.add(thisBoard);
                    lastWinner = thisBoard;
                }
            }
            data.boards.removeAll(removeBoards);
        }
        int unmarked = 0;
        for(int row = 0; row < lastWinner.board.length; row++){
            for(int col = 0; col < lastWinner.board.length; col++){
                if(!lastWinner.picked[row][col]){
                    unmarked += lastWinner.board[row][col];
                }
            }
        }
        return lastWinner.winningNum * unmarked;
    }
}
class Data{
    ArrayList<Board> boards;
    int[] numbers;
    public Data(String file){
        boards = new ArrayList<Board>();
        openFile(file);
    }
    public void openFile(String path){
        try{
            Scanner s = new Scanner(new File(path));
            boolean first = true;
            int[][] currentBoard = new int[5][5];
            int boardLine = 0;
            while(s.hasNext()){
                String line = s.nextLine();
                if(first){
                    numbers = numSplit(line, ",");
                    s.nextLine(); // Ignore the blank line after the first line.
                    first = false;
                } else if(line.length() != 0){
                    // Split the line by whitespace after removing leading whitespace.
                    currentBoard[boardLine] = numSplit(line.replaceFirst("^\\s*", ""), "\\s+");
                    boardLine++;
                } else{
                    boards.add(new Board(currentBoard, boards.size()));
                    currentBoard = new int[5][5];
                    boardLine = 0;
                }
            }
            boards.add(new Board(currentBoard,boards.size())); // Add the final board
            s.close();
        } catch(FileNotFoundException e){
            throw new RuntimeException(e);
        }
    }
    public int[] numSplit(String s, String delimiter){
        String[] sp = s.split(delimiter);
        int[] ret = new int[sp.length];
        for(int i = 0; i < sp.length; i++){
            ret[i] = Integer.parseInt(sp[i]);
        }
        return ret;
    }
}
class Board{
    int[][] board;
    boolean[][] picked;
    int num;
    int winningNum;
    public Board(){
        // For a null board.
    }
    public Board(int[][] board, int num){
        this.board = board;
        this.num = num;
        int boardSize = board.length;
        picked = new boolean[boardSize][boardSize];
        for(int i = 0; i < boardSize; i++){
            for(int j = 0; j < boardSize; j++){
                picked[i][j] = false;
            }
        }
    }
    public String toString(){
        return String.valueOf(this.num);
    }
}