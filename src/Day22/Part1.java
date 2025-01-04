package Day22;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Part1 {
    static int iterations = 2000;

    public static long mix(long a, long b) {
        return a ^ b;
    }

    public static long prune(long a) {
        return a % 16777216;
    }

    public static long getNextSecretNum(long num) {

        // Step 1: Multiply by 64 and mix
        num = prune(mix(num, num * 64));

        // Step 2: Divide by 32 and mix
        num = prune(mix(num, num / 32));

        // Step 3: Multiply by 2048 and mix
        num = prune(mix(num, num * 2048));

        return num;
    }


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("src/Day22/input.txt"));
        String line;

        List<Long> initials = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            initials.add(Long.valueOf(line));
        }

        // Calculate the 2000th secret number for each initial secret number
        long result = 0;
        for (int i = 0; i < initials.size(); i++) {
            long initialNum = initials.get(i);
            for (int j = 0; j < iterations; j++) {
                initialNum = getNextSecretNum(initialNum);
            }
            result += initialNum;
        }

        // Output the results
        System.out.println(result);
    }
}
