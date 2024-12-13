package Day11;

import java.io.*;
import java.util.*;

public class Part2 {

    public static void main(String[] args) throws IOException {
        // Read input file
        BufferedReader br = new BufferedReader(new FileReader("src/Day11/input.txt"));
        String[] originalNums = br.readLine().trim().split(" ");

        // Initialize the map to store number frequencies
        Map<Long, Long> frequencyMap = new HashMap<>();
        for (String num : originalNums) {
            long n = Long.parseLong(num);
            frequencyMap.put(n, frequencyMap.getOrDefault(n, 0L) + 1);
        }

        // Do the blink process
        int blink = 75;
        for (int i = 0; i < blink; i++) {
            frequencyMap = blink(frequencyMap);
        }

        // Calculate the sum of all frequencies (the number of iterations)
        long sum = 0;
        for (long count : frequencyMap.values()) {
            sum += count;
        }

        // Print the result
        System.out.println("Answer is: " + sum);
    }


    private static Map<Long, Long> blink(Map<Long, Long> frequencyMap) {
        Map<Long, Long> newFrequencyMap = new HashMap<>();

        for (Map.Entry<Long, Long> entry : frequencyMap.entrySet()) {
            long stone = entry.getKey();
            long frequency = entry.getValue();
            int len = String.valueOf(stone).length();

            // 3 Conditions
            if (stone == 0) {
                newFrequencyMap.put(1L, newFrequencyMap.getOrDefault(1L, 0L) + frequency);
            } else if (len % 2 == 0) {
                String str = String.valueOf(stone);
                long left = Long.parseLong(str.substring(0, len / 2));
                long right = Long.parseLong(str.substring(len / 2));
                newFrequencyMap.put(left, newFrequencyMap.getOrDefault(left, 0L) + frequency);
                newFrequencyMap.put(right, newFrequencyMap.getOrDefault(right, 0L) + frequency);
            } else {
                long newValue = stone * 2024;
                newFrequencyMap.put(newValue, newFrequencyMap.getOrDefault(newValue, 0L) + frequency);
            }
        }
        return newFrequencyMap;
    }
}