package Day5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Part2 {

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
            if (!isValidUpdate(rulesMap, update)) {
                String[] reorderedUpdate = sortCorrectly(rulesMap, update);
                int middleIndex = reorderedUpdate.length / 2;
                middleSum += Integer.parseInt(reorderedUpdate[middleIndex]);
            }
        }
        return middleSum;
    }


    private static String[] sortCorrectly(Map<String, List<String>> rulesMap, String[] update) {
        // Topological Sort!
        // Step 1: Create a map to track the indegree of each item (dependencies)
        Map<String, Integer> indegree = new HashMap<>();
        Map<String, List<String>> adjacencyList = new HashMap<>();

        // Initialize adjacency list and indegree map for the topological sort
        for (String item : update) {
            indegree.put(item,0);
            adjacencyList.put(item, new ArrayList<>());
        }

        // Step 2: Build the dependency graph based on rulesMap
        for (String key : rulesMap.keySet()) {
            for (String value : rulesMap.get(key)) {
                if (contains(update, key) && contains(update, value)) {
                    adjacencyList.get(key).add(value);
                    indegree.put(value, indegree.getOrDefault(value, 0) + 1);
                }
            }
        }

        // Step 3: Perform Topological Sort (Using Queue)
        Queue<String> queue = new LinkedList<>();
        for (String item : update) {
            // If there is no dependency, insert it to the queue
            if (indegree.get(item) == 0) {
                queue.offer(item);
            }
        }

        List<String> sortedList = new ArrayList<>();
        while (!queue.isEmpty()) {
            String current = queue.poll();
            sortedList.add(current);

            // Subtract dependency -1 for the adjacency list of the current
            for (String neighbor : adjacencyList.get(current)) {
                indegree.put(neighbor, indegree.get(neighbor) - 1);
                if (indegree.get(neighbor) == 0) {
                    queue.offer(neighbor);
                }
            }
        }

        // If we could not sort all items, there was cycle (invalid update)
        // In the topological sort algorithm, there should be a start and an end element.
        if (sortedList.size() != update.length) {
            throw new IllegalStateException("Cycle detected, invalid update.");
        }

        // Step 4: Return the sorted array
        // Creating an empty array of String and convert the list to an array
        // We can do 'new String[sortedList.size()]' instead of 'new String[0]'
        // but using new String[0] is a common practice because it’s concise, and Java efficiently handles resizing internally
        return sortedList.toArray(new String[0]);
    }

    private static boolean contains(String[] update, String num) {
        for (String value : update) {
            if (value.equals(num)) {
                return true;
            }
        }
        return false;
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
