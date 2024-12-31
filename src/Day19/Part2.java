package Day19;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Part2 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("src/Day19/input.txt"));
        String line;

        List<String> patterns = new ArrayList<>();
        List<String> designs = new ArrayList<>();
        boolean isDesignSection = false;

        while ((line = br.readLine()) != null) {
            if (line.isEmpty()) {
                isDesignSection = true;
                continue;
            }
            if (isDesignSection) {
                designs.add(line);
            } else {
                String[] patternArray = line.split(",\\s*");
                for (String pattern : patternArray) {
                    patterns.add(pattern);
                }
            }
        }
        br.close();

        long sumOfPossibleOptions = 0;

        // For each design, calculate the number of possible ways to form it
        for (String design : designs) {
            sumOfPossibleOptions += getPossibleOptions(design, patterns, new HashMap<>());
        }

        // Print the total number of ways all designs can be formed
        System.out.println(sumOfPossibleOptions);
    }

    // Change return type to long to handle large numbers
    private static long getPossibleOptions(String design, List<String> patterns, Map<String, Long> memoization) {

        // If the design is empty, there is one way to make it (i.e., no more patterns needed)
        if (design.isEmpty()) return 1;

        // Check if this design has already been computed
        if (memoization.containsKey(design)) {
            return memoization.get(design);
        }

        long totalWays = 0;

        // Try all patterns to see if they match at the beginning of the design
        for (String pattern : patterns) {
            // Manually check if the design starts with the pattern
            if (design.length() >= pattern.length() && design.substring(0, pattern.length()).equals(pattern)) {
                String remainingDesign = design.substring(pattern.length());
                totalWays += getPossibleOptions(remainingDesign, patterns, memoization);
            }
        }

        // Memoize the result for this design
        memoization.put(design, totalWays);

        return totalWays;
    }
}
