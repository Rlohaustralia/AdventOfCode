package Day4;

import java.io.*;
import java.util.*;

public class Part1 {

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

        // Count `XMAS`
        int totalXMAS = countXMAS(matrix);

        // Print the result
        System.out.println("XMAS: " + totalXMAS);
    }

    static int countXMAS(char[][] matrix) {
        int count = 0;
        // Total eight directions that we should check
        int[][] directions = {
                {0,1},{0,-1},{1,0},{-1,0},{1,1},{1,-1},{-1,1},{-1,-1}
        };

        // Process
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {
                for (int[] direction : directions) {
                    if (searchXMAS(matrix, row, col, direction)) {
                        count++;
                    }
                }
            }

        }
        return count;
    }

    private static boolean searchXMAS(char[][] matrix, int row, int col, int[] direction) {
        String target = "XMAS";
        int lenRow = matrix.length;
        int lenCol = matrix[0].length;

        for (int i = 0; i < target.length(); i++){
            int newRow = row + i * direction[0]; // Calculate a new location of row
            int newCol = col + i * direction[1]; // Calculate a new location of col

            // Check if the new position is out of the grid boundaries
            if (newRow < 0 || newRow >= lenRow || newCol < 0 || newCol >= lenCol) {
                return false;
            }

            if (matrix[newRow][newCol] != target.charAt(i)) {
                return false;
            }
        }
        return true;
    }
}
