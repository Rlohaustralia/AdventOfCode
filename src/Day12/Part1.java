package Day12;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Part1 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("src/Day12/input.txt"));
        String line;

        List<String> lineList = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            lineList.add(line);
        }

        String[] grid = lineList.toArray(new String[0]);
        int rows = grid.length;
        int cols = grid[0].length();
        int totalPrice = 0;
        int[][] directions = {{-1,0}, {1,0}, {0,1}, {0,-1}};

        Set<String> visited = new HashSet<>();
        List<List<String>> plots = new ArrayList<>(); // Save the plots founded

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (!visited.contains(i + "," + j)) {
                    exploreGarden(i, j, rows, cols, grid, directions, visited, plots);
                }
            }
        }

        // Calculate the total price based on the areas and perimeters of the plots
        for (List<String> plot : plots) {
            String target = plot.get(0);
            int perimeter = calculatePerimeter(plot, directions, grid);
            int area = plot.size();
            totalPrice += area * perimeter;
        }

        System.out.println(totalPrice);
    }

    private static void exploreGarden(int i, int j, int rows, int cols, String[] grid, int[][] directions, Set<String> seen, List<List<String>> plots) {
        String target = grid[i].substring(j, j + 1);
        List<String> currentPlot = new ArrayList<>();
        List<int[]> stack = new ArrayList<>();
        stack.add(new int[]{i, j});

        while (!stack.isEmpty()) {
            int[] current = stack.remove(stack.size() - 1);
            int ci = current[0], cj = current[1];

            // If already seen or out of bounds, continue
            if (seen.contains(ci + "," + cj) || ci < 0 || ci >= rows || cj < 0 || cj >= cols || !grid[ci].substring(cj, cj + 1).equals(target)) {
                continue;
            }

            seen.add(ci + "," + cj);
            currentPlot.add(ci + "," + cj);

            // Explore neighbors
            for (int[] dir : directions) {
                int ni = ci + dir[0], nj = cj + dir[1];
                stack.add(new int[]{ni, nj});
            }
        }
        plots.add(currentPlot);
    }


    private static int calculatePerimeter(List<String> plot, int[][] directions, String[] grid) {
        int perimeter = 0;
        for (String plotCell : plot) {
            String[] coords = plotCell.split(",");
            int i = Integer.parseInt(coords[0]);
            int j = Integer.parseInt(coords[1]);

            for (int[] dir : directions) {
                int ni = i + dir[0], nj = j + dir[1];
                if (ni < 0 || ni >= grid.length || nj < 0 || nj >= grid[0].length() || !grid[ni].substring(nj, nj + 1).equals(grid[i].substring(j, j + 1))) {
                    perimeter++;
                }
            }
        }
        return perimeter;
    }
}
