import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLongArray;
import java.util.stream.IntStream;

public class FlipBytes {

    private static final int SIZE = 200000000;
    private static final int NUM_THREADS = 7;

    private static AtomicLongArray divsum = new AtomicLongArray(SIZE);

    public static void main(String[] args) throws IOException {

        int mode = Integer.parseInt(args[0]);

        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);

        IntStream.range(0, NUM_THREADS) //
                .mapToObj(c -> executor.submit(() -> {
                    for (int i = c == 0 ? NUM_THREADS : c; i < SIZE; i += NUM_THREADS) {
                        if (mode == 0 && divsum.get(i) > 1) {
                            continue;
                        }

                        for (int j = 2 * i; j < SIZE; j += i) {
                            divsum.addAndGet(j, i);
                        }
                    }
                    return (Void) null;
                })).toList().forEach(f -> {
                    try {
                        f.get();
                    } catch (ExecutionException | InterruptedException e) {
                        System.out.println(e);
                    }
                });

        executor.shutdown();

        byte[] input = Files.readAllBytes(Paths.get(args[1]));
        for (int i = 0; i < input.length; i++) {
            if (shouldFlip(mode, i)) {
                input[i] ^= (byte) 0b11111111;
            }
        }

        Files.write(Paths.get(args[2]), input, StandardOpenOption.CREATE_NEW);
    }

    private static boolean shouldFlip(int mode, int position) {
        if (mode == 0) {
            return divsum.get(position) == 1;
        } else {
            return divsum.get(position) > position;
        }
    }
}
