package Day20;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Part1 {
    static int N; // Grid size (N x N)
    static char[][] grid; // Grid of characters representing the map
    static int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}}; // Directions (Right, Down, Left, Up)

    public static void main(String[] args) throws IOException {

        // Reading the grid from a file
        BufferedReader br = new BufferedReader(new FileReader("src/Day20/input.txt"));
        String line;

        List<String> list = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            list.add(line); // Store each line of the grid
        }

        // Initializing the grid based on the input lines
        grid = new char[list.size()][list.get(0).length()];
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(0).length(); j++) {
                grid[i][j] = list.get(i).charAt(j); // Fill the grid with characters
            }
        }

        // Print the grid (for debugging purposes)
        for (char[] row : grid) {
            System.out.println(row);
        }

        N = grid.length; // Set the size of the grid

        // Initialize the starting (S) and ending (E) coordinates
        int si = 0, sj = 0, ei = 0, ej = 0;

        // Loop through the grid to find the start ('S') and end ('E') coordinates
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 'S') {
                    si = i; // Starting row
                    sj = j; // Starting column
                } else if (grid[i][j] == 'E') {
                    ei = i; // Ending row
                    ej = j; // Ending column
                }
            }
        }

        // Pathfinding logic to find the path from 'S' to 'E'
        List<int[]> path = new ArrayList<>();
        path.add(new int[]{si, sj}); // Add starting point to the path
        while (true) {
            int[] last = path.get(path.size() - 1);
            int i = last[0], j = last[1];
            if (i == ei && j == ej) break; // If we reach the end, stop

            // Try moving in the four possible directions (right, down, left, up)
            for (int[] dir : directions) {
                int ii = i + dir[0], jj = j + dir[1];
                if (!isValid(ii, jj)) continue; // Skip if the new position is outside the grid
                if (grid[ii][jj] == '#') continue; // Skip blocked cells

                if (path.size() > 1) {
                    int[] secondLast = path.get(path.size() - 2);
                    if (ii == secondLast[0] && jj == secondLast[1]) continue; // Avoid backtracking (한 번 이미 지나온 경로로 되돌아 가려는 시도)
                }

                path.add(new int[]{ii, jj}); // Add the new valid position to the path
                break; // Stop after adding one valid move (non-backtracking)
            }
        }

        int og = path.size(); // Original path length

        // Map to store the time each cell is reached during the original pathfinding
        Map<String, Integer> times = new HashMap<>();
        for (int t = 0; t < path.size(); t++) {
            int[] coord = path.get(t);
            times.put(Arrays.toString(coord), og - t); // Store the time remaining to reach the end
        }

        // Map to store the time saved by each possible move
        Map<String, Integer> saved = new HashMap<>();
        for (int t = 0; t < path.size(); t++) {
            int[] coord = path.get(t);
            int i = coord[0], j = coord[1];
            // Try to move in two directions at once (double direction step)
            for (int[] dir1 : directions) {
                for (int[] dir2 : directions) {
                    int ii = i + dir1[0] + dir2[0];
                    int jj = j + dir1[1] + dir2[1];
                    if (!isValid(ii, jj) || grid[ii][jj] == '#') continue; // Skip invalid or blocked cells

                    String key = Arrays.toString(new int[]{ii, jj});
                    int remT = times.getOrDefault(key, Integer.MAX_VALUE); // Get the time remaining for this cell
                    saved.put(Arrays.toString(new int[]{i, j, ii, jj}), og - (t + remT + 2)); // Calculate time saved
                }
            }
        }

        // Count the number of times saved is greater than or equal to 100
        int ans = 0;
        Map<Integer, Integer> counts = new HashMap<>();
        for (int v : saved.values()) {
            if (v >= 0) counts.put(v, counts.getOrDefault(v, 0) + 1); // Count the number of times saved
            if (v >= 100) ans++; // Increment answer if the saved time is greater than or equal to 100
        }

        // Print the result (number of cheats where saved time is >= 100)
        System.out.println(ans);
    }

    // Helper method to check if the cell (i, j) is within the grid bounds
    private static boolean isValid(int i, int j) {
        return i >= 0 && i < N && j >= 0 && j < N;
    }
}
