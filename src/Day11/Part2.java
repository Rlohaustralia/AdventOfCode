package Day11;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Part2 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("src/Day11/input.txt"));
        String line;

        ArrayList<String> lists = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            for (String num : line.split(" ")) {
                lists.add(num.trim());
            }
        }

        int blink = 25;
        int cycle = 1;
        while (blink > 0) {
            System.out.println("After " + cycle + " blink:");

            for (int i = 0; i < lists.size(); i++) {
                String str = lists.get(i);
                int strLen = str.length();

                if (str.equals("0")) {
                    lists.set(i, "1");  // Change "0" to "1"
                } else if (strLen % 2 == 0) {
                    String left = str.substring(0, strLen / 2).replaceFirst("^0+", "");
                    String right = str.substring(strLen / 2).replaceFirst("^0+", "");

                    // Avoid adding empty strings or unnecessary zeros
                    if (left.isEmpty()) left = "0";
                    if (right.isEmpty()) right = "0";

                    lists.set(i, left);  // Replace current element with left part
                    lists.add(i + 1, right);  // Add right part at the next index
                    i++;  // Skip the next index since it's already processed
                } else {
                    Long num = Long.parseLong(str) * 2024;
                    String numStr = String.valueOf(num);
                    lists.set(i, numStr);  // Replace the current element with the multiplied value
                }

                System.out.println("List contents: " + lists);
            }

            blink--;
            cycle++;
        }

        System.out.println(lists.size());
    }
}
