package Day15;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Part1 {
    public static void main(String[] args) throws IOException {

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
        System.out.println(movement);

        // Convert grid lines to a 2D char array
        int rows = gridLines.size();
        int cols = gridLines.get(0).length();
        char[][] grid = new char[rows][cols];

        for (int i = 0; i < rows; i++) {
            grid[i] = gridLines.get(i).toCharArray();
        }

        // Searching for the robot's current position
        int currentX = 0;
        int currentY = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '@') {
                    currentX = i;
                    currentY = j;
                }
            }
        }

        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // ^ for up, v for down, < for left, > for right
        char[] moveSymbols = {'^', 'v', '<', '>'};

        // Debugging
        System.out.println("Initial Grid:");
        for (char[] row : grid) {
            System.out.println(new String(row));
        }
        System.out.println("");


        for (int i = 0; i < movement.length(); i++) {

            char move = movement.charAt(i);

            // Determine direction
            int dirIdx = -1;
            for (int d = 0; d < moveSymbols.length; d++) {
                if (moveSymbols[d] == move) {
                    dirIdx = d;
                    break;
                }
            }

            // Calculate the next position
            int nextX = currentX + directions[dirIdx][0];
            int nextY = currentY + directions[dirIdx][1];

            // Check boundaries and obstacles
            if (nextX >= 0 && nextX < rows && nextY >= 0 && nextY < cols) {
                if (grid[nextX][nextY] == '#') {

                    // Debugging
                    System.out.println(i + "th Grid:" + moveSymbols[dirIdx]);
                    for (char[] row : grid) {
                        System.out.println(new String(row));
                    }
                    System.out.println("");

                    continue; // Nothing moves




                } else if (grid[nextX][nextY] == 'O') {

                    while (grid[nextX][nextY] == 'O') {
                        int newX = nextX + directions[dirIdx][0];
                        int newY = nextY + directions[dirIdx][1];

                        // Check boundaries
                        if (newX < 0 || newX >= rows || newY < 0 || newY >= cols) {
                            break; // Stop if out of bounds
                        }

                        // Movement conditions
                        if (grid[newX][newY] == '.') {
                            grid[newX][newY] = 'O';
                            grid[currentX][currentY] = '.';
                            currentX += directions[dirIdx][0];
                            currentY += directions[dirIdx][1];
                            grid[currentX][currentY] = '@';
                            break;
                        } else if (grid[newX][newY] == '#') {
                            // Stop if an obstacle is encountered
                            break;
                        } else if (grid[newX][newY] == 'O') {
                            // Continue looking further along the direction
                            nextX = newX;
                            nextY = newY;
                        }
                    }




                } else if (grid[nextX][nextY] == '.') {
                    // Otherwise, just move the robot
                    grid[nextX][nextY] = '@';
                    grid[currentX][currentY] = '.';
                    currentX = nextX;
                    currentY = nextY;
                }
            }

            // Debugging
            System.out.println(i + "th Grid:" + moveSymbols[dirIdx]);
            for (char[] row : grid) {
                System.out.println(new String(row));
            }
            System.out.println("");
        }


        // Calculate GPS sum of all boxes
        // GPS= 100 × row + col
        int gpsSum = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 'O') {
                    gpsSum += 100 * i + j;
                    System.out.println("i: " + i + " j: " + j + " Gps: " + gpsSum);
                }
            }
        }

        System.out.println("Sum: " + gpsSum);
    }
}
