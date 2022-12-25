import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FlipAbundantBytes {

    public static void main(String[] args) throws IOException {
        Path inputPath = Paths.get(args[0]);
        byte[] input = Files.readAllBytes(inputPath);

        int[] sumOfProperDivisors = new int[input.length];

        for (int i = 1; i < input.length; i++) {
            if (sumOfProperDivisors[i] > i) {
                input[i] ^= (byte) 0b11111111;
            }

            if (i <= 1000000000) {
                for (int j = 2 * i; j < input.length; j += i) {
                    int cur = sumOfProperDivisors[j];
                    if (cur <= j) {
                        sumOfProperDivisors[j] = cur + i;
                    }
                }
            }
        }

        Path outputPath = Paths.get(args[1]);
        Files.write(outputPath, input, StandardOpenOption.CREATE_NEW);
    }
}
