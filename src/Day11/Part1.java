package Day11;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Part1 {
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

            ArrayList<String> currentList = new ArrayList<>();

            for (String str : lists) {
                int strLen = str.length();

                if (str.equals("0")) {
                    currentList.add("1");
                } else if (strLen % 2 == 0) {
                    String left = str.substring(0, strLen / 2).replaceFirst("^0+", "");
                    String right = str.substring(strLen / 2).replaceFirst("^0+", "");
                    if (left.isEmpty()) left = "0";
                    if (right.isEmpty()) right = "0";
                    currentList.add(left);
                    currentList.add(right);
                } else {
                    Long num = Long.parseLong(str) * 2024;
                    String numStr = String.valueOf(num);
                    currentList.add(numStr);
                }
            }
            System.out.println("List contents: " + currentList);

            lists = currentList;
            blink--;
            cycle++;

        }
        System.out.println(lists.size());
    }
}
