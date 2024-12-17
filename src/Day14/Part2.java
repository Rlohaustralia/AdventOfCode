package Day14;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Part2 {

    static List<int[]> positions = new ArrayList<>();
    static List<int[]> velocities = new ArrayList<>();
    static int width = 103;
    static int height = 101;


    public static void main(String[] args) throws IOException {

        // Reading the file and parsing the data
        BufferedReader br = new BufferedReader(new FileReader("src/Day14/input.txt"));
        String line;

        while ((line = br.readLine()) != null) {
            String[] parts = line.split(" ");

            // Parse position
            String[] positionParts = parts[0].split("=")[1].split(",");
            int[] position = new int[positionParts.length];
            for (int i = 0; i < positionParts.length; i++) {
                position[i] = Integer.parseInt(positionParts[i]);
            }

            // Parse velocity
            String[] velocityParts = parts[1].split("=")[1].split(",");
            int[] velocity = new int[velocityParts.length];
            for (int i = 0; i < velocityParts.length; i++) {
                velocity[i] = Integer.parseInt(velocityParts[i]);
            }
            positions.add(new int[]{position[1], position[0]});
            velocities.add(new int[]{velocity[1], velocity[0]});
        }




        // Part 2

        int step = 0;
        Map<String, Integer> visited = new HashMap<>();

        while (true) {

            // Initialize the canvas
            String[][] canvas = new String[width][height];
            for (int i = 0; i < width; i++) {
                for (int j = 0 ; j < height; j++) {
                    canvas[i][j] = " ";
                }
            }

            // Locate the robots to the current positions
            for (int[] pos : positions) {
                canvas[pos[0]][pos[1]] = "*";
            }

            // Convert picture to a string format
            StringBuilder canvasStr = new StringBuilder();
            for (int i = 0; i < width; i++) {
                canvasStr.append(String.join("", canvas[i])).append("\n");
            }

            // Check if we have seen this picture before
            if (visited.containsKey(canvasStr.toString())) {
                System.out.println("Step: "+ visited.get(canvasStr.toString()));
                break;
            }

            // Store the picture and the step at which it was first seen
            visited.put(canvasStr.toString(), step);

            // Print the picture
            System.out.println(canvasStr);
            System.out.println("\n");

            int size = positions.size();
            updatePositions(size);

            step++;
        }
    }

    private static void updatePositions(int size) {
        for (int i = 0; i < size; i++) {
            positions.get(i)[0] = (positions.get(i)[0] + velocities.get(i)[0] + width) % width;
            positions.get(i)[1] = (positions.get(i)[1] + velocities.get(i)[1] + height) % height;
        }
    }
}