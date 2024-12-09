package Day8;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Part1 {
    public static void main(String[] args) throws IOException {
        // 입력 파일 읽기
        BufferedReader br = new BufferedReader(new FileReader("src/Day8/input.txt"));
        List<String> lines = new ArrayList<>();
        String line;

        while ((line = br.readLine()) != null) {
            lines.add(line);
        }


        int n = lines.size(); // 그리드 크기
        List<String> grid = lines; // 리스트 형태의 그리드

        // 안테나 위치 저장
        Map<Character, List<int[]>> allLocs = new HashMap<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < grid.get(i).length(); j++) {
                char ch = grid.get(i).charAt(j);
                if (ch != '.') {
                    allLocs.putIfAbsent(ch, new ArrayList<>());
                    allLocs.get(ch).add(new int[]{i, j});
                }
            }
        }


        Set<String> antinodes = new HashSet<>();
        for (Map.Entry<Character, List<int[]>> entry : allLocs.entrySet()) {
            List<int[]> locs = entry.getValue();


            for (int i = 0; i < locs.size(); i++) {
                for (int j = i + 1; j < locs.size(); j++) {
                    int[] a = locs.get(i);
                    int[] b = locs.get(j);


                    int[] c = {a[0] - (b[0] - a[0]), a[1] - (b[1] - a[1])};
                    int[] d = {b[0] + (b[0] - a[0]), b[1] + (b[1] - a[1])};


                    if (inBounds(c, n)) {
                        antinodes.add(c[0] + "," + c[1]);
                    }
                    if (inBounds(d, n)) {
                        antinodes.add(d[0] + "," + d[1]);
                    }
                }
            }
        }

        System.out.println("Total unique antinode locations: " + antinodes.size());
    }


    private static boolean inBounds(int[] coord, int n) {
        return coord[0] >= 0 && coord[0] < n && coord[1] >= 0 && coord[1] < n;
    }
}
