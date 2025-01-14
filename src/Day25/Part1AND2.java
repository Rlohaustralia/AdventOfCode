package Day25;

import java.io.*;
import java.util.*;

public class Part1AND2 {
    static int WIDTH = 5;
    static int HEIGHT = 7;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("src/Day25/input.txt"));
        StringBuilder content = new StringBuilder();
        String line;

        // Read the entire file
        while ((line = br.readLine()) != null) {
            content.append(line).append("\n");
        }

        String[] blocks = content.toString().split("\n\n");

        List<List<Integer>> locks = new ArrayList<>();
        List<List<Integer>> keys = new ArrayList<>();

        for (String block : blocks) {
            String[] blockArr = block.split("\n");
            Pair result = process(blockArr);
            if (result.lock) {
                locks.add(result.values);
            } else {
                keys.add(result.values);
            }
        }


        int validPairsCount = 0;

        for (List<Integer> lock : locks) {
            for (List<Integer> key : keys) {
                boolean isValid = true;

                for (int j = 0; j < 5; j++) {
                    if (lock.get(j) + key.get(j) > 7) {
                        isValid = false;
                        break;
                    }
                }
                if (isValid) {
                    validPairsCount++;
                }
            }
        }
        System.out.println(validPairsCount);
    }


    public static Pair process(String[] block) {
        boolean lock = block[0].charAt(0) == '#';
        List<Integer> heights = new ArrayList<>();

        if (lock) {
            for (int j = 0; j < WIDTH; j++) {
                for (int i = 0; i < HEIGHT; i++) {
                    if (block[i].charAt(j) == '.') {
                        heights.add(i);
                        break;
                    }
                }
            }
        } else {
            for (int j = 0; j < WIDTH; j++) {
                for (int i = HEIGHT - 1; i >= 0; i--) {
                    if (block[i].charAt(j) == '.') {
                        heights.add(HEIGHT - 1 - i);
                        break;
                    }
                }
            }
        }
        return new Pair(heights, lock);
    }





    static class Pair {
        List<Integer> values;
        boolean lock;

        Pair(List<Integer> values, boolean lock) {
            this.values = values;
            this.lock = lock;
        }
    }
}

