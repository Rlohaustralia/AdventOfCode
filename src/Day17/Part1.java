package Day17;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Part1 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("src/Day17/input.txt"));
        String line;
        int A = 0;
        int B = 0;
        int C = 0;
        int[] program;

        // Read input and parse registers and program
        List<String> lines = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            lines.add(line);
        }
        A = Integer.parseInt(lines.get(0).split(":")[1].trim());  // Register A
        B = Integer.parseInt(lines.get(1).split(":")[1].trim());  // Register B
        C = Integer.parseInt(lines.get(2).split(":")[1].trim());  // Register C
        String[] programStr = lines.get(4).split(":")[1].trim().split(",");
        program = new int[programStr.length];
        for (int i = 0; i < programStr.length; i++) {
            program[i] = Integer.parseInt(programStr[i].trim());
        }

        List<Integer> result = new ArrayList<>();
        int pointer = 0;

        // Process instructions
        while (pointer < program.length) {
            int opcode = program[pointer];
            int operand = program[pointer + 1];

            // Determine combo value
            int combo;
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
            switch (opcode) {
                case 0: // adv
                    A = (int) (A / Math.pow(2, combo));
                    break;
                case 1: // bxl
                    B = B ^ operand; // Bitwise XOR with literal operand
                    break;
                case 2: // bst
                    B = combo % 8; // Modulo 8 of combo operand
                    break;
                case 3: // jnz
                    if (A != 0) {
                        pointer = operand; // Jump to literal operand
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
                    B = (int) (A / Math.pow(2, combo)); // Divide A by 2^combo
                    break;
                case 7: // cdv
                    C = (int) (A / Math.pow(2, combo)); // Divide A by 2^combo
                    break;
            }

            // Increment instruction pointer
            pointer += 2;
        }

        // Print final result
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < result.size(); i++) {
            sb.append(result.get(i));
            if (i < result.size() - 1) {
                sb.append(",");
            }
        }
        System.out.println(sb.toString());

    }
}
