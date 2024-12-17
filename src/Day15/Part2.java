package Day15;

import java.io.*;
import java.util.*;

public class Part2 {

    static int n;
    static char[][] grid;
    static int currentI, currentJ; // Current player position
    static List<int[]> boxes = new ArrayList<>(); // Save the locations of boxes
    static Set<String> walls = new HashSet<>(); // Save the locations of walls
    static Map<Character, int[]> directions = new HashMap<>();
    static {
        directions.put('<', new int[]{0, -1});
        directions.put('v', new int[]{1, 0});
        directions.put('>', new int[]{0, 1});
        directions.put('^', new int[]{-1, 0});
    }



    public static void main(String[] args) throws IOException {

        // Input handling
        BufferedReader br = new BufferedReader(new FileReader("src/Day15/input.txt"));

        // Variables to store the grid and movement sequence
        List<String> gridLines = new ArrayList<>();
        String movement = "";

        // Flag to track which part we are reading (before or after the blank line)
        boolean isGridPart = true;

        String line;
        while ((line = br.readLine()) != null) {

            // If it's a blank line, switch to the second part
            if (line.isEmpty()) {
                isGridPart = false;
                continue;
            }

            // Add lines to the appropriate part
            if (isGridPart) {
                gridLines.add(line);
            } else {
                movement += line;
            }
        }
        br.close();

        // Convert gridLines into a 2D char array
        grid = gridLines.stream().map(String::toCharArray).toArray(char[][]::new);
        n = grid.length;

        // Initialize grid locations
        initializeGrid();

        // Process each step
        for (char step : movement.toCharArray()) {
            move(directions.get(step));
        }

        // Calculate the final result
        int ans = 0;
        for (int[] box : boxes) {
            ans += box[0] * 100 + box[1];
        }
        System.out.println(ans);
    }




    private static void initializeGrid() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '@') {
                    currentI = i;
                    currentJ = j * 2; // Finds the player and store the current position
                } else if (grid[i][j] == 'O') {
                    boxes.add(new int[]{i, j * 2}); // If it finds a box, it adds the position to the boxes list.
                } else if (grid[i][j] == '#') {
                    walls.add(i + "," + (j * 2)); // If it finds a wall, it adds the positions to the walls set
                    walls.add(i + "," + (j * 2 + 1));
                }
            }
        }
    }

    private static boolean isInBoundary(int i, int j) {
        return i >= 0 && i < n && j >= 0 && j < 2 * n;
    }

    private static void move(int[] direction) {
        // Calculate new positions
        int newi = currentI + direction[0];
        int newj = currentJ + direction[1];

        // checks if the new position is inside the grid and if it's not blocked by a wall
        if (!isInBoundary(newi, newj)) return;
        if (walls.contains(newi + "," + newj)) return;

        Stack<int[]> stack = new Stack<>(); // Stack to keep track of the boxes that need to be moved
        Set<String> seen = new HashSet<>(); // Set to keep track of visited positions to avoid loops
        boolean canMove = true;

        // checks if there are boxes at the new position or the adjacent position
        if (containsBox(newi, newj)) {
            stack.push(new int[]{newi, newj});
        }

        if (containsBox(newi, newj - 1)) {
            stack.push(new int[]{newi, newj - 1});
        }

        while (!stack.isEmpty()) {
            int[] top = stack.pop();
            int ni = top[0] + direction[0]; // Calculate the new row position for the box
            int nj = top[1] + direction[1]; // Calculate the new column position for the box

            if (!isInBoundary(ni, nj) || walls.contains(ni + "," + nj) || walls.contains(ni + "," + (nj + 1))) {
                canMove = false;
                break;
            }

            String key = top[0] + "," + top[1];
            if (seen.contains(key)) continue;
            seen.add(key);

            // If there are more boxes at the new position, add them to the stack for further checking
            if (containsBox(ni, nj)) stack.push(new int[]{ni, nj});
            if (containsBox(ni, nj - 1)) stack.push(new int[]{ni, nj - 1});
            if (containsBox(ni, nj + 1)) stack.push(new int[]{ni, nj + 1});
        }

        if (!canMove) return;

        // Move boxes
        for (int[] box : boxes) {
            if (seen.contains(box[0] + "," + box[1])) {
                box[0] += direction[0];
                box[1] += direction[1];
            }
        }

        // Update player position
        currentI += direction[0];
        currentJ += direction[1];
    }

    private static boolean containsBox(int i, int j) {
        for (int[] box : boxes) {
            if (box[0] == i && box[1] == j) return true;
        }
        return false;
    }
}
