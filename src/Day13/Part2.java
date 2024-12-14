package Day13;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Part2 {
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
        List<Map<String, Map<String, Long>>> machines = new ArrayList<>();

        // Extract data from input using regex
        while (m.find()) {
            Map<String, Map<String, Long>> machine = new HashMap<>();

            // Button A data
            Map<String, Long> buttonA = new HashMap<>();
            buttonA.put("X", Long.parseLong(m.group(1)));
            buttonA.put("Y", Long.parseLong(m.group(2)));
            machine.put("Button A", buttonA);

            // Button B data
            Map<String, Long> buttonB = new HashMap<>();
            buttonB.put("X", Long.parseLong(m.group(3)));
            buttonB.put("Y", Long.parseLong(m.group(4)));
            machine.put("Button B", buttonB);

            // Prize data
            Map<String, Long> prize = new HashMap<>();

            prize.put("X", Long.parseLong(m.group(5)) + 10000000000000L);
            prize.put("Y", Long.parseLong(m.group(6)) + 10000000000000L);
            machine.put("Prize", prize);


            // Add machine data to the list
            machines.add(machine);
        }

        // Token costs for A and B buttons
        int tokenA = 3;
        int tokenB = 1;

        int totalTokensUsed = 0;

        for (Map<String, Map<String, Long>> machine : machines) {
            Map<String, Long> buttonA = machine.get("Button A");
            Map<String, Long> buttonB = machine.get("Button B");
            Map<String, Long> prize = machine.get("Prize");

            long buttonA_X = buttonA.get("X");
            long buttonA_Y = buttonA.get("Y");
            long buttonB_X = buttonB.get("X");
            long buttonB_Y = buttonB.get("Y");
            long prize_X = prize.get("X");
            long prize_Y = prize.get("Y");

            // Calculate the determinant of matrix A
            long detA = buttonA_X * buttonB_Y - buttonA_Y * buttonB_X;

            // If the determinant is 0, the system has no unique solution
            if (detA == 0) {
                System.out.println("No unique solution for this machine.");
                continue;
            }

            // Calculate determinants for A1 and A2
            long detA1 = prize_X * buttonB_Y - prize_Y * buttonB_X;
            long detA2 = buttonA_X * prize_Y - buttonA_Y * prize_X;

            // Calculate a and b using Cramer's rule
            long a = detA1 / detA;
            long b = detA2 / detA;

            // Calculate the number of tokens used
            int tokensUsed = (int) (tokenA * a + tokenB * b);
            totalTokensUsed += tokensUsed;
        }

        // Output results
        System.out.println("Total tokens used: " + totalTokensUsed);
    }
}
