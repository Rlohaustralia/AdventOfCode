package Day24;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Part1 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("src/Day24/input.txt"));
        String line;

        HashMap<String, Integer> firstList = new HashMap<>();
        List<List<String>> secondList = new ArrayList<>();
        boolean isSecondList = false;

        // Parse the input
        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) {
                isSecondList = true;
                continue;
            }
            if (!isSecondList) {
                String[] str = line.split(": ");
                firstList.put(str[0], Integer.parseInt(str[1]));
            } else {
                String[] str2 = line.split(" ");
                secondList.add(Arrays.asList(str2[0], str2[1], str2[2], str2[4]));
            }
        }
        br.close();


        // Iterative computation
        while (true) {
            boolean allDone = true;
            for (List<String> operation : secondList) {
                String x = operation.get(0);
                String o = operation.get(1);
                String y = operation.get(2);
                String out = operation.get(3);

                if (!firstList.containsKey(x) || !firstList.containsKey(y)) {
                    allDone = false;
                    continue;
                }

                int xValue = firstList.get(x);
                int yValue = firstList.get(y);

                // Perform the operation and store the result
                int result = calculateOperator(xValue, yValue, o);
                firstList.put(out, result);
            }

            if (allDone) {
                break;
            }
        }

        // Extract and sort "z" keys, then calculate final binary result
        List<Map.Entry<String, Integer>> sortedZValues = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : firstList.entrySet()) {
            if (entry.getKey().startsWith("z")) {
                sortedZValues.add(entry);
            }
        }

        sortedZValues.sort(Map.Entry.comparingByKey());

        StringBuilder binaryString = new StringBuilder();
        for (Map.Entry<String, Integer> entry : sortedZValues) {
            binaryString.append(entry.getValue());
        }

        long finalResult = Long.parseLong(binaryString.reverse().toString(), 2);
        System.out.println("Sorted z-values: " + sortedZValues);
        System.out.println("Final Result: " + finalResult);
    }

    private static int calculateOperator(int x, int y, String operator) {
        switch (operator) {
            case "AND":
                return x & y;
            case "OR":
                return x | y;
            case "XOR":
                return x ^ y;
            default:
                throw new IllegalArgumentException("Unknown operator: " + operator);
        }
    }
}
