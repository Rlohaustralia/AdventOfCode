package Day23;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Part2 {
    public static void main(String[] args) throws IOException {
        // Reading input
        BufferedReader br = new BufferedReader(new FileReader("src/Day23/input.txt"));
        List<String> lines = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            lines.add(line.trim());
        }
        br.close();

        String result = solvePart2(lines);
        System.out.println("Part 2 solution = " + result);
    }

    private static String solvePart2(final List<String> lines) {
        Map<String, Set<String>> adjSets = createAdjacencySets(lines);
        Set<String> largestClique = new HashSet<>();

        for (String node : adjSets.keySet()) {
            Set<String> neighbors = adjSets.get(node);
            List<String> neighborList = new ArrayList<>(neighbors);

            // Generate all subsets of neighbors + current node
            int size = neighborList.size();
            for (int i = 0; i < (1 << size); i++) {
                Set<String> clique = new HashSet<>();
                clique.add(node);

                // Add subset of neighbors to the clique
                for (int j = 0; j < size; j++) {
                    if ((i & (1 << j)) != 0) {
                        clique.add(neighborList.get(j));
                    }
                }

                // Check if it's a valid clique
                if (isClique(clique, adjSets) && clique.size() > largestClique.size()) {
                    largestClique = clique;
                }
            }
        }

        // Sort and return the largest clique as a comma-separated string
        List<String> sortedClique = new ArrayList<>(largestClique);
        Collections.sort(sortedClique);
        return String.join(",", sortedClique);
    }

    private static Map<String, Set<String>> createAdjacencySets(final List<String> connections) {
        Map<String, Set<String>> adj = new HashMap<>();
        for (String str : connections) {
            String[] parts = str.split("-");
            String left = parts[0];
            String right = parts[1];

            adj.computeIfAbsent(left, k -> new HashSet<>()).add(right);
            adj.computeIfAbsent(right, k -> new HashSet<>()).add(left);
        }
        return adj;
    }

    private static boolean isClique(Set<String> clique, Map<String, Set<String>> adjSets) {
        for (String node : clique) {
            for (String other : clique) {
                if (!node.equals(other) && !adjSets.getOrDefault(node, new HashSet<>()).contains(other)) {
                    return false;
                }
            }
        }
        return true;
    }
}

