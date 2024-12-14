package Day13;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Part1 {
    public static void main(String[] args) throws IOException {
        // Read input file
        BufferedReader br = new BufferedReader(new FileReader("src/Day13/input.txt"));
        StringBuilder stringBuilder = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            stringBuilder.append(line).append("\n");
        }

        // Regular expression to extract data
        String pattern = "Button A: X([+-]?\\d+), Y([+-]?\\d+)\\n" +
                "Button B: X([+-]?\\d+), Y([+-]?\\d+)\\n" +
                "Prize: X=(\\d+), Y=(\\d+)";

        // Compile the regex
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(stringBuilder.toString());

        // Store results for each machine
        List<Map<String, Map<String, Integer>>> machines = new ArrayList<>();

        // Extract data from input using regex
        while (m.find()) {
            Map<String, Map<String, Integer>> machine = new HashMap<>();

            // Button A data
            Map<String, Integer> buttonA = new HashMap<>();
            buttonA.put("X", Integer.parseInt(m.group(1)));
            buttonA.put("Y", Integer.parseInt(m.group(2)));
            machine.put("Button A", buttonA);

            // Button B data
            Map<String, Integer> buttonB = new HashMap<>();
            buttonB.put("X", Integer.parseInt(m.group(3)));
            buttonB.put("Y", Integer.parseInt(m.group(4)));
            machine.put("Button B", buttonB);

            // Prize data
            Map<String, Integer> prize = new HashMap<>();
            prize.put("X", Integer.parseInt(m.group(5)));
            prize.put("Y", Integer.parseInt(m.group(6)));
            machine.put("Prize", prize);

            // Add machine data to the list
            machines.add(machine);
        }

        // Token costs for A and B buttons
        int tokenA = 3;
        int tokenB = 1;

        int totalTokensUsed = 0;

        for (Map<String, Map<String, Integer>> machine : machines) {
            Map<String, Integer> buttonA = machine.get("Button A");
            Map<String, Integer> buttonB = machine.get("Button B");
            Map<String, Integer> prize = machine.get("Prize");

            int buttonA_X = buttonA.get("X");
            int buttonA_Y = buttonA.get("Y");
            int buttonB_X = buttonB.get("X");
            int buttonB_Y = buttonB.get("Y");
            int prize_X = prize.get("X");
            int prize_Y = prize.get("Y");

            int fewestTokens = Integer.MAX_VALUE;

            for (int a = 0; a <= 100; a++) {
                for (int b = 0; b <= 100; b++) {
                    int totalX = a * buttonA_X + b * buttonB_X;
                    int totalY = a * buttonA_Y + b * buttonB_Y;

                    if (totalX == prize_X && totalY == prize_Y) {
                        int tokensUsed = tokenA * a + tokenB * b;
                        fewestTokens = Math.min(fewestTokens, tokensUsed);
                    }
                }
            }

            if (fewestTokens != Integer.MAX_VALUE) {
                totalTokensUsed += fewestTokens;
            }
        }
        // Output results
        System.out.println("Total tokens used: " + totalTokensUsed);
    }
}