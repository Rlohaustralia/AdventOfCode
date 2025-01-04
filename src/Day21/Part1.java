package Day21;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Part1 {


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("src/Day21/input.txt"));
        String line;
        List<String> list = new ArrayList<>();

        while ((line = br.readLine()) != null) {
            list.add(line);
        }

        Map<String, int[]> numericKeypads = new HashMap<>();
        numericKeypads.put("7", new int[]{0,0});
        numericKeypads.put("8", new int[]{0, 1});
        numericKeypads.put("9", new int[]{0, 2});
        numericKeypads.put("4", new int[]{1, 0});
        numericKeypads.put("5", new int[]{1, 1});
        numericKeypads.put("6", new int[]{1, 2});
        numericKeypads.put("1", new int[]{2, 0});
        numericKeypads.put("2", new int[]{2, 1});
        numericKeypads.put("3", new int[]{2, 2});
        numericKeypads.put("0", new int[]{3, 1});
        numericKeypads.put("A", new int[]{3, 2});

        Map<String, int[]> directionalKeypads = new HashMap<>();
        directionalKeypads.put("^", new int[]{0, 1});
        directionalKeypads.put("A", new int[]{0, 2}); // 로봇 팔은 맨 처음에 A 버튼에 있음
        directionalKeypads.put("<", new int[]{1, 0});
        directionalKeypads.put("v", new int[]{1, 1});
        directionalKeypads.put(">", new int[]{1, 2});

        Map<String, int[]> directions = new HashMap<>();
        directions.put(">", new int[]{0, 1});
        directions.put("v", new int[]{1, 0});
        directions.put("<", new int[]{0, -1});
        directions.put("^", new int[]{-1, 0});


        int shortestPathlength = 0;
        int codeNum = 0;
        int complexity = shortestPathlength * codeNum;
        int sumComplexity = 0;




    }
}
