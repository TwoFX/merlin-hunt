package de.markushimmel.merlinhunt.buggyrelay.generator.languages;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import de.markushimmel.merlinhunt.buggyrelay.generator.IProgramGenerator;
import io.agroal.api.AgroalDataSource;
import io.quarkus.logging.Log;

@ApplicationScoped
public class SQLiteProgramGenerator implements IProgramGenerator {

    @Inject
    AgroalDataSource dataSource;

    @Override
    public String generateProgram(String standardOutput, String standardError, boolean errors) {
        try (Connection connection = dataSource.getConnection()) {
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

        return "Hello";
    }

    private void executeUpdate(Connection connection, String sql) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sql);
        }
    }

}
