package Day22;
import java.io.*;
import java.util.*;

public class Part2 {
    static int modulo = 16777216;
    static int iteration = 2000;

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader("src/Day22/input.txt"));
        String line;

        List<Integer> nums = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            if (!line.trim().isEmpty()) {
                nums.add(Integer.parseInt(line.trim()));
            }
        }

        Map<List<Integer>, Integer> memos = new HashMap<>(); // 시퀀스 차이의 특정 패턴을 추적하며, 각 패턴에 대한 값을 기록

        // Process each number
        for (int num : nums) {
            int[] sequence = new int[iteration + 1];
            sequence[0] = num % 10;

            // Generate sequence
            for (int j = 1; j <= iteration; j++) {
                num = (int) ((num ^ (num * 64L)) % modulo);
                num = (int) ((num ^ (num / 32L)) % modulo);
                num = (int) ((num ^ (num * 2048L)) % modulo);
                sequence[j] = num % 10;
            }

            // Calculate differences
            int[] diffs = new int[iteration];
            for (int j = 1; j <= iteration; j++) {
                diffs[j - 1] = sequence[j] - sequence[j - 1];
            }

            // Track sequences and their sums
            Set<List<Integer>> seen = new HashSet<>();
            for (int p = 3; p < diffs.length; p++) {
                List<Integer> h = Arrays.asList(diffs[p - 3], diffs[p - 2], diffs[p - 1], diffs[p]);
                if (!memos.containsKey(h) && !seen.contains(h)) {
                    memos.put(h, sequence[p + 1]);
                } else if (!seen.contains(h)) {
                    memos.put(h, memos.get(h) + sequence[p + 1]);
                }
                seen.add(h);
            }
        }

        // Find maximum value in memos
        int maxBananas = 0;
        for (int value : memos.values()) {
            if (value > maxBananas) {
                maxBananas = value;
            }
        }

        // Print results
        System.out.println("Part 2: " + maxBananas);
    }
}
