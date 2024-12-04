package Day3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws IOException {

        // Day 3: Mull It Over

        BufferedReader br = new BufferedReader(new FileReader("src/Day3/input.txt"));

        String line;
        int enabledSum = 0;
        boolean isMulEnabled = true;

        // Regex pattern (Do || Don't || Mul(x,y))
        Pattern pattern = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)|do\\(\\)|don't\\(\\)");


        while ((line = br.readLine()) != null) {

            Matcher matcher = pattern.matcher(line);

            while (matcher.find()) {
                if (matcher.group(1) != null && matcher.group(2) != null){
                    if (isMulEnabled) {
                        int x = Integer.parseInt(matcher.group(1));
                        int y = Integer.parseInt(matcher.group(2));
                        enabledSum += x * y;
                    }
                } else if ("do()".equals(matcher.group())) {
                    isMulEnabled = true;
                } else if ("don't()".equals(matcher.group())) {
                    isMulEnabled = false;
                }
            }
        }
        System.out.println("Enabled sum: " + enabledSum);
    }
}
