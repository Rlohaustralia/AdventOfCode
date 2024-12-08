package Day7;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Part1 {
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader("src/Day7/input.txt"));
        String line;

        Map <String, List<String>> map = new HashMap<>();
        while ((line = br.readLine()) != null) {
            // Parsing the data
            String[] str = line.split(":");
            String key = str[0].trim();
            String[] values = str[1].trim().split(" ");

            List<String> valueList = new ArrayList<>();
            for (String value : values) {
                valueList.add(value.trim());
            }
            map.put(key, valueList);
        }
    }
}
