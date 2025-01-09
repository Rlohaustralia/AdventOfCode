package Day23;
import java.util.*;
import java.io.*;


public class Part1 {
    public static void main(String[] args) throws IOException {
        // Reading input
        BufferedReader br = new BufferedReader(new FileReader("src/Day23/input.txt"));
        List<String> lines = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            lines.add(line.trim());
        }
        br.close();

        // Create adjacency list
        // {gf=[ud], ud=[gf]}
        Map<String, List<String>> adj = new HashMap<>();
        for (String str : lines) {
            String[] parts = str.split("-");
            String left = parts[0];
            String right = parts[1];
            if (!adj.containsKey(left)) {
                adj.put(left, new ArrayList<>());
            }
            adj.get(left).add(right);

            if (!adj.containsKey(right)) {
                adj.put(right, new ArrayList<>());
            }
            adj.get(right).add(left);
        }

        // Find interconnections
        Set<List<String>> interConnections = new HashSet<>();
        for (String key : adj.keySet()) {
            for (String i : adj.get(key)) {
                for (String j : adj.get(key)) {
                    if (adj.get(i).contains(j)) {
                        List<String> connected = new ArrayList<>(Arrays.asList(key, i, j));
                        Collections.sort(connected);
                        interConnections.add(connected);
                    }
                }
            }
        }


        // Count if containing 't'
        int ans = 0;
        for (List<String> connections : interConnections) {
            if (connections.get(0).startsWith("t") || connections.get(1).startsWith("t") || connections.get(2).startsWith("t")) {
                ans++;
            }
        }

        System.out.println(ans);
    }
}
