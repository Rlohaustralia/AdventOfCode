package Day18;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Part2 {

    static int gridSize = 71;

    public static void main(String[] args) throws IOException {

        // Reading the input file
        BufferedReader br = new BufferedReader(new FileReader("src/Day18/input.txt"));
        String line;

        // List to store the corrupted memory coordinates
        List<int[]> list = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            String[] str = line.split(",");
            int x = Integer.parseInt(str[0].trim());
            int y = Integer.parseInt(str[1].trim());
            list.add(new int[]{x, y});
        }

        // Initialize the grid with '.'
        char[][] grid = new char[gridSize][gridSize];
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                grid[i][j] = '.';
            }
        }

        // Simulate the addition of corrupted bytes
        for (int n = 0; n < list.size(); n++) {
            int[] pos = list.get(n);
            int x = pos[1];
            int y = pos[0];
            grid[x][y] = '#';

            int result = searchTheShortestWay(grid);
            if (result == -1) {
                // Print the coordinates of the first blocking byte
                System.out.println(y + "," + x);
                break;
            }
        }
    }

    private static int searchTheShortestWay(char[][] grid) {
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        boolean[][] seen = new boolean[gridSize][gridSize];

        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{0, 0, 0}); // x = 0, y = 0, steps = 0
        seen[0][0] = true;

        while (!q.isEmpty()) {
            int[] current = q.poll();
            int x = current[0];
            int y = current[1];
            int steps = current[2];

            if (x == gridSize - 1 && y == gridSize - 1) {
                return steps;
            }

            for (int[] dir : directions) {
                int newX = x + dir[0];
                int newY = y + dir[1];

                if (newX >= 0 && newX < gridSize && newY >= 0 && newY < gridSize
                        && grid[newX][newY] == '.' && !seen[newX][newY]) {
                    seen[newX][newY] = true;
                    q.add(new int[]{newX, newY, steps + 1});
                }
            }
        }
        return -1; // No path found
    }
}
