package Day24;
import java.io.*;
import java.util.*;

public class Part2 {

    static Map<String, Integer> wires = new HashMap<>();
    static List<Operation> operations = new ArrayList<>();

    public static int process(String op, int op1, int op2) {
        switch (op) {
            case "AND":
                return op1 & op2;
            case "OR":
                return op1 | op2;
            case "XOR":
                return op1 ^ op2;
            default:
                throw new IllegalArgumentException("Unknown operation: " + op);
        }
    }

    public static class Operation {
        String op1, op, op2, res;

        public Operation(String op1, String op, String op2, String res) {
            this.op1 = op1;
            this.op = op;
            this.op2 = op2;
            this.res = res;
        }
    }

    public static void main(String[] args) throws IOException {
        String highestZ = "z00";
        BufferedReader reader = new BufferedReader(new FileReader("src/Day24/input.txt"));
        String line;

        while ((line = reader.readLine()) != null) {
            if (line.contains(":")) {
                String[] parts = line.split(": ");
                String wire = parts[0];
                int value = Integer.parseInt(parts[1]);
                wires.put(wire, value);
            } else if (line.contains("->")) {
                String[] parts = line.split(" ");
                String op1 = parts[0], op = parts[1], op2 = parts[2], res = parts[4];
                operations.add(new Operation(op1, op, op2, res));
                if (res.startsWith("z") && Integer.parseInt(res.substring(1)) > Integer.parseInt(highestZ.substring(1))) {
                    highestZ = res;
                }
            }
        }

        Set<String> wrong = new HashSet<>();

        for (Operation operation : operations) {
            String op1 = operation.op1;
            String op = operation.op;
            String op2 = operation.op2;
            String res = operation.res;

            if (res.startsWith("z") && !op.equals("XOR") && !res.equals(highestZ)) {
                wrong.add(res);
            }
            if (op.equals("XOR") && !res.startsWith("x") && !res.startsWith("y") && !res.startsWith("z") && !op1.startsWith("x") && !op1.startsWith("y") && !op1.startsWith("z") && !op2.startsWith("x") && !op2.startsWith("y") && !op2.startsWith("z")) {
                wrong.add(res);
            }
            if (op.equals("AND") && !op1.equals("x00") && !op2.equals("x00")) {
                for (Operation subOperation : operations) {
                    if ((res.equals(subOperation.op1) || res.equals(subOperation.op2)) && !subOperation.op.equals("OR")) {
                        wrong.add(res);
                    }
                }
            }
            if (op.equals("XOR")) {
                for (Operation subOperation : operations) {
                    if ((res.equals(subOperation.op1) || res.equals(subOperation.op2)) && subOperation.op.equals("OR")) {
                        wrong.add(res);
                    }
                }
            }
        }

        while (!operations.isEmpty()) {
            Operation operation = operations.remove(0);
            String op1 = operation.op1;
            String op = operation.op;
            String op2 = operation.op2;
            String res = operation.res;

            if (wires.containsKey(op1) && wires.containsKey(op2)) {
                wires.put(res, process(op, wires.get(op1), wires.get(op2)));
            } else {
                operations.add(operation);
            }
        }

        StringBuilder bits = new StringBuilder();
        for (String wire : wires.keySet()) {
            if (wire.startsWith("z")) {
                bits.append(wires.get(wire));
            }
        }


        System.out.println(Long.parseLong(bits.toString(), 2));
        List<String> sortedWrong = new ArrayList<>(wrong);
        Collections.sort(sortedWrong);
        System.out.println(String.join(",", sortedWrong));
    }
}
