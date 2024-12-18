package Day14;
import java.io.*;
import java.util.*;

public class Part2 {
    static int rows = 103;
    static int cols = 101;

    static List<int[]> positions = new ArrayList<>();
    static List<int[]> velocities = new ArrayList<>();
    static Map<String, Integer> visited = new HashMap<>();
    static int maxStep = 6871; // ANSWER

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/Day14/input.txt"));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] parts = line.strip().split(" ");
            int[] position = Arrays.stream(parts[0].split("=")[1].split(","))
                    .mapToInt(Integer::parseInt).toArray();
            int[] velocity = Arrays.stream(parts[1].split("=")[1].split(","))
                    .mapToInt(Integer::parseInt).toArray();


            positions.add(new int[]{position[1], position[0]});
            velocities.add(new int[]{velocity[1], velocity[0]});
        }

        reader.close();

        int step = 0;
        while (step < maxStep) {
            System.out.println("Step " + step);
            StringBuilder picture = new StringBuilder();

            char[][] grid = new char[rows][cols];
            for (char[] row : grid) {
                Arrays.fill(row, ' ');
            }

            for (int[] pos : positions) {
                grid[pos[0]][pos[1]] = '*';
            }

            for (char[] row : grid) {
                picture.append(new String(row)).append("\n");
            }

            String pictureString = picture.toString();
            if (visited.containsKey(pictureString)) {
                break;
            }

            visited.put(pictureString, step);
            System.out.println(pictureString);

            update();

            step++;
        }
    }

    static void update() {
        for (int i = 0; i < positions.size(); i++) {
            positions.get(i)[0] = (positions.get(i)[0] + velocities.get(i)[0] + rows) % rows;
            positions.get(i)[1] = (positions.get(i)[1] + velocities.get(i)[1] + cols) % cols;
        }
    }
}
