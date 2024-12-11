package Day9;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Part1 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("src/Day9/input.txt"));
        String line;
        StringBuilder combinedString = new StringBuilder();
        while ((line = br.readLine()) != null) {
            combinedString.append(line);
        }

        String combinedInput = combinedString.toString();
        int totalBlocks = 0;

        // First pass: calculate the total number of blocks needed
        boolean isFile = true;
        for (int i = 0; i < combinedInput.length(); i++) {
            int individualNum = Character.getNumericValue(combinedInput.charAt(i)); // Get individual number
            totalBlocks += individualNum; // Add the count to total blocks

            if (isFile) {
                // Add blocks for file
            } else {
                // Add blocks for free space
            }
            isFile = !isFile;  // Toggle between file blocks and free space
        }

        // Create the blocks array with the correct size
        int[] blocks = new int[totalBlocks];
        int idx = 0;
        isFile = true;
        int id = 0;

        // Second pass: Populate the blocks array
        for (int i = 0; i < combinedInput.length(); i++) {
            int individualNum = Character.getNumericValue(combinedInput.charAt(i)); // Get individual number

            if (isFile) {
                for (int j = 0; j < individualNum; j++) {
                    blocks[idx++] = id;  // File block with ID
                }
                id++;  // Increment file ID after adding blocks
            } else {
                for (int j = 0; j < individualNum; j++) {
                    blocks[idx++] = -1;  // Free space represented by -1
                }
            }

            isFile = !isFile;  // Toggle between file blocks and free space
        }

        // Move file blocks step by step
        while (true) {
            boolean moved = false;

            for (int i = 0; i < blocks.length; i++) {
                if (blocks[i] == -1) {  // Free space found
                    for (int j = blocks.length - 1; j > i; j--) {
                        if (blocks[j] != -1) {  // File block found
                            blocks[i] = blocks[j];
                            blocks[j] = -1;
                            moved = true;
                            break;
                        }
                    }
                }
                if (moved) break;  // Stop after one move
            }

            if (!moved) break;  // Stop if no more moves are possible
        }

        // Calculate checksum using long to prevent overflow
        long checksum = 0;  // Use long to prevent overflow
        for (int i = 0; i < blocks.length; i++) {
            if (blocks[i] != -1) {  // Skip free space
                checksum += (long) i * blocks[i];  // Add position multiplied by file ID, using long
            }
        }

        // Print the final checksum
        System.out.println("Checksum: " + checksum);
    }
}
