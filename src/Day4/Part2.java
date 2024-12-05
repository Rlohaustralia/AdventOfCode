package Day4;

import java.io.*;
import java.util.*;

public class Part2 {

    public static void main(String[] args) throws IOException {
        // Read a data file
        BufferedReader br = new BufferedReader(new FileReader("src/Day4/input.txt"));
        String line;

        // Create a list to save file
        List<String> grid = new ArrayList<>();

        // Add lines to the grid list by reading line by line
        while((line = br.readLine()) != null) {
            grid.add(line);
        }
        br.close();

        // Create a 2D array
        char[][] matrix = new char[grid.size()][];
        for (int i = 0; i < grid.size(); i++) {
            matrix[i] = grid.get(i).toCharArray();
        }

        // Count `X-MAS`
        int totalMAS = countMAS(matrix);

        // Print the result
        System.out.println("MAS: " + totalMAS);
    }

    static int countMAS(char[][] matrix) {
        int count = 0;

        // Process
        for (int row = 1; row < matrix.length-1; row++) {
            for (int col = 1; col < matrix[0].length-1; col++) {
                if (hasMAS(matrix, row, col)) {
                    count++;
                }
            }
        }
        return count;
    }

    private static boolean hasMAS(char[][] matrix, int row, int col) {

        if(matrix[row][col] != 'A') {
            return false;
        }

        String diagonal1 = "" + matrix[row-1][col-1] + matrix[row+1][col+1];
        String diagonal2 = "" + matrix[row-1][col+1] + matrix[row+1][col-1];
        return (diagonal1.equals("MS") || diagonal1.equals("SM")) && (diagonal2.equals("MS") || diagonal2.equals("SM"));

    }
}
