package Day6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Part2 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("src/Day6/input.txt"));
        String line;

        // Create a list to save the grid
        StringBuilder gridBuilder = new StringBuilder();
        while ((line = br.readLine()) != null) {
            gridBuilder.append(line).append("\n");
        }
        br.close();

        // Convert the grid into a 2D array (n x m matrix)
        String[] grid = gridBuilder.toString().split("\n");
        int n = grid.length;
        int m = grid[0].length();

        // Find the starting position of the guard
        int i = -1, j = -1;
        boolean found = false;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < m; col++) {
                if (grid[row].charAt(col) == '^') {
                    i = row;
                    j = col;
                    found = true;
                    break;
                }
            }
            if (found) break;
        }

        // Directions (Up, Right, Down, Left)
        int[] di = {-1, 0, 1, 0};  // Row directions for Up, Right, Down, Left
        int[] dj = {0, 1, 0, -1};  // Column directions for Up, Right, Down, Left
        int dir = 0; // Starting direction is Up

        // Set to track distinct visited positions
        Set<String> seen = new HashSet<>();

        // Simulate the guard's movement
        while (true) {
            // Mark the current position as visited
            seen.add(i + "," + j);

            // Calculate next position
            int next_i = i + di[dir];
            int next_j = j + dj[dir];

            // Check if the next position is out of bounds
            if (!(0 <= next_i && next_i < n && 0 <= next_j && next_j < m)) {
                break;
            }

            // Check if the next position is an obstacle
            if (grid[next_i].charAt(next_j) == '#') {
                // Change direction: turn right (clockwise)
                if (dir == 3) {
                    dir = 0; // Turn from Left (3) back to Up (0)
                } else {
                    dir++; // Move to the next direction (Up -> Right -> Down -> Left)
                }
            } else {
                // Move to the next position if no obstacle
                i = next_i;
                j = next_j;
            }
        }

        // Output the number of distinct positions visited
        System.out.println(seen.size());
    }
}