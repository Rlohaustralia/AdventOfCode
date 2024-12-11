package Day10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Part1 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("src/Day10/input.txt"));
        String line;

        List<String> lineList = new ArrayList<>();
        while((line = br.readLine()) != null) {
            lineList.add(line);
            System.out.println(line);
        }

        String[] grid = lineList.toArray(new String[0]);
        int rows = grid.length;
        int cols = grid[0].length();
        int[][] directions = {{-1,0}, {1,0}, {0,1}, {0,-1}};
        int scoreSum = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if(grid[i].charAt(j) == '0') {
                    scoreSum += caculateTrailHeadScore(grid, i, j, rows, cols, directions);
                }
            }
        }

        System.out.println("Score Sum is: " + scoreSum);
    }

    private static int caculateTrailHeadScore(String[] grid, int startX, int startY, int rows, int cols, int[][] directions) {
        Queue<int[]> queue = new LinkedList<>();
        Set<String> seen = new HashSet<>();
        queue.add(new int[]{startX, startY, 0}); // {x, y, current height}
        seen.add(startX + "," + startY); // Mark starting position as seen
        int score = 0;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int x = current[0];
            int y = current[1];
            int h = current[2];

            if (h == 9) { // Reached height 9, add to score
                score++;
                continue;
            }

            // Explore all directions
            for (int[] dir : directions) {
                int newX = x + dir[0];
                int newY = y + dir[1];

                // If new x, y is in boundary,
                if (newX >= 0 && newX < rows && newY >= 0 && newY < cols) {
                    String nextPos = newX + "," + newY; // Represent position as a string
                    if (!seen.contains(nextPos)) {
                        int nextH = grid[newX].charAt(newY) - '0'; // Make it integer
                        if (nextH == h + 1) { // Check if height increases by 1
                            seen.add(nextPos);
                            queue.add(new int[]{newX, newY, nextH});
                        }
                    }
                }
            }
        }
        return score;
    }
}
