package Day16;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Part2 {

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
        int startX = 0, startY = 0, endX = 0, endY = 0;

        // Search for start and end points
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < grid[i].length(); j++) {
                if (grid[i].charAt(j) == 'S') {
                    startX = i;
                    startY = j;
                } else if (grid[i].charAt(j) == 'E') {
                    endX = i;
                    endY = j;
                }
            }
        }

        // Priority queue for Dijkstra's algorithm
        PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        queue.add(new int[]{0, 0, startX, startY, 0, startX, startY}); // cost, direction, currentX, currentY, prevDir, prevX, prevY
        Map<String, Integer> costMap = new HashMap<>();
        Map<String, String> backtracking = new HashMap<>();
        String endState = "";

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int cost = current[0];
            int direction = current[1];
            int x = current[2];
            int y = current[3];
            int prevDir = current[4];
            int prevX = current[5];
            int prevY = current[6];

            String state = direction + "," + x + "," + y;
            if (costMap.containsKey(state)) {
                if (cost == costMap.get(state)) {
                    backtracking.put(state, prevDir + "," + prevX + "," + prevY);
                }
                continue;
            }

            String newState = "";
            for (int newDirection = 0; newDirection < 4; newDirection++) {
                newState = Arrays.toString(directions[newDirection]) + "," + x + "," + y;
                if (costMap.containsKey(newState)) {
                    break;
                }
                backtracking.put(state, prevDir + "," + prevX + "," + prevY);
            }

            costMap.put(state, cost);

            if (grid[x].charAt(y) == '#') {
                continue;
            }

            if (grid[x].charAt(y) == 'E') {
                System.out.println("Shortest Cost: " + cost);
                endState = state;
                break;
            }


            // Move forward in the same direction
            int newX = x + directions[direction][0];
            int newY = y + directions[direction][1];
            if (isValid(newX, newY, n, grid)) {
                queue.add(new int[]{cost + 1, direction, newX, newY, direction, x, y});
            }

            // Turn right
            queue.add(new int[]{cost + 1000, (direction + 1) % 4, x, y, direction, x, y});
            // Turn left
            queue.add(new int[]{cost + 1000, (direction + 3) % 4, x, y, direction, x, y});




        }

        // Trace back the path
        Set<String> seen = new HashSet<>();
        Set<String> seenPositions = new HashSet<>();
        Stack<String> stack = new Stack<>();
        stack.push(endState);

        while (!stack.isEmpty()) {
            String top = stack.pop();
            if (seen.contains(top)) {
                continue;
            }
            seen.add(top);
            seenPositions.add(top.substring(1));

            // Get the previous state for the current position (top)
            String prevState = backtracking.get(top);

            if (prevState != null) {
                stack.push(prevState);
            }
        }
        System.out.println("Answer: " + seenPositions.size());







        // Debugging: Visualize the final path on the grid
        char[][] resultGrid = new char[n][];
        for (int i = 0; i < n; i++) {
            resultGrid[i] = grid[i].toCharArray();
        }

        // Mark the path
        for (String position : seenPositions) {
            String[] parts = position.split(",");
            int x = Integer.parseInt(parts[1]);
            int y = Integer.parseInt(parts[2]);
            resultGrid[x][y] = 'O'; // Mark the path with 'O'
        }

        System.out.println("Final Result Grid:");
        for (char[] row : resultGrid) {
            System.out.println(new String(row));
        }
    }

    private static boolean isValid(int i, int j, int n, String[] grid) {
        return i >= 0 && i < n && j >= 0 && j < grid[i].length() && grid[i].charAt(j) != '#';
    }
}
