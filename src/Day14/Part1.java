package Day14;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Part1 {

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

        int seconds = 100;
        int size = positions.size();
        for (int i = 0; i < seconds; i++) {
            updatePositions(size);
        }

        // Calculate robot counts for each quadrant
        int numOfRobotsInQuadrant1 = countRobots(0, width/2, 0, height / 2);
        int numOfRobotsInQuadrant2 = countRobots(width/2 +1, width, 0, height/2);
        int numOfRobotsInQuadrant3 = countRobots(0, width/2, height/2 +1, height);
        int numOfRobotsInQuadrant4 = countRobots(width/2 +1, width, height/2 +1, height);

        //Calculate the product of robots in the quadrants
        int sum = numOfRobotsInQuadrant1 * numOfRobotsInQuadrant2 * numOfRobotsInQuadrant3 * numOfRobotsInQuadrant4;
        System.out.println("Sum: " + sum);
    }

    private static void updatePositions(int size) {
        for (int i = 0; i < size; i++) {
            positions.get(i)[0] = (positions.get(i)[0] + velocities.get(i)[0] + width) % width;
            positions.get(i)[1] = (positions.get(i)[1] + velocities.get(i)[1] + height) % height;
        }
    }


    public static int countRobots(int x0, int x1, int y0, int y1) {
        int count = 0;
        for (int x = x0; x < x1; x++) {
            for (int y = y0; y < y1; y++) {
                for (int[] position : positions) {
                    if (x == position[0] && y == position[1]) {
                        count++;
                    }
                }
            }
        }
        return count;
    }
}