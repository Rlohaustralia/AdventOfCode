package Day7;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Part2 {
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

            // Generate all combinations of "*", "||", and "+"
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

    // Generate all combinations of "*", "||", and "+"
    private static List<String> generateOperatorCombinations(int length) {
        List<String> combinations = new ArrayList<>();
        int totalCombinations = (int) Math.pow(3, length); // 3 choices per operator

        // Generate combinations using base-3 representation
        for (int i = 0; i < totalCombinations; i++) {
            StringBuilder combination = new StringBuilder();
            int temp = i;
            for (int j = 0; j < length; j++) {
                int operatorIndex = temp % 3;
                if (operatorIndex == 0) {
                    combination.append("*");
                } else if (operatorIndex == 1) {
                    combination.append("+");
                } else {
                    combination.append("||");
                }
                temp /= 3;
            }
            combinations.add(combination.toString());
        }
        return combinations;
    }

    // Evaluate the equation with the given operator combination
    private static long evaluateEquation(String operators, List<Long> numbers) {
        long result = numbers.get(0);
        int operatorIndex = 0;

        for (int i = 1; i < numbers.size(); i++) {
            char operator = operators.charAt(operatorIndex);
            if (operator == '+') {
                result += numbers.get(i);
            } else if (operator == '*') {
                result *= numbers.get(i);
            } else if (operator == '|' && operators.charAt(operatorIndex + 1) == '|') {
                // Handle the "||" operator: concatenation of numbers
                String concatenated = "" + result + numbers.get(i);
                result = Long.parseLong(concatenated);
                operatorIndex++; // Skip next character for "||"
            }
            operatorIndex++;
        }
        return result;
    }
}
