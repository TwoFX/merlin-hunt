package de.markushimmel.merlinhunt.buggyrelay.generator.languages;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.stream.IntStream;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import de.markushimmel.merlinhunt.buggyrelay.generator.IProgramGenerator;
import de.markushimmel.merlinhunt.buggyrelay.generator.util.EmojiProvider;
import io.agroal.api.AgroalDataSource;
import io.quarkus.logging.Log;

@ApplicationScoped
public class SQLiteProgramGenerator implements IProgramGenerator {

    private static final int NUM_EMOJI = 2500;
    private static final int RNG_SEED = 1234;

    @ConfigProperty(name = "buggyrelay.database.name")
    String databaseName;

    @Inject
    AgroalDataSource dataSource;

    @Inject
    EmojiProvider emojiProvider;

    @Override
    public String generateProgram(String standardOutput, String standardError, boolean errors) {
        try (Connection connection = dataSource.getConnection()) {
            executeUpdate(connection, "DROP TABLE IF EXISTS data");
            executeUpdate(connection, "CREATE TABLE data (fd INTEGER, pg TEXT);");
            try (PreparedStatement prepared = connection
                    .prepareStatement("INSERT INTO data (fd, pg) VALUES (1, ?), (2, ?)")) {
                prepared.setString(1, standardOutput);
                prepared.setString(2, standardError);
                prepared.executeUpdate();
            }
        } catch (SQLException e) {
            Log.error("Something has gone very wrong... Fix what's boken and try again.");
            return "You cannot continue here. Fix what's broken and try again.";
        }

        String result;
        try {
            Process xxdProcess = new ProcessBuilder("xxd", databaseName, "-").start();
            result = new String(xxdProcess.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            xxdProcess.getOutputStream().close();
            xxdProcess.getInputStream().close();
            xxdProcess.getErrorStream().close();
            xxdProcess.waitFor();
        } catch (IOException | InterruptedException e) {
            Log.error("Something has gone very wrong... Fix what's boken and try again.");
            return "You cannot continue here. Fix what's broken and try again.";
        }

        if (errors) {
            result = intersperseEmojis(result);
        }

        return result;
    }

    private String intersperseEmojis(String input) {
        Random random = new Random(RNG_SEED);
        int[] positions = IntStream.range(0, NUM_EMOJI) //
                .map(t -> random.nextInt(input.length()))
                .sorted() //
                .toArray();

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < positions.length; i++) {
            int from = i == 0 ? 0 : positions[i - 1];
            int to = positions[i];
            result.append(input.substring(from, to));
            result.append(emojiProvider.getRandomEmoji(random));
        }

        result.append(input.substring(positions[positions.length - 1]));
        return result.toString();
    }

    private void executeUpdate(Connection connection, String sql) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sql);
        }
    }

}
