package Day23;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class Part1 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("src/Day22/input.txt"));
        String line;

        List<String> list = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            list.add(line);
        }
        System.out.println("Original list size: " + list.size());

        // Remove duplicated ones first
        // kh-tc AND tc-kh are same
        List<String> newList = removeDuplicates(list);
        System.out.println("New list size: " + newList.size());

    }




    private static List<String> removeDuplicates(List<String> list) {
        List<String> newList = new ArrayList<>();
        HashSet<String> originalCombination = new HashSet<>();
        for (int i = 0; i < list.size(); i++) {
            String[] str = list.get(i).split("-");
            String left = str[0];
            String right= str[1];
            String reversed = left + right;
            if (originalCombination.contains(reversed)) {
                continue;
            }
            originalCombination.add(left + "," + right);
            newList.add(left + "," + right);
        }
        return newList;
    }
}
