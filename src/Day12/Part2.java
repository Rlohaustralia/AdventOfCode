package Day12;
import java.io.*;
import java.util.*;

public class Part2 {

    static final int[][] DIRS = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}}; // up, right, down, left

    public static void main(String[] args) throws IOException {
        String infile = args.length >= 1 ? args[0] : "src/Day12/input.txt";
        BufferedReader reader = new BufferedReader(new FileReader(infile));

        List<String> grid = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            grid.add(line.strip());
        }
        reader.close();

        int R = grid.size();
        int C = grid.get(0).length();

        Set<String> seen = new HashSet<>();
        long p1 = 0, p2 = 0;

        for (int r = 0; r < R; r++) {
            for (int c = 0; c < C; c++) {
                String cell = r + "," + c;
                if (seen.contains(cell)) {
                    continue;
                }

                Queue<int[]> queue = new LinkedList<>();
                queue.offer(new int[]{r, c});
                int area = 0;
                int perim = 0;
                Map<String, Set<String>> perimMap = new HashMap<>();

                while (!queue.isEmpty()) {
                    int[] current = queue.poll();
                    int r2 = current[0], c2 = current[1];
                    String currentCell = r2 + "," + c2;
                    if (seen.contains(currentCell)) {
                        continue;
                    }
                    seen.add(currentCell);
                    area++;

                    for (int[] dir : DIRS) {
                        int rr = r2 + dir[0], cc = c2 + dir[1];
                        if (rr >= 0 && rr < R && cc >= 0 && cc < C && grid.get(rr).charAt(cc) == grid.get(r2).charAt(c2)) {
                            queue.offer(new int[]{rr, cc});
                        } else {
                            perim++;
                            String dirKey = dir[0] + "," + dir[1];
                            perimMap.putIfAbsent(dirKey, new HashSet<>());
                            perimMap.get(dirKey).add(currentCell);
                        }
                    }
                }

                int sides = 0;
                for (Map.Entry<String, Set<String>> entry : perimMap.entrySet()) {
                    Set<String> seenPerim = new HashSet<>();
                    for (String perimCell : entry.getValue()) {
                        if (!seenPerim.contains(perimCell)) {
                            sides++;
                            queue.offer(stringToCoords(perimCell));
                            while (!queue.isEmpty()) {
                                int[] current = queue.poll();
                                String currentPerim = current[0] + "," + current[1];
                                if (seenPerim.contains(currentPerim)) {
                                    continue;
                                }
                                seenPerim.add(currentPerim);
                                for (int[] dir : DIRS) {
                                    int rr = current[0] + dir[0], cc = current[1] + dir[1];
                                    String nextCell = rr + "," + cc;
                                    if (entry.getValue().contains(nextCell)) {
                                        queue.offer(new int[]{rr, cc});
                                    }
                                }
                            }
                        }
                    }
                }

                p1 += area * perim;
                p2 += area * sides;
            }
        }

        System.out.println(p1);
        System.out.println(p2);
    }

    private static int[] stringToCoords(String s) {
        String[] parts = s.split(",");
        return new int[]{Integer.parseInt(parts[0]), Integer.parseInt(parts[1])};
    }
}
