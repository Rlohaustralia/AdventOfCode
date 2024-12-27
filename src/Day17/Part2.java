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
        long A = 0, B = 0, C = 0;
        long[] program;

        // Read input and parse registers and program
        List<String> lines = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            lines.add(line);
        }
        A = Long.parseLong(lines.get(0).split(":")[1].trim());
        B = Long.parseLong(lines.get(1).split(":")[1].trim());
        C = Long.parseLong(lines.get(2).split(":")[1].trim());
        String[] programStr = lines.get(4).split(":")[1].trim().split(",");
        program = new long[programStr.length];
        for (int i = 0; i < programStr.length; i++) {
            program[i] = Long.parseLong(programStr[i].trim());
        }

        // Create instances of GetCombo and RunProgram
        GetCombo getCombo = new GetCombo();
        RunProgram runProgram = new RunProgram();

        // Simulate the program
        List<Long> result = runProgram.run(program, A, B, C, getCombo);

        // Convert result list to a comma-separated string
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < result.size(); i++) {
            sb.append(result.get(i));
            if (i < result.size() - 1) {
                sb.append(",");
            }
        }
        String outputString = sb.toString();
        System.out.println(outputString);

        // Calculate A using backtracking logic
        A = 0;
        int j = 1, istart = 0;
        while (j <= program.length && j >= 0) {
            System.out.println("j: " + j);
            A <<= 3; // Shift A left by 3

            for (int i = istart; i < 8; i++) {
                List<Long> programResult = runProgram.run(program, A + i, B, C, getCombo);

                // Correctly slice the last j elements from the program
                List<Long> sliced = new ArrayList<>();
                int endIdx = program.length - j;
                for (int s = endIdx; s < program.length; s++) {
                    sliced.add(program[s]);
                }

                // Compare sliced part of the program with the result
                if (sliced.equals(programResult)) {
                    // Output for debugging
                    System.out.println("@programResult: " + programResult);
                    System.out.println("@sliced: " + sliced);
                    A += i;
                    break;
                }
            }

            // Adjust j and A if necessary
            if (A != (A + istart)) {
                j--;
                A >>= 3; // Undo shift
                istart = (int) (A % 8) + 1;
                A >>= 3; // Reset A further
                continue;
            }
            j++;
            istart = 0;
        }
        System.out.println("Final A: " + A);
    }
}

class GetCombo {
    public long getCombo(int oper, long rega, long regb, long regc) {
        if (0 <= oper && oper <= 3) {
            return oper;
        } else if (oper == 4) {
            return rega;
        } else if (oper == 5) {
            return regb;
        } else if (oper == 6) {
            return regc;
        }
        throw new IllegalArgumentException("Invalid operand: " + oper);
    }
}

class RunProgram {
    public List<Long> run(long[] program, long A, long B, long C, GetCombo getCombo) {
        int pointer = 0;
        List<Long> result = new ArrayList<>();

        while (pointer < program.length) {
            long opcode = program[pointer];
            long operand = program[pointer + 1];

            // Determine combo value
            long combo = getCombo.getCombo((int) operand, A, B, C);

            // Execute instruction based on opcode
            switch ((int) opcode) {
                case 0: // adv
                    A = (long) (A / Math.pow(2, combo));
                    break;
                case 1: // bxl
                    B = B ^ operand; // Bitwise XOR with literal operand
                    break;
                case 2: // bst
                    B = (int) (combo % 8); // Modulo 8 of combo operand
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
            }
            // Increment instruction pointer
            pointer += 2;
        }
        return result;
    }
}
