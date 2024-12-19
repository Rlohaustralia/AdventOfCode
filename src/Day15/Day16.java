package Day15;

import java.io.*;
import java.util.*;

public class Day16 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("src/Day16/input.txt"));
        List<String> gridLines = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            gridLines.add(line.trim());
        }
        br.close();

        String[] grid = gridLines.toArray(new String[0]);
        int n = grid.length;

        int[] start = new int[2];
        int[] end = new int[2];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < grid[i].length(); j++) {
                if (grid[i].charAt(j) == 'S') {
                    start[0] = i;
                    start[1] = j;
                } else if (grid[i].charAt(j) == 'E') {
                    end[0] = i;
                    end[1] = j;
                }
            }
        }

        int[][] dd = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}}; // Direction vectors
        PriorityQueue<Node> pq = new PriorityQueue<>();
        Set<String> seen = new HashSet<>();

        pq.add(new Node(0, 0, start[0], start[1]));

        while (!pq.isEmpty()) {
            Node top = pq.poll();
            int cost = top.cost;
            int d = top.direction;
            int i = top.i;
            int j = top.j;

            String state = d + "," + i + "," + j;
            if (seen.contains(state)) {
                continue;
            }
            seen.add(state);

            if (grid[i].charAt(j) == '#') {
                continue;
            }

            if (grid[i].charAt(j) == 'E') {
                System.out.println(cost);
                break;
            }

            // Move forward
            int ii = i + dd[d][0];
            int jj = j + dd[d][1];
            if (isValid(ii, jj, n, grid)) {
                pq.add(new Node(cost + 1, d, ii, jj));
            }

            // Turn right
            pq.add(new Node(cost + 1000, (d + 1) % 4, i, j));

            // Turn left
            pq.add(new Node(cost + 1000, (d + 3) % 4, i, j));
        }
    }

    private static boolean isValid(int i, int j, int n, String[] grid) {
        return i >= 0 && i < n && j >= 0 && j < grid[i].length() && grid[i].charAt(j) != '#';
    }

    static class Node implements Comparable<Node> {
        int cost;
        int direction;
        int i;
        int j;

        Node(int cost, int direction, int i, int j) {
            this.cost = cost;
            this.direction = direction;
            this.i = i;
            this.j = j;
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.cost, other.cost);
        }
    }
}
