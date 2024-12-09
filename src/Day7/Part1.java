package Day7;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Part1 {
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader("src/Day7/input.txt"));
        String line;
        long totalSum = 0;

        while ((line = br.readLine()) != null) {
            String[] parts = line.split(":");
            long key = Long.parseLong(parts[0].trim());
            String[] values = parts[1].trim().split(" ");

            List<Long> numbers = new ArrayList<>();
            for (String value : values) {
                numbers.add(Long.parseLong(value));
            }

            // Test all combinations of "*" and "+"
            List<String> operatorCombinations = generateOperatorCombinations(numbers.size() - 1);

            boolean validEquationFound = false;
            for (String operators : operatorCombinations) {
                if (evaluateEquation(operators, numbers) == key) {
                    totalSum += key;
                    validEquationFound = true;
                    break;
                }
            }
        }

        System.out.println("Total sum of valid equations: " + totalSum);
    }


    // Generate all combinations of "*" and "+"
    private static List<String> generateOperatorCombinations(int length) {
        List<String> combinations = new ArrayList<>();
        int totalCombinations = (int) Math.pow(2, length);

        for (int i = 0; i < totalCombinations; i++) {
            StringBuilder combination = new StringBuilder();
            int temp = i;
            for (int j = 0; j < length; j++) {
                combination.append(temp % 2 == 0 ? "*" : "+");
                temp /= 2;
            }
            combinations.add(combination.toString());
        }
        return combinations;
    }


    // Evaluate the equation with the given operator combination
    private static long evaluateEquation(String operators, List<Long> numbers) {
        long result = numbers.get(0);
        for (int i = 1; i < numbers.size(); i++) {
            if (operators.charAt(i - 1) == '+') {
                result += numbers.get(i);
            } else if(operators.charAt(i-1) == '*') {
                result *= numbers.get(i);
            }
        }
        return result;
    }
}
