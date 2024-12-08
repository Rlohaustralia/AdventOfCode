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

        // Create a grid from input file
        StringBuilder gridBuilder = new StringBuilder();
        while ((line = br.readLine()) != null) {
            gridBuilder.append(line).append("\n");
        }
        br.close();

        // Convert grid into 2D array
        String[] grid = gridBuilder.toString().split("\n");
        int rows = grid.length;
        int cols = grid[0].length();

        // Find initial position of the guard
        int guardX = -1, guardY = -1;
        boolean foundGuard = false;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r].charAt(c) == '^') {
                    guardX = r;
                    guardY = c;
                    foundGuard = true;
                    break;
                }
            }
            if (foundGuard) break;
        }

        // Directions (North, East, South, West)
        int[] moveRow = {-1, 0, 1, 0};  // Row movement for directions
        int[] moveCol = {0, 1, 0, -1};  // Column movement for directions

        // Track positions visited by the guard
        Set<String> visitedPositions = new HashSet<>();
        int direction = 0; // Start direction is North
        int currentX = guardX, currentY = guardY;

        // Simulate guard's initial movement
        while (true) {
            visitedPositions.add(currentX + "," + currentY);

            int nextX = currentX + moveRow[direction];
            int nextY = currentY + moveCol[direction];

            if (!(0 <= nextX && nextX < rows && 0 <= nextY && nextY < cols)) break;
            if (grid[nextX].charAt(nextY) == '#') {
                if (direction == 3) {
                    direction = 0;
                } else {
                    direction++;
                }
            } else {
                currentX = nextX;
                currentY = nextY;
            }
        }

        // Count positions that cause infinite loops
        int infiniteLoopCount = 0;
        for (String position : visitedPositions) {
            String[] coordinates = position.split(",");
            int obstacleX = Integer.parseInt(coordinates[0]);
            int obstacleY = Integer.parseInt(coordinates[1]);

            // Skip guard's starting position
            if (obstacleX == guardX && obstacleY == guardY) continue;

            // Check if placing an obstacle here causes a loop
            if (causesInfiniteLoop(grid, guardX, guardY, rows, cols, obstacleX, obstacleY)) {
                infiniteLoopCount++;
            }
        }

        System.out.println(infiniteLoopCount);
    }

    private static boolean causesInfiniteLoop(String[] grid, int startX, int startY, int rows, int cols, int obstacleX, int obstacleY) {
        // Temporarily place an obstacle
        char[] row = grid[obstacleX].toCharArray();
        if (row[obstacleY] == '#') return false; // Already an obstacle

        row[obstacleY] = '#'; // Place obstacle
        grid[obstacleX] = String.valueOf(row);

        int[] moveRow = {-1, 0, 1, 0};
        int[] moveCol = {0, 1, 0, -1};
        Set<String> stateHistory = new HashSet<>();
        int direction = 0;
        int currentX = startX, currentY = startY;

        // Simulate guard's movement with the new obstacle
        while (true) {
            String state = currentX + "," + currentY + "," + direction;
            if (stateHistory.contains(state)) {
                // Remove obstacle and return true (loop detected)
                row[obstacleY] = '.';
                grid[obstacleX] = String.valueOf(row);
                return true;
            }

            stateHistory.add(state);

            int nextX = currentX + moveRow[direction];
            int nextY = currentY + moveCol[direction];

            if (!(0 <= nextX && nextX < rows && 0 <= nextY && nextY < cols)) {
                // Remove obstacle and return false (no loop detected)
                row[obstacleY] = '.';
                grid[obstacleX] = String.valueOf(row);
                return false;
            }

            if (grid[nextX].charAt(nextY) == '#') {
                if (direction == 3) {
                    direction = 0;
                } else {
                    direction++;
                }
            } else {
                currentX = nextX;
                currentY = nextY;
            }
        }
    }
}
