package Day5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Part1 {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader("src/Day5/input.txt"));
        String line;
        boolean isEmptyLine = false;


        // Pre-processing
        List<String> rules = new ArrayList<>();
        List<String> update = new ArrayList<>();

        // Separate 'rules' data and 'update' data
        while ((line = br.readLine()) != null) {
            if(line.trim().isEmpty()) {
                isEmptyLine = true;
                continue;
            }
            if (isEmptyLine) {
                update.add(line);
            } else {
                rules.add(line);
            }
        }


        // Process 'rules' data
        Map<String, List<String>> rulesMap = new HashMap<>();
        for (String rule : rules) {
            String[] str = rule.split("\\|");
            String key = str[0];
            String val = str[1];

            // Add each rules data to the map
            if (!rulesMap.containsKey(key)) {
                rulesMap.put(key, new ArrayList<>());
            } else {
                rulesMap.get(key).add(val);
            }
        }

        // Process 'update' data
        String[][] updateStr = new String[update.size()][];
        for (int i = 0; i < update.size(); i++) {
            updateStr[i] = update.get(i).split(",");
        }

        // Result
        int middleSum = calculateMiddleSum(rulesMap,updateStr);
        System.out.println("Middle Sum: " + middleSum);
    }


    private static int calculateMiddleSum(Map<String, List<String>> rulesMap, String[][] updateArray) {
        int middleSum = 0;
        for (String[] update : updateArray) {
            if (isValidUpdate(rulesMap, update)) {
                int middleIndex = update.length / 2;
                middleSum += Integer.parseInt(update[middleIndex]);
            }
        }
        return middleSum;
    }

    private static boolean isValidUpdate(Map<String, List<String>> rulesMap, String[] update) {

        Map<String, Integer> indexMap = new HashMap<>();
        for (int i = 0; i < update.length; i++) {
            indexMap.put(update[i], i);
        }

        for (String key : rulesMap.keySet()) {
            if (!indexMap.containsKey(key)) continue;
            int keyIndex = indexMap.get(key);

            List<String> values = rulesMap.get(key);
            for (String value : values) {
                if (!indexMap.containsKey(value)) continue;
                int valueIndex = indexMap.get(value);

                if(keyIndex >= valueIndex) {
                    return false;
                }
            }
        }
        return true;
    }
}
