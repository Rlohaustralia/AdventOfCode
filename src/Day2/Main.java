package Day2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    // Day 2: Red-Nosed Reports

    public static void main(String[] args) throws IOException {

        // Read the data file
        BufferedReader br = new BufferedReader(new FileReader("src/Day2/input.txt"));
        String line = br.readLine();

        int safe = 0;  // Count of safe reports


        // Process each line
        while (line != null) {
            line = line.trim();  // Remove leading/trailing spaces
            String[] nums = line.split("\\s+");  // Split the line into an array of strings

            boolean isSafe = checkIfSafe(nums);

            if (!isSafe) {
                // Try one more time by removing one element
                checkIfSafeAgain(nums);
            }

            if (isSafe) {
                safe++;
            }

            // Go to the next line
            line = br.readLine();
        }

        // Output the number of safe reports
        System.out.println("Safe: " + safe);

    }


    private static boolean checkIfSafe(String[] levels) {
        boolean isIncreasing = true;
        boolean isDecreasing = true;

        for (int i = 1; i < levels.length; i++) {

            int diff = Integer.parseInt(levels[i]) - Integer.parseInt(levels[i - 1]);

            if (diff < -3 || diff > 3 || diff == 0) {
                return false;
            }

            if (diff < 0) {
                isIncreasing = false;
            } else if (diff > 0) {
                isDecreasing = false;
            }
        }
        return isIncreasing || isDecreasing;
    }


    private static boolean checkIfSafeAgain(String[] levels) {
        for (int i = 0; i < levels.length; i++) {
            String[] newLevels = new String[levels.length - 1];

            int k = 0;
            for (int j = 0; j < levels.length; j++) {
                if (j != i) {
                    newLevels[k] = levels[j];
                    k++;
                }
            }

            if (checkIfSafe(newLevels)) {
                return true;
            }
        }
        return false;
    }
}
