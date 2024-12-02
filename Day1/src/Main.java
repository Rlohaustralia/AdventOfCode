import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {

        // Day 1: Historian Hysteria

        // Part 1

        try {
            BufferedReader br = new BufferedReader (new FileReader("Day1/input.txt"));

            List<Integer> firstList = new ArrayList<>();
            List<Integer> secondList = new ArrayList<>();

            String line = br.readLine();
            boolean isFirstList = true;

            while (line != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    String[] nums = line.split("\\s+");
                    for (int i = 0; i < nums.length; i++) {
                        if (i % 2 == 0) {
                            secondList.add(Integer.parseInt(nums[i]));
                        } else {
                            firstList.add(Integer.parseInt(nums[i]));
                        }
                    }
                }
                line = br.readLine();
            }

            Collections.sort(firstList);
            Collections.sort(secondList);

            int diffSum = 0;
            for (int i = 0; i < firstList.size(); i++) {
                diffSum += Math.abs(firstList.get(i) - secondList.get(i));
            }

            System.out.println("diffSum: " + diffSum);



            // Part 2

            int similaritySum = 0;
            int count = 0;

            for (int i = 0; i < firstList.size(); i++) {
                for (int j = 0; j < secondList.size(); j++) {
                    if (Objects.equals(firstList.get(i), secondList.get(j))) {
                        count++;
                    }
                }
                similaritySum += count * firstList.get(i);
                count = 0;
            }

            System.out.println("similaritySum: " + similaritySum);




        } catch (IOException e) {
            throw new RuntimeException(e);
        }





    }
}