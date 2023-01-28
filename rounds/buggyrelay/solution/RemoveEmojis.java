import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

class RemoveEmojis {
    public static void main(String[] args) throws IOException {
        System.out.println(String.join("",
                new String(Files.readAllBytes(Path.of(args[0])), StandardCharsets.UTF_8) //
                        .codePoints() //
                        .filter(x -> x <= 255) //
                        .mapToObj(c -> new String(Character.toChars(c))) //
                        .toArray(String[]::new)));
    }
}