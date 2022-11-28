import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.BitSet;

class FlipPrimeBytes {

    public static void main(String[] args) throws IOException {
        Path inputPath = Paths.get(args[0]);
        byte[] input = Files.readAllBytes(inputPath);

        BitSet notPrime = new BitSet(input.length);

        for (long i = 2; i < input.length; i++) {
            if (notPrime.get((int) i)) {
                continue;
            }

            input[(int) i] ^= (byte) 0b11111111;
            for (long j = i * i; j < input.length; j += i) {
                notPrime.set((int) j, true);
            }
        }

        Path outputPath = Paths.get(args[1]);
        Files.write(outputPath, input, StandardOpenOption.CREATE_NEW);
    }

}