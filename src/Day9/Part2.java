package Day9;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Part2 {
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

            isFile = !isFile;  // Toggle between file blocks and free space
        }

        // Create the blocks array with the correct size
        int[] blocks = new int[totalBlocks];
        int idx = 0;
        int[] loc = new int[combinedInput.length()];  // Store the location of each file
        int[] size = new int[combinedInput.length()];  // Store the size of each file
        int id = 0;
        isFile = true;

        // Second pass: Populate the blocks array
        for (int i = 0; i < combinedInput.length(); i++) {
            int individualNum = Character.getNumericValue(combinedInput.charAt(i)); // Get individual number

            if (isFile) {
                loc[id] = idx;  // Store the location for file
                size[id] = individualNum;  // Store the size for the file
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

        // Call the move method to rearrange files
        blocks = move(blocks, loc, size);

        // Calculate checksum
        long checksum = 0;  // Use long to prevent overflow
        for (int i = 0; i < blocks.length; i++) {
            if (blocks[i] != -1) {  // Skip free space
                checksum += (long) i * blocks[i];  // Add position multiplied by file ID
            }
        }

        // Print the final checksum
        System.out.println("Checksum: " + checksum);
    }

    // Move method to rearrange the blocks (mirroring the Python logic)
    public static int[] move(int[] arr, int[] loc, int[] size) {
        int big = 0;
        while (size[big] > 0) {
            big++;
        }
        big--; // Step back to the last file

        for (int toMove = big; toMove >= 0; toMove--) {
            // Find first free space large enough for the current file
            int freeSpace = 0;
            int firstFree = 0;
            while (firstFree < loc[toMove] && freeSpace < size[toMove]) {
                firstFree = firstFree + freeSpace;
                freeSpace = 0;
                while (arr[firstFree] != -1) { // Skip non-free spaces
                    firstFree++;
                }
                while (firstFree + freeSpace < arr.length && arr[firstFree + freeSpace] == -1) {
                    freeSpace++;
                }
            }

            if (firstFree >= loc[toMove]) continue;

            // Move file by swapping block values
            for (int idx = firstFree; idx < firstFree + size[toMove]; idx++) {
                arr[idx] = toMove;
            }
            for (int idx = loc[toMove]; idx < loc[toMove] + size[toMove]; idx++) {
                arr[idx] = -1;
            }
        }

        return arr;
    }
}

