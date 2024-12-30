package Day19;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.Buffer;
import java.util.*;

public class Part1 {
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


        int count = 0;
        for (String design : designs) {
            if (isValidDesign(design, patterns, new HashMap<>())) {
                System.out.println(design);
                count++;
            }
        }

        System.out.println("Answer: " + count);
    }


    private static boolean isValidDesign(String design, List<String> patterns, Map<String, Boolean> memoization) {

        if(design.isEmpty()) return true;

        if (memoization.containsKey(design)) {
            return memoization.get(design);
        }

        for (String pattern : patterns) {
            if (design.length() >= pattern.length() &&
                design.substring(0, pattern.length()).equals(pattern)) {

                String remaining = design.substring(pattern.length());
                boolean result = isValidDesign(remaining, patterns, memoization);

                memoization.put(remaining, result);

                if (result) return true;
            }
        }
        return false;
    }
}
