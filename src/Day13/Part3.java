package Day14;
import java.io.*;
import java.util.*;

public class Part3 {

    static int n = 103;
    static int m = 101;

    static List<int[]> p = new ArrayList<>();
    static List<int[]> v = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("src/Day14/input.txt"));
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(" ");
            int[] position = Arrays.stream(parts[0].split("=")[1].split(","))
                    .mapToInt(Integer::parseInt).toArray();
            int[] velocity = Arrays.stream(parts[1].split("=")[1].split(","))
                    .mapToInt(Integer::parseInt).toArray();

            // Swap position and velocity
            p.add(new int[] { position[1], position[0] });
            v.add(new int[] { velocity[1], velocity[0] });
        }

        br.close();

        int N = p.size();

        // Perform 100 updates
        for (int t = 0; t < 100; t++) {
            update(N);
        }

        // Calculate robot counts for each quadrant
        int q0 = countRobots(0, n / 2, 0, m / 2);
        int q1 = countRobots(n / 2 + 1, n, 0, m / 2);
        int q2 = countRobots(0, n / 2, m / 2 + 1, m);
        int q3 = countRobots(n / 2 + 1, n, m / 2 + 1, m);

        System.out.println(q0 + " " + q1 + " " + q2 + " " + q3);
        System.out.println(q0 * q1 * q2 * q3);
    }

    // Method to update positions of robots
    public static void update(int N) {
        for (int i = 0; i < N; i++) {
            p.get(i)[0] = (p.get(i)[0] + v.get(i)[0] + n) % n;
            p.get(i)[1] = (p.get(i)[1] + v.get(i)[1] + m) % m;
        }
    }

    // Method to count robots in a specified grid region
    public static int countRobots(int i0, int i1, int j0, int j1) {
        int ans = 0;
        for (int i = i0; i < i1; i++) {
            for (int j = j0; j < j1; j++) {
                for (int[] position : p) {
                    if (i == position[0] && j == position[1]) {
                        ans++;
                    }
                }
            }
        }
        return ans;
    }
}
