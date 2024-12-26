package Day16;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Part1 {

    static int n;

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader("src/Day16/input.txt"));
        String line;

        // Parse data
        List<String> lines = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            lines.add(line.trim());
        }

        // Create a 2D Grid
        String[] grid = lines.toArray(new String[0]);

        n = grid.length;
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}}; // Right, Down, Left, Up
        int currentX = 0;
        int currentY = 0;
        int targetX = 0;
        int targetY = 0;

        // Search for current location and target location
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < grid[i].length(); j++) {
                if (grid[i].charAt(j) == 'S') {
                    currentX = i;
                    currentY = j;
                    break;
                }
                if (grid[i].charAt(j) == 'E') {
                    targetX = i;
                    targetY = j;
                    break;
                }
            }
        }


        Set<String> visited = new HashSet<>();
        List<int[]> heap = new ArrayList<>();
        heap.add(new int[]{0, 0, currentX, currentY}); // {cost, direction, row, col}

        while (!heap.isEmpty()) {
            int[] currentNode = calculateMinValue(heap);
            int score = currentNode[0];
            int direction = currentNode[1];
            int i = currentNode[2];
            int j = currentNode[3];

            String state = direction + "," + i + "," + j;
            if (visited.contains(state)) {
                continue;
            }
            visited.add(state);

            if (grid[i].charAt(j) == '#') {
                continue;
            }

            if (grid[i].charAt(j) == 'E') {
                System.out.println(score);
                break;
            }

            // add the cases for moving forward, turning right and left into the priority queue (heap)
            // Case: Move forward
            int nextI = i + directions[direction][0];
            int nextJ = j + directions[direction][1];
            if (isValid(nextI, nextJ, n, grid)) {
                heap.add(new int[] {score + 1, direction, nextI, nextJ});
            }
            // Case: Turn right
            heap.add(new int[]{score + 1000, (direction + 1) % 4, i, j});

            // Case: Turn left
            heap.add(new int[]{score + 1000, (direction + 3) % 4, i, j});
        }
    }



    private static int[] calculateMinValue(List<int[]> heap) {
        // Priority queue
        int minIndex = 0;
        for (int i = 1; i < heap.size(); i++) {
            if (heap.get(i)[0] < heap.get(minIndex)[0]) {
                minIndex = i;
            }
        }
        return heap.remove(minIndex);
    }



    private static boolean isValid(int i, int j, int n, String[] grid) {
        return i >= 0 && i < n && j >= 0 && j < grid[i].length() && grid[i].charAt(j) != '#';
    }
}