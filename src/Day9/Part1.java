package Day9;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Part1 {
    public static void main(String[] args) throws IOException {

//        // Test input data
//        String test = "2333133121414131402";
//        StringBuilder combinedString = new StringBuilder();
//        for (int i = 0; i < test.length(); i++) {
//            combinedString.append(test.charAt(i));
//        }


        // 하지만 Integer.parseInt()를 사용할 수도 있습니다. 다만, Integer.parseInt()는 문자열 전체를 정수로 변환하므로, 문자 하나를 변환하는 데는 약간의 오버헤드가 있을 수 있습니다.
        // -'0' 문자열을 정수로 바꾸는????

        BufferedReader br = new BufferedReader(new FileReader("src/Day9/input.txt"));
        String line;
        StringBuilder combinedString = new StringBuilder();
        while ((line = br.readLine()) != null) {
            combinedString.append(line);
        }




        List<String> blocks = new ArrayList<>();
        boolean isFile = true; // Start with processing files
        int id = 0; // Start file ID at 0
        List<String> freeSpaceIdx = new ArrayList<>();


        // Process each character in the combined string
        String combinedInput = combinedString.toString();
        for (int i = 0; i < combinedInput.length(); i++) {
            String individual = String.valueOf(combinedInput.charAt(i)); // Get individual character as String
            int individualNum = Integer.parseInt(individual); // Convert to integer

            if (isFile) {
                // Add file blocks
                for (int j = 0; j < individualNum; j++) {
                    blocks.add(String.valueOf(id));
                }
                id++; // Increment file ID after adding blocks
            } else {
                // Save free space indexes
                freeSpaceIdx.add(String.valueOf(i));
                // Add free space
                for (int j = 0; j < individualNum; j++) {
                    blocks.add(".");
                }
            }

            isFile = !isFile; // Toggle between file blocks and free space
        }

        // Print the resulting blocks
        System.out.println(String.join("", blocks));

        // Move file blocks step by step
        while (true) {
            boolean moved = false;

            // Find the rightmost file block and the leftmost free space
            for (int i = 0; i < blocks.size(); i++) {
                if (blocks.get(i).equals(".")) { // Free space found
                    for (int j = blocks.size() - 1; j > i; j--) {
                        if (!blocks.get(j).equals(".")) { // File block found
                            // Move the file block to the free space
                            blocks.set(i, blocks.get(j));
                            blocks.set(j, ".");
                            moved = true;
                            break;
                        }
                    }
                }
                if (moved) break; // Stop after one move
            }

            // Print the current state after each move
            System.out.println(String.join("", blocks));

            if (!moved) break; // Stop if no more moves are possible
        }

        // Calculate the checksum
        int checksum = 0;
        for (int i = 0; i < blocks.size(); i++) {
            String block = blocks.get(i);
            if (!block.equals(".")) { // Skip free space
                int fileId = Integer.parseInt(block); // Convert file ID to integer
                checksum += i * fileId; // Add position multiplied by file ID
            }
        }

        // Print the final checksum
        System.out.println("Checksum: " + checksum);
    }
}
