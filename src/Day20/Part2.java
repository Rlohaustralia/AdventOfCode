package Day20;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Part2 {
    static int N; // Grid size (N x N)
    static char[][] grid; // Grid of characters representing the map
    static int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}}; // Directions (Right, Down, Left, Up)
    static int MAX_LEN = 20; // Maximum cheat length in picoseconds

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

        int og = path.size() - 1; // Original path length

        // Map to store the times
        Map<String, Integer> times = new HashMap<>();
        for (int t = 0; t < path.size(); t++) {
            int[] coord = path.get(t);
            times.put(Arrays.toString(coord), og - t);
        }

        // Calculate cheats and savings
        Map<String, Integer> saved = new HashMap<>();
        for (int t = 0; t < path.size(); t++) {
            int[] coord = path.get(t);
            int i = coord[0], j = coord[1];

            for (int ii = i - MAX_LEN; ii <= i + MAX_LEN; ii++) {
                for (int jj = j - MAX_LEN; jj <= j + MAX_LEN; jj++) {
                    int timeUsed = Math.abs(ii - i) + Math.abs(jj - j);
                    if (!isValid(ii, jj) || timeUsed > MAX_LEN || grid[ii][jj] == '#') continue;

                    String key = Arrays.toString(new int[]{ii, jj});
                    int remT = times.getOrDefault(key, Integer.MAX_VALUE);
                    saved.put(Arrays.toString(new int[]{i, j, ii, jj}), og - (t + remT + timeUsed));
                }
            }
        }

        // Count cheats with savings >= 100
        int ans = 0;
        for (int value : saved.values()) {
            if (value >= 100) ans++;
        }

        // Print the result
        System.out.println(ans);
    }

    // Helper method to check if the cell (i, j) is within the grid bounds
    private static boolean isValid(int i, int j) {
        return i >= 0 && i < N && j >= 0 && j < grid[i].length;
    }
}
