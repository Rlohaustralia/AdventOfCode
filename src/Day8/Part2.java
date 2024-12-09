package Day8;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Part2 {
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader("src/Day8/input.txt"));
        List<String> gridData = new ArrayList<>();
        String line;

        while ((line = br.readLine()) != null) {
            gridData.add(line);
        }

        int gridSize = gridData.size();
        List<String> grid = gridData;


        Map<Character, List<int[]>> frequencyMap = new HashMap<>();
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < grid.get(i).length(); j++) {
                char symbol = grid.get(i).charAt(j);
                if (symbol != '.') {
                    frequencyMap.putIfAbsent(symbol, new ArrayList<>());
                    frequencyMap.get(symbol).add(new int[]{i, j});
                }
            }
        }


        Set<String> uniqueAntinodes = new HashSet<>();
        for (Map.Entry<Character, List<int[]>> entry : frequencyMap.entrySet()) {
            List<int[]> antennaLocations = entry.getValue();


            for (int i = 0; i < antennaLocations.size(); i++) {
                for (int j = i + 1; j < antennaLocations.size(); j++) {
                    int[] antenna1 = antennaLocations.get(i);
                    int[] antenna2 = antennaLocations.get(j);


                    generateAntinodes(antenna1, antenna2, uniqueAntinodes, gridSize);
                }
            }


            if (antennaLocations.size() > 1) {
                for (int[] location : antennaLocations) {
                    uniqueAntinodes.add(location[0] + "," + location[1]);
                }
            }
        }


        System.out.println("Total unique antinode locations: " + uniqueAntinodes.size());
    }


    private static void generateAntinodes(int[] ant1, int[] ant2, Set<String> antinodeSet, int size) {
        int dx = ant2[0] - ant1[0];
        int dy = ant2[1] - ant1[1];


        int x = ant1[0];
        int y = ant1[1];
        while (isWithinBounds(x, y, size)) {
            antinodeSet.add(x + "," + y);
            x -= dx;
            y -= dy;
        }


        x = ant2[0];
        y = ant2[1];
        while (isWithinBounds(x, y, size)) {
            antinodeSet.add(x + "," + y);
            x += dx;
            y += dy;
        }
    }


    private static boolean isWithinBounds(int x, int y, int size) {
        return x >= 0 && x < size && y >= 0 && y < size;
    }
}
