package Day17;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Part2 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("src/Day17/input.txt"));
        String line;
        long B = 0;
        long C = 0;
        long[] program;

        // Read input and parse registers and program
        List<String> lines = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            lines.add(line);
        }
        B = Long.parseLong(lines.get(1).split(":")[1].trim());  // Register B
        C = Long.parseLong(lines.get(2).split(":")[1].trim());  // Register C
        String[] programStr = lines.get(4).split(":")[1].trim().split(",");
        program = new long[programStr.length];
        for (int i = 0; i < programStr.length; i++) {
            program[i] = Long.parseLong(programStr[i].trim());
        }

        // Brute-force to find the lowest positive value of A
        long lowerBound = 2400000000000000L;  // Starting value for A
        long upperBound = 2411751540035530L;  // Reasonable upper bound for A
        for (long A = lowerBound; A <= upperBound; A++) {
            System.out.println("Trying A = " + A); // Debugging line
            if (doesProgramOutputItself(A, B, C, program)) {
                System.out.println("Lowest positive value for A: " + A);
                break;
            }
        }
    }

    private static boolean doesProgramOutputItself(long A, long B, long C, long[] program) {
        List<Long> result = new ArrayList<>();
        int pointer = 0;

        // Simulate program execution
        while (pointer < program.length) {
            long opcode = program[pointer];
            long operand = program[pointer + 1];

            // Determine combo value
            long combo;
            if (operand <= 3) {
                combo = operand; // Literal value
            } else if (operand == 4) {
                combo = A; // Value of register A
            } else if (operand == 5) {
                combo = B; // Value of register B
            } else if (operand == 6) {
                combo = C; // Value of register C
            } else {
                throw new IllegalArgumentException("Invalid combo operand: " + operand);
            }

            // Execute instruction based on opcode
            switch ((int) opcode) {
                case 0: // adv
                    A = (long) (A / Math.pow(2, combo));
                    break;
                case 1: // bxl
                    B = B ^ operand; // Bitwise XOR with literal operand
                    break;
                case 2: // bst
                    B = combo % 8; // Modulo 8 of combo operand
                    break;
                case 3: // jnz
                    if (A != 0) {
                        pointer = (int) operand; // Jump to literal operand
                        continue; // Skip pointer increment
                    }
                    break;
                case 4: // bxc
                    B = B ^ C; // Bitwise XOR of B and C
                    break;
                case 5: // out
                    result.add(combo % 8); // Output value modulo 8
                    break;
                case 6: // bdv
                    B = (long) (A / Math.pow(2, combo)); // Divide A by 2^combo
                    break;
                case 7: // cdv
                    C = (long) (A / Math.pow(2, combo)); // Divide A by 2^combo
                    break;
                default:
                    throw new IllegalArgumentException("Invalid opcode: " + opcode);
            }

            // Increment instruction pointer
            pointer += 2;
        }

        // Debugging: Print last few elements of the output
        int outputSize = result.size();
        System.out.println("Last elements of output:");
        for (int i = Math.max(0, outputSize - 5); i < outputSize; i++) {
            System.out.print(result.get(i) + " ");
        }
        System.out.println();

        // Check if the output matches the program itself (using the last elements)
        if (outputSize != program.length) {
            return false;
        }
        for (int i = 0; i < program.length; i++) {
            if (!result.get(i).equals(program[i])) {
                return false;
            }
        }
        return true;
    }
}
